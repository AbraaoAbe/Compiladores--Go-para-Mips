package checker;

import static ast.NodeKind.*;
import static typing.Conv.I2R;
import static typing.Conv.NONE;
import static typing.Type.BOOL_TYPE;
import static typing.Type.INT_TYPE;
import static typing.Type.NO_TYPE;
import static typing.Type.FLOAT_TYPE;
import static typing.Type.STRING_TYPE;
import static typing.Type.ARRAY_TYPE;

import com.sun.jdi.LocalVariable;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import ast.AST;
import ast.NodeKind;
import parser.GoLexer;
import parser.GoParser;
import parser.GoParser.SourceFileContext;
import parser.GoParser.FunctionDeclContext;
//import parser.GoParser.FunctionLitvalContext;
import parser.GoParser.BlockContext;
import parser.GoParser.StatementListContext;
import parser.GoParser.AssignmentContext;
//import parser.GoParser.Assign_stmtContext;
import parser.GoParser.VarDeclContext;
import parser.GoParser.EqLtContext;
import parser.GoParser.ExprIdContext;
//import parser.GoParser.ExprFalseContext;
import parser.GoParser.BoolLitContext;
import parser.GoParser.RelAndContext;
import parser.GoParser.RelOrContext;
import parser.GoParser.IntegerLitContext;
import parser.GoParser.ParameterDeclContext;
import parser.GoParser.FloatLitContext;
import parser.GoParser.StringLitContext;
import parser.GoParser.ExprParContext;
//import parser.GoParser.ExprIdContext;
//import parser.GoParser.ExprIntValContext;
//import parser.GoParser.ExprParContext;
//import parser.GoParser.ExprRealValContext;
//import parser.GoParser.ExprStrValContext;
//import parser.GoParser.ExprTrueContext;
//import parser.GoParser.If_stmtContext;
import parser.GoParser.PlusMinusContext;
import parser.GoParser.TimesOverContext;
//import parser.GoParser.Read_stmtContext;
//import parser.GoParser.Repeat_stmtContext;
//import parser.GoParser.StmtContext;
//import parser.GoParser.Stmt_sectContext;
//import parser.GoParser.Vars_sectContext;
//import parser.GoParser.Write_stmtContext;
import parser.GoParserBaseVisitor;
import tables.StrTable;
import tables.VarTable;
import tables.FuncTable;
import typing.Conv;
import typing.Conv.Unif;
import typing.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Analisador sem??ntico de EZLang implementado como um visitor
 * da ParseTree do ANTLR. A classe EZParserBaseVisitor ?? gerada
 * automaticamente e j?? possui m??todos padr??o aonde o comportamento
 * ?? s?? visitar todos os filhos. Por conta disto, basta sobreescrever
 * os m??todos que a gente quer alterar. Neste caso, todos foram sobreescritos.
 *
 * No laborat??rio anterior, foi usado Type no tipo gen??rico do
 * EZParserBaseVisitor porque a gente s?? estava fazendo uma verifica????o
 * simples dos tipos primitivos. Agora o tipo declarado ?? AST, pois o
 * analisador sem??ntico tamb??m realiza a constru????o da AST na mesma passada.
 * Assim, se a an??lise sem??ntica (uso de vari??veis e tipos) terminar sem erros,
 * ent??o temos no final uma AST que representa o programa de entrada.
 * Em linguagens mais complexas ?? prov??vel que sejam necess??rias mais passadas,
 * por exemplo, uma para an??lise sem??ntica e outra para a constru????o da AST.
 * Neste caso, talvez voc?? tenha de implementar dois visitadores diferentes.
 *
 * Lembre que o caminhamento pela Parse Tree ?? top-down. Assim, ?? preciso sempre
 * visitar os filhos de um n?? primeiro para construir as sub??rvores dos filhos.
 * No Bison isso j?? acontecia automaticamente porque o parsing l?? ?? bottom-up e
 * as a????es sem??nticas do parser j?? faziam a constru????o da AST junto com a an??lise
 * sint??tica. Aqui, ?? o inverso, por isso temos que visitar os filhos primeiro.
 */
public class SemanticChecker extends GoParserBaseVisitor<AST> {

	private StrTable st = new StrTable();   // Tabela de strings.
    private VarTable vt = new VarTable();   // Tabela de vari??veis.
	private FuncTable ft = new FuncTable();
	private VarTable localvt;
	private List<Type> list_params;

    Type lastDeclType;  // Vari??vel "global" com o ??ltimo tipo declarado.
	int tamArray;
    AST root; // N?? raiz da AST sendo constru??da.

    // Testa se o dado token foi declarado antes.
    // Se sim, cria e retorna um n?? de 'var use'.
    AST checkVar(Token token) {
    	String text = token.getText();
    	int line = token.getLine();
   		int idx = vt.lookupVar(text);
    	if (idx == -1) {
    		System.err.printf("SEMANTIC ERROR (%d): variable '%s' was not declared.\n", line, text);
    		// A partir de agora vou abortar no primeiro erro para facilitar.
    		System.exit(1);
            return null; // Never reached.
        }
    	return new AST(VAR_USE_NODE, idx, vt.getType(idx));
    }

    // Cria uma nova vari??vel a partir do dado token.
    // Retorna um n?? do tipo 'var declaration'.
    AST newVar(Token token) {
    	String text = token.getText();
    	int line = token.getLine();
   		int idx = vt.lookupVar(text);
        if (idx != -1) {
        	System.err.printf("SEMANTIC ERROR (%d): variable '%s' already declared at line %d.\n", line, text, vt.getLine(idx));
        	// A partir de agora vou abortar no primeiro erro para facilitar.
        	System.exit(1);
            return null; // Never reached.
        }
        idx = vt.addVar(text, line, NO_TYPE, -1);
        return new AST(VAR_DECL_NODE, idx, NO_TYPE);
    }

	AST checkIndex(AST finalIndex, int line){

		// Faz a unifica????o dos tipos para determinar o tipo resultante.
		Type lt = INT_TYPE;
		Type rt = finalIndex.type;
		Unif unif = lt.unifyIndexArith(rt);

		if (unif.type == NO_TYPE) {
			System.out.printf("SEMANTIC ERROR (%d): index expected an int type but found '%s'.\n",
    			line,  rt.toString());
    		System.exit(1);
		}

		// Cria os n??s de convers??o que forem necess??rios segundo a
		// estrutura de convers??o.
		finalIndex = Conv.createConvNode(unif.rc, finalIndex);

		return finalIndex;
	}

	// Testa se o dado token foi declarado antes.
    // Se sim, cria e retorna um n?? de 'var use'.
    AST checkFunc(Token token) {
    	String text = token.getText();
    	int line = token.getLine();
   		int idx = ft.lookupFunc(text);
    	if (idx == -1) {
    		System.err.printf("SEMANTIC ERROR (%d): func '%s' was not declared.\n", line, text);
    		// A partir de agora vou abortar no primeiro erro para facilitar.
    		System.exit(1);
            return null; // Never reached.
        }
    	return new AST(FUNC_USE_NODE, idx, ft.getRetorno(idx));
    }

    // Cria uma nova vari??vel a partir do dado token.
    // Retorna um n?? do tipo 'var declaration'.
    AST newFunc(Token token) {
    	String text = token.getText();
    	int line = token.getLine();
   		int idx = ft.lookupFunc(text);
        if (idx != -1) {
        	System.err.printf("SEMANTIC ERROR (%d): variable '%s' already declared at line %d.\n", line, text, vt.getLine(idx));
        	// A partir de agora vou abortar no primeiro erro para facilitar.
        	System.exit(1);
            return null; // Never reached.
        }
        idx = ft.addFunc(text, line, null, NO_TYPE, null);
        return new AST(FUNC_DECL_NODE, idx, NO_TYPE);
    }

	void newFunc(GoParser.FunctionDeclContext ctx) {
		String text = ctx.getChild(1).getText();
		int line = ctx.IDENTIFIER().getSymbol().getLine();
		int idx = ft.lookupFunc(text);
		if (idx != -1) {
			System.err.printf(
					"SEMANTIC ERROR (%d): function '%s' already declared at line %d.\n",
					line, text, ft.getLine(idx));
			System.exit(1);
//			return null;

		}
		// Cria a lista de param
		List<Type> param = new ArrayList<Type>();

		List<GoParser.ParameterDeclContext> parameterDeclContext = ctx.signature().parameters().parameterDecl();
		int tam = parameterDeclContext.size();

		for(int i = 0; i < tam; i++) {
//			String tipo = parameterDeclContext.get(i).type_().typeName().IDENTIFIER().getSymbol().getText();
//			setLastDeclType(tipo);
			visit(parameterDeclContext.get(i).type_());
			param.add(lastDeclType);
		}


		//cria list retorno
		visit(ctx.signature().type_());
		Type returns = lastDeclType;
		ft.addFunc(text, line, param, returns, new VarTable());

	}

	AST checkLocalVar(Token token) {
		String text = token.getText();
		int line = token.getLine();
		int idx = localvt.lookupVar(text);
		if (idx == -1) {
			System.err.printf("SEMANTIC ERROR (%d): variable '%s' was not declared.\n", line, text);
			// A partir de agora vou abortar no primeiro erro para facilitar.
			System.exit(1);
			return null; // Never reached.
		}
		return new AST(VAR_USE_NODE, idx, localvt.getType(idx));
	}

	// Cria uma nova vari??vel a partir do dado token.
	// Retorna um n?? do tipo 'var declaration'.
	AST newLocalVar(Token token) {
		String text = token.getText();
		int line = token.getLine();
		int idx = localvt.lookupVar(text);
		if (idx != -1) {
			System.err.printf("SEMANTIC ERROR (%d): variable '%s' already declared at line %d.\n", line, text, vt.getLine(idx));
			// A partir de agora vou abortar no primeiro erro para facilitar.
			System.exit(1);
			return null; // Never reached.
		}
		idx = localvt.addVar(text, line, NO_TYPE, -1);
		return new AST(VAR_DECL_NODE, idx, NO_TYPE);
	}

    // ----------------------------------------------------------------------------
    // Type checking and inference.

    private static void typeError(int lineNo, String op, Type t1, Type t2) {
    	System.out.printf("SEMANTIC ERROR (%d): incompatible types for operator '%s', LHS is '%s' and RHS is '%s'.\n",
    			lineNo, op, t1.toString(), t2.toString());
    	System.exit(1);
    }

    private static void checkBoolExpr(int lineNo, String cmd, Type t) {
        if (t != BOOL_TYPE) {
            System.out.printf("SEMANTIC ERROR (%d): conditional expression in '%s' is '%s' instead of '%s'.\n",
               lineNo, cmd, t.toString(), BOOL_TYPE.toString());
            System.exit(1);
        }
    }

    // ----------------------------------------------------------------------------

    // Exibe o conte??do das tabelas em stdout.
    void printTables() {
        System.out.print("\n\n");
        System.out.print(st);
        System.out.print("\n\n");
    	System.out.print(vt);
    	System.out.print("\n\n");
		System.out.print(ft);
    	System.out.print("\n\n");
    }

    // Exibe a AST no formato DOT em stderr.
    void printAST(String type) {
		if( type == "console"){
			AST.printDot(root, vt);
		} else{
			AST.printFileDot(root, vt);
		}
    }

    // ----------------------------------------------------------------------------
    // Visitadores.
	
	// Visita a regra sourceFile: ((functionDecl) eos)* EOF #funcDeclLoop
	//							| ((methodDecl) eos)* EOF #methDeclLoop
	//							| ((declaration) eos)* EOF #DeclvarLoop;
    @Override
	public AST visitSourceFile(SourceFileContext ctx) {
    	this.root = AST.newSubtree(PROGRAM_NODE, NO_TYPE);
    	// No caso de n??o-terminais com fechos (* ou +), a chamada do m??todo
    	// correspondente retornatype_ uma lista com todos os elementos da Parse
    	// Tree que entraram no fecho. Assim, podemos percorrer (visitar) a
    	// lista para construir as sub??rvores dos filhos.
    	// Tamb??m ?? poss??vel usar o iterador da lista aqui mas prefiro esse
    	// estilo de loop cl??ssico...

		//visita o sourceFile: ((functionDecl) eos)* EOF #funcDeclLoop
    	for (int i = 0; i < ctx.functionDecl().size(); i++) {
    		AST child = visit(ctx.functionDecl(i));
    		this.root.addChild(child);
    	}
	

		//visita o sourceFile: ((declaration) eos)* EOF #DeclvarLoop
    	//for (int i = 0; i < ctx.DeclvarLoop().size(); i++) {
    	//	AST child = visit(ctx.DeclvarLoop(i));
    	//	this.root.addChild(child);
    	//}
    	
    	// Como esta ?? a regra inicial, chegamos na raiz da AST.
		return this.root;
	}

	//visita a regra functionDecl: FUNC IDENTIFIER (signature block?);
//	@Override
//	public AST visitFunctionDecl(FunctionDeclContext ctx) {
//
//
//		AST func = newFunc(ctx.IDENTIFIER().getSymbol()); // Precisa pegar o tipo da funcao
//
//		int idx = func.getIntData();
//		localvt = ft.getVarTable(idx); // Set localvt
//
//		AST params = visit(ctx.signature()); // visit return type
//		func.addChild(params);
//
//		// Set type for node and func table
//		func.setType(lastDeclType);
//		ft.setRetorno(idx, lastDeclType);
//
//		// Backup the global vartable
//		VarTable backup = vt;
//		vt = localvt;
//		// Now all the variables created will be inserted on localvt
//		AST block = visit(ctx.block());
//		func.addChild(block);
//
//		// Come back to global vartable;
//		vt = backup;
//
//		return func;
//	}
//
//	//	signature:
//	//	parameters type_?;
//	public AST visitSignature(GoParser.SignatureContext  ctx){
//		AST params = visit(ctx.parameters());
//
//		if (ctx.type_() != null){
//			visit(ctx.type_());
//		} else{
//			lastDeclType = NO_TYPE;
//		}
//		return params;
//	}
//
//	@Override
//	public AST visitParameters(GoParser.ParametersContext ctx) {
//
//		AST params_list = AST.newSubtree(PARAMS_LIST_NODE, NO_TYPE);
//		int size = ctx.parameterDecl().size();
////		params_list = new List<Type>(size);
//		for (int i = 0; i < size; i++) {
//			AST node = visit(ctx.parameterDecl(i)); // Precisa pegar o tipo da vari??vel
//			params_list.addChild(node);
//		}
//		return params_list;
//	}
//
//	@Override
//	public AST visitParameterDecl(ParameterDeclContext ctx) {
//
//		AST node = newLocalVar(ctx.IDENTIFIER().getSymbol());
//		visit(ctx.type_());
//		node.setType(lastDeclType);
//		return node;
//	}

	/*
	//visita a regra block: L_CURLY statementList R_CURLY;
	@Override
	public AST visitBlock(BlockContext ctx) {
		AST block = AST.newSubtree(BLOCK_NODE, NO_TYPE);
    	
		AST node = visit(ctx.statementList());

		AST random = AST.newSubtree(VAR_USE_NODE, NO_TYPE);
		
		block.addChild(node);
		block.addChild(random);
    	

		return block;
	
	}
	*/

	@Override
    public AST visitBlock(GoParser.BlockContext ctx){
        AST blockTree = (AST.newSubtree(ast.NodeKind.BLOCK_NODE,Type.NO_TYPE));
        try {        
            int tam = ctx.statementList().statement().size();
            
            for(int i = 0;i < tam;i++){
                AST teste = visit(ctx.statementList().statement(i));    

            	blockTree.addChild(teste);
            }
        }    catch(Exception e) {
            System.out.printf("[visitBlock] Caiu exception statementList [%s]\n",e.toString());
        }
        
        return blockTree;

    }

	//statementList: (statement eos?)+;
	@Override
	public AST visitStatementList(StatementListContext ctx) {
    
		AST stmtList = AST.newSubtree(STMT_LIST_NODE, NO_TYPE);
    	
		for (int i = 0; i < ctx.statement().size(); i++) {
			AST child = visit(ctx.statement(i));
			stmtList.addChild(child);
    	}
    	
    	return stmtList;
	}


	// Visita a regra varDecl: VAR varSpec ;
    

	// varSpec:
	// identifierList type_ (ASSIGN expressionList)?;
	// Agora a regra est?? assim
	// varSpec:
	//	identifierList type_;
	// O acesso a expressionList sera feito no corpo do programa
	@Override
	public AST visitVarSpec(GoParser.VarSpecContext ctx) {
		AST idList = visit(ctx.identifierList());

		visit(ctx.type_());

		for (int i = 0; i < idList.getSizeChild(); i++) {
			AST child = idList.children.get(i);
			//set type 
			child.type = lastDeclType;
			if (tamArray != 0){
				child.sizeData = tamArray;
			}

			//vartable
			vt.setType(child.intData, lastDeclType);
			vt.setTamArr(child.intData, tamArray);

			tamArray = -1;
    	}
    	
    	return idList;
	}

	//identifierList: IDENTIFIER (COMMA IDENTIFIER)*;
	@Override
	public AST visitIdentifierList(GoParser.IdentifierListContext ctx) {
    
		AST varList = AST.newSubtree(VAR_LIST_NODE, NO_TYPE);
    	
		for (int i = 0; i < ctx.IDENTIFIER().size(); i++) {
    		AST node = newVar(ctx.IDENTIFIER(i).getSymbol()); // Precisa pegar o tipo da vari??vel
			varList.addChild(node);
    	}
    	
    	return varList;
	}

	// Visita a regra typeName: BOOL
    @Override
    public AST visitBoolType(GoParser.BoolTypeContext ctx) {
    	this.lastDeclType = Type.BOOL_TYPE;
		this.tamArray = -1;
    	// N??o tem problema retornar null aqui porque o m??todo chamador
    	// ignora o valor de retorno.
    	return null;
    }

	// Visita a regra typeName: INT
	@Override
	public AST visitIntType(GoParser.IntTypeContext ctx) {
		this.lastDeclType = Type.INT_TYPE;
		this.tamArray = -1;
		// N??o tem problema retornar null aqui porque o m??todo chamador
    	// ignora o valor de retorno.
		return null;
	}

	@Override
	public AST visitStringType(GoParser.StringTypeContext ctx){
		this.lastDeclType = Type.STRING_TYPE;
		this.tamArray = -1;
//
//		this.st.add(ctx.string_().getStop().getText());

    	return null; // Java says must return something even when Void	
	}

	// Visita a regra typeName: FLOAT
	@Override
	public AST visitFloatType(GoParser.FloatTypeContext ctx) {
		this.lastDeclType = Type.FLOAT_TYPE;
		this.tamArray = -1;
		// N??o tem problema retornar null aqui porque o m??todo chamador
    	// ignora o valor de retorno.
		return null;
	}

	//!MUDAR ARRAY TYPE
	@Override
	public AST visitArrayType(GoParser.ArrayTypeContext ctx){
//		visit(ctx.typeName()); // define type array

//		this.lastDeclType = Type.ARRAY_TYPE;
		this.tamArray = Integer.parseInt(ctx.arrayLength().getText());
//		this.tamArray = 111;
		Type arrt;
		int type = ctx.typeName().getStop().getType();
		if (type == GoLexer.INT){
			arrt = INT_TYPE;
		} else if (type == GoLexer.FLOAT){
			arrt = FLOAT_TYPE;
		} else if (type == GoLexer.STRING){
			arrt = STRING_TYPE;
		} else{
			arrt = BOOL_TYPE;
		}
		this.lastDeclType = arrt;
    	return null; // Java says must return something even when Void
	}

	// Visita a regra assignment: IDENTIFIER index? ASSIGN expression;
	@Override
	public AST visitAssignment(GoParser.AssignmentContext ctx) {
		// Visita a express??o da direita.
		AST exprNode = visit(ctx.expression());
		// Visita o identificador da esquerda.
		Token idToken = ctx.IDENTIFIER().getSymbol();
		AST idNode = checkVar(idToken);
		if (ctx.index() != null) {
//			idNode.sizeData = Integer.parseInt(ctx.index().DECIMAL_LIT().getText());
			AST index_node = visit(ctx.index());
			idNode.addChild(checkIndex(index_node, ctx.getStop().getLine()));
//			AST some =

		}

		// Faz as verifica????es de tipos.
		return checkAssign(idToken.getLine(), idNode, exprNode);
	}

	@Override
	public AST visitIndex(GoParser.IndexContext ctx) {
		return visit(ctx.expression());
	}

	@Override
	public AST visitForStmt(GoParser.ForStmtContext ctx) {
		AST for_root = AST.newSubtree(FOR_NODE, NO_TYPE);

		AST stm_node = visit(ctx.forClause());
		for(int i=0; i < stm_node.getSizeChild(); i++){
			for_root.addChild(stm_node.getChild(i));
		}

		AST block = visit(ctx.block());
		for_root.addChild(block);

		return for_root;
	}

	//	forClause:
	//	initStmt = simpleStmt? eos expression? eos postStmt = simpleStmt?;
	@Override
	public AST visitForClause(GoParser.ForClauseContext ctx) {
		AST temp_root = AST.newSubtree(STMT_LIST_NODE, NO_TYPE);
		if (ctx.initStmt != null){
			temp_root.addChild(visit(ctx.initStmt));
		}
		if (ctx.expression() != null){
			AST exprNode = visit(ctx.expression());
			checkBoolExpr(ctx.expression().getStop().getLine(), "for", exprNode.type);
			temp_root.addChild(exprNode);
		}
		if (ctx.postStmt != null){
			temp_root.addChild(visit(ctx.postStmt));
		}
		return temp_root;
	}

	// Visita a regra typeDecl: type_spec ID SEMI
    // @Override
    // public AST visitTypeDecl(GoParser.TypeDeclContext ctx) {
    // 	// Visita a defini????o do tipo da vari??vel.
    // 	visit(ctx.type_spec());
    // 	// Cria e retorna um n?? para a vari??vel.
    // 	return newVar(ctx.ID().getSymbol());
    // }

    // Visita a regra stmt_sect: BEGIN stmt+ END
//    @Override
//	public AST visitStmt_sect(Stmt_sectContext ctx) {
//    	// Valem os mesmos coment??rios de visitVars_sect.
//    	AST node = AST.newSubtree(BLOCK_NODE, NO_TYPE);
//    	for (int i = 0; i < ctx.stmt().size(); i++) {
//    		AST child = visit(ctx.stmt(i));
//    		node.addChild(child);
//    	}
//    	return node;
//	}

    // Visita a regra stmt para todos os poss??veis tipos.
    // Esse m??todo n??o precisava ser sobreescrito, mas era o ??nico
    // que ficou faltando... :P
//	@Override
//	public AST visitStmt(StmtContext ctx) {
//		return super.visitStmt(ctx);
//	}

	// Visita a regra assign_stmt: ID ASSIGN expr SEMI
//	@Override
//	public AST visitVarDecl(VarDeclContext ctx) {
//		// Visita a express??o da direita.
//		AST exprNode = visit(ctx.expr());
//		// Visita o identificador da esquerda.
//		Token idToken = ctx.ID().getSymbol();
//		AST idNode = checkVar(idToken);
//		// Faz as verifica????es de tipos.
//		return checkAssign(idToken.getLine(), idNode, exprNode);
//	}

	// M??todo auxiliar criado s?? para separar melhor a parte de visitador
	// da parte de an??lise sem??ntica.
    private static AST checkAssign(int lineNo, AST l, AST r) {
    	Type lt = l.type;
    	Type rt = r.type;

        if (lt == BOOL_TYPE && rt != BOOL_TYPE) typeError(lineNo, "=", lt, rt);
        if (lt == STRING_TYPE  && rt != STRING_TYPE)  typeError(lineNo, "=", lt, rt);
        if (lt == INT_TYPE  && rt != INT_TYPE)  typeError(lineNo, "=", lt, rt);

        if (lt == FLOAT_TYPE) {
        	if (rt == INT_TYPE) {
        		r = Conv.createConvNode(I2R, r);
        	} else if (rt != FLOAT_TYPE) {
        		typeError(lineNo, "=", lt, rt);
            }
        }

        return AST.newSubtree(ASSIGN_NODE, NO_TYPE, l, r);
    }

	
	//ponte
	@Override
	public AST visitStmtIf(GoParser.StmtIfContext ctx){
		return visit(ctx.ifStmt());
	}

	// Visita a regra if_stmt: IF expr THEN stmt+ (ELSE stmt+)? END
	//ifStmt: IF ( expression  ) block ( ELSE block )?;
	@Override
	public AST visitIfStmt(GoParser.IfStmtContext ctx) {
		// Esse ?? o m??todo mais complicado da AST. A complica????o surge
		// principalmente porque o bloco de ELSE ?? opcional, e por conta
		// dos dois fechos 'stmt+', pois o ANTLR n??o faz distin????o entre eles.
		// Assim, comandos dos blocos de THEN e ELSE ficam todos na mesma
		// lista de 'stmt'. Pelo menos eles ficam em ordem.

		// Uma forma mais simples de resolver esse problema seria criar
		// dois novos n??o-terminais como
		// then_part: stmt+
		// else_part: stmt+
		// Assim, daria para identificar e usar as partes com m??todos diferentes.

		// Preferi n??o fazer desse jeito porque acho que voc??s v??o ter casos
		// parecidos nas gram??ticas, e assim d?? para aproveitar e mostar um pouco
		// da magia negra que d?? para fazer com o ANTLR... :P

		// Analisa a express??o booleana.
		AST exprNode = visit(ctx.expression());
		checkBoolExpr(ctx.IF().getSymbol().getLine(), "if", exprNode.type);

		// Vamos dividir o c??digo em duas partes, um IF sem ou com ELSE.
		// Para saber se um s??mbolo opcional est?? presente, basta chamar o
		// seu m??todo. Se for retornado nulo, o s??mbolo n??o existe.
		if (ctx.ELSE() == null) {
			// Caso em que n??o existe um bloco de ELSE. A?? fica simples
			// porque todos os comandos pertencem ao bloco do THEN.
			AST thenNode = visit(ctx.block(0));
			
			return AST.newSubtree(IF_NODE, NO_TYPE, exprNode, thenNode);
		} else {
			// Caso em que existe um bloco de ELSE. A?? precisamos separar
			// os 'stmt' entre os blocos de THEN e ELSE. Vamos usar o
			// Token do ELSE para fazer essa separa????o. Mas para isso
			// precisamos identificar o ??ndice do ELSE na lista de todos
			// os filhos da Parse Tree.

			// Cria o n?? com o bloco de comandos do THEN.
			AST thenNode = AST.newSubtree(BLOCK_NODE, NO_TYPE);
			
			//primeiro bloco pertence ao if
	    	AST childThen = visit(ctx.block(0));
	    	thenNode.addChild(childThen);
			

			// Cria o n?? com o bloco de comandos do ELSE.
			AST elseNode = AST.newSubtree(BLOCK_NODE, NO_TYPE);
			//segundo bloco pertence ao else
	    	AST childElse = visit(ctx.block(1));
	    	elseNode.addChild(childElse);
			

			// // Faz uma busca pelo token na lista de filhos e retorna o indice do mesmo
			// TerminalNode elseToken = ctx.ELSE();
			// int elseIdx = -1;
			
			// for (int i = 0; i < ctx.children.size(); i++) {
			// 	if (ctx.children.get(i).equals(elseToken)) {
			// 		elseIdx = i;
			// 		break;
			// 	}
			// }

			// Temos que elseIdx ?? o ??ndice na lista de todos os filhos.
			// Por outro lado, a lista de 'stmts' come??a do ??ndice zero.
			// O offset entre as duas listas ?? 3 porque a regra come??a com
			// IF expr THEN
			// ou seja, h?? 3 s??mbolos antes do primeiro bloco de 'stmt+'.
			// int thenEnd = elseIdx - 3;

			// // Cria o n?? com o bloco de comandos do THEN.
			// AST thenNode = AST.newSubtree(BLOCK_NODE, NO_TYPE);
			// for (int i = 0; i < thenEnd; i++) {
	    	// 	AST child = visit(ctx.block(i));
	    	// 	thenNode.addChild(child);
			// }

			// // Cria o n?? com o bloco de comandos do ELSE.
			// AST elseNode = AST.newSubtree(BLOCK_NODE, NO_TYPE);
			// for (int i = thenEnd; i < ctx.block().size(); i++) {
	    	// 	AST child = visit(ctx.block(i));
	    	// 	elseNode.addChild(child);
			// }

			return AST.newSubtree(IF_NODE, NO_TYPE, exprNode, thenNode, elseNode);
		}
	}

	// Visita a regra read_stmt: READ ID SEMI
	// ----
	// Visita o ioStmt:scanIo
	@Override
	public AST visitScanIO(GoParser.ScanIOContext ctx) {
//		AST idNode = checkVar(ctx.ID().getSymbol());
		AST exprNode = visit(ctx.paramsCaller());
		return AST.newSubtree(SCAN_NODE, NO_TYPE, exprNode);
	}
	// Visita o ioStmt:printIo
	@Override
	public AST visitPrintIO(GoParser.PrintIOContext ctx) {
//		AST idNode = checkVar(ctx.ID().getSymbol());
		AST exprNode = visit(ctx.paramsCaller());
		return AST.newSubtree(PRINT_NODE, NO_TYPE, exprNode);
	}

	//paramsCaller: L_PAREN (expression (COMMA expression)* COMMA?)? R_PAREN;
	@Override
	public AST visitParamsCaller(GoParser.ParamsCallerContext ctx) {
		int tam = ctx.expression().size();
		AST paramsList = AST.newSubtree(PARAMS_LIST_NODE, NO_TYPE);

		for(int i = 0 ; i < tam; i++){
			AST child = visit(ctx.expression(i));
			paramsList.addChild(child);
		}

		return paramsList;
	}

	

	// Visita as subregra de expression
	//	expression:
	//	primaryExpr #primaryExp
	//	| expression mul_op = ( TIMES| DIV | MOD ) expression # timesOver
	//	| expression add_op = ( PLUS | MINUS ) expression # plusMinus
	//	| expression rel_op = ( EQUALS | NOT_EQUALS | LESS | LESS_OR_EQUALS | GREATER | GREATER_OR_EQUALS ) expression # eqLt
	//	| expression LOGICAL_AND expression # relAnd
	//	| expression LOGICAL_OR expression #relOr;
	
	// Visita a regra expr: expr op=(TIMES | OVER) expr
	@Override
	public AST visitTimesOver(TimesOverContext ctx) {
		// Visita recursivamente as duas subexpress??es.
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));

		// Faz a unifica????o dos tipos para determinar o tipo resultante.
		Type lt = l.type;
		Type rt = r.type;
		Unif unif = lt.unifyOtherArith(rt);

		if (unif.type == NO_TYPE) {
			typeError(ctx.mul_op.getLine(), ctx.mul_op.getText(), lt, rt);
		}

		// Cria os n??s de convers??o que forem necess??rios segundo a
		// estrutura de convers??o.
		l = Conv.createConvNode(unif.lc, l);
		r = Conv.createConvNode(unif.rc, r);

		// Olha qual ?? o operador e cria o n?? correspondente na AST.
		if (ctx.mul_op.getType() == GoParser.TIMES) {
			return AST.newSubtree(TIMES_NODE, unif.type, l, r);
		} else if (ctx.mul_op.getType() == GoParser.DIV) { // DIV
			return AST.newSubtree(DIV_NODE, unif.type, l, r);
		} else{ // MOD
			return AST.newSubtree(MOD_NODE, unif.type, l, r);
		}
	}

	// Visita a regra expr: expr op=(PLUS | MINUS) expr
	//expression add_op = ( PLUS | MINUS ) expression # plusMinus
	@Override
	public AST visitPlusMinus(PlusMinusContext ctx) {
		// Visita recursivamente as duas subexpress??es.
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));

		// Faz a unifica????o dos tipos para determinar o tipo resultante.
		Type lt = l.type;
		Type rt = r.type;
		Unif unif;
		// ?? preciso diferenciar '+' e '-' na unifica????o por conta da sem??ntica.
		if (ctx.add_op.getType() == GoParser.PLUS) {
			unif = lt.unifyPlus(rt);
		} else { // MINUS
			unif = lt.unifyOtherArith(rt);
		}

		if (unif.type == NO_TYPE) {
			typeError(ctx.add_op.getLine(), ctx.add_op.getText(), lt, rt);
		}

		// Cria os n??s de convers??o que forem necess??rios segundo a
		// estrutura de convers??o.
		l = Conv.createConvNode(unif.lc, l);
		r = Conv.createConvNode(unif.rc, r);

		// Olha qual ?? o operador e cria o n?? correspondente na AST.
		if (ctx.add_op.getType() == GoParser.PLUS) {
			return AST.newSubtree(PLUS_NODE, unif.type, l, r);
		} else { // MINUS
			return AST.newSubtree(MINUS_NODE, unif.type, l, r);
		}
	}

	// Visita a regra expr: expr op=(EQ | LT) expr
	@Override
	public AST visitEqLt(EqLtContext ctx) {
		// Visita recursivamente as duas subexpress??es.
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));

		// Faz a unifica????o dos tipos para determinar o tipo resultante.
		Type lt = l.type;
		Type rt = r.type;
		Unif unif = lt.unifyComp(rt);

		if (unif.type == NO_TYPE) {
			typeError(ctx.rel_op.getLine(), ctx.rel_op.getText(), lt, rt);
		}

		// Cria os n??s de convers??o que forem necess??rios segundo a
		// estrutura de convers??o.
		l = Conv.createConvNode(unif.lc, l);
		r = Conv.createConvNode(unif.rc, r);

		// Olha qual ?? o operador e cria o n?? correspondente na AST.
		if (ctx.rel_op.getType() == GoParser.EQUALS) {
			return AST.newSubtree(EQ_NODE, unif.type, l, r);
		} else if (ctx.rel_op.getType() == GoParser.NOT_EQUALS) {
			return AST.newSubtree(NEQ_NODE, unif.type, l, r);
		} else if (ctx.rel_op.getType() == GoParser.LESS) {
			return AST.newSubtree(LESS_NODE, unif.type, l, r);
		} else if (ctx.rel_op.getType() == GoParser.LESS_OR_EQUALS) {
			return AST.newSubtree(LESS_OR_EQUALS_NODE, unif.type, l, r);
		} else if (ctx.rel_op.getType() == GoParser.GREATER) {
			return AST.newSubtree(GREATER_NODE, unif.type, l, r);
		} else  { // Greater or equals
			return AST.newSubtree(GREATER_OR_EQUALS_NODE, unif.type, l, r);
		}
	}

	@Override
	public AST visitRelAnd(GoParser.RelAndContext ctx){
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));

		Type lt = l.type;
		Type rt = r.type;
		Unif unif = lt.unifyComp(rt);

		if (unif.type == NO_TYPE) {
			typeError(ctx.LOGICAL_AND().getChildCount(), ctx.LOGICAL_AND().getText(), lt, rt);
		}

		// Cria os n??s de convers??o que forem necess??rios segundo a
		// estrutura de convers??o.
		l = Conv.createConvNode(unif.lc, l);
		r = Conv.createConvNode(unif.rc, r);

		return AST.newSubtree(AND_NODE, unif.type, l, r);
	}

	@Override
	public AST visitRelOr(GoParser.RelOrContext ctx){
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));

		Type lt = l.type;
		Type rt = r.type;
		Unif unif = lt.unifyComp(rt);

		if (unif.type == NO_TYPE) {
			typeError(ctx.LOGICAL_OR().getChildCount(), ctx.LOGICAL_OR().getText(), lt, rt);
		}

		// Cria os n??s de convers??o que forem necess??rios segundo a
		// estrutura de convers??o.
		l = Conv.createConvNode(unif.lc, l);
		r = Conv.createConvNode(unif.rc, r);

		return AST.newSubtree(AND_NODE, unif.type, l, r);
	}


	// Visita a regra expr: TRUE
	//@Override
//	public AST visitExprTrue(ExprTrueContext ctx) {
//		// Fim da recurs??o, representa 'true' como o inteiro '1'.
//		return new AST(BOOL_VAL_NODE, 1, BOOL_TYPE);
//	}
//
//	// Visita a regra expr: FALSE
//	@Override
//	public AST visitExprFalse(ExprFalseContext ctx) {
//		// Fim da recurs??o, representa 'false' como o inteiro '0'.
//		return new AST(BOOL_VAL_NODE, 0, BOOL_TYPE);
//	}
	// Esse visitExprTrue ou False s??o convertido nesse visitor
	@Override
	public AST visitBoolLit(BoolLitContext ctx){
		if (ctx.bool_v.getType() == GoParser.FALSE){ // checa se o valor de bool ?? false
			return new AST(BOOL_VAL_NODE, 0,BOOL_TYPE);
		} else{
			return new AST(BOOL_VAL_NODE, 1,BOOL_TYPE);
		}
	}

	// Visita a regra expr: INT_VAL
	@Override
	public AST visitIntegerLit(GoParser.IntegerLitContext ctx) {
		// Fim da recurs??o, converte o lexema da constante inteira.
		int intData = Integer.parseInt(ctx.getText());
		return new AST(INT_VAL_NODE, intData, INT_TYPE);
	}

	// Visita a regra expr: REAL_VAL
	@Override
	public AST visitFloatLit(GoParser.FloatLitContext ctx) {
		// Fim da recurs??o, converte o lexema da constante real.
		float floatData = Float.parseFloat(ctx.getText());
		return new AST(REAL_VAL_NODE, floatData, FLOAT_TYPE);
	}

	@Override
	// Visita a regra expr: STR_VAL
	public AST visitStringLit(GoParser.StringLitContext ctx) {
		// Adiciona a string na tabela de strings.
		String value = ctx.str_v.getText();
		int idx = st.addStr(value);
		// Campo 'data' do n?? da AST guarda o ??ndice na tabela.
		return new AST(STR_VAL_NODE, idx, STRING_TYPE);
	}

	@Override
	// Visita a regra expr: ID
	public AST visitExprId(ExprIdContext ctx) {
		// Fim da recurs??o, retorna um n?? de 'var use'.

		//sem array
		if(ctx.index() == null){
			return checkVar(ctx.IDENTIFIER().getSymbol());
		}else{//com array
			AST var = checkVar(ctx.IDENTIFIER().getSymbol());
			AST child = visit(ctx.index());

			var.addChild(child);
			return var;
		}
		
	}

	// Visita a regra expr: LPAR expr RPAR
	@Override
	public AST visitExprPar(ExprParContext ctx) {
		// Propaga o n?? criado para a express??o.
		return visit(ctx.expression());
	}


	//exoression: funcCaller
//	@Override
//	public AST visitFuncCaller(GoParser.FuncCallerContext ctx) {
//		return visit(ctx.functionCaller());
//	}
//
//	//functionCaller: IDENTIFIER paramsCaller;
//	//paramsCaller: L_PAREN (IDENTIFIER (COMMA IDENTIFIER)* COMMA?)? R_PAREN;
//	@Override
//	public AST visitFunctionCaller(GoParser.FunctionCallerContext ctx) {
//
//		// Visita o identificador da esquerda.
//		Token idToken = ctx.IDENTIFIER().getSymbol();
//		AST idNode = checkFunc(idToken);
//		return null;
//	}


	@Override
	public AST visitStmtBreak(GoParser.StmtBreakContext ctx) {
		return AST.newSubtree(BREAK_NODE, NO_TYPE);
	}
	

	// Visita a regra expr: LPAR expr RPAR
	//@Override
	//public AST visitArrayLitval(ArrayLitvalContext ctx) {
	// Propaga o n?? criado para a express??o.
	//	return visit(ctx.expression());
	//}

	// Visita a regra expression: functionLit
	//functionLit: IDENTIFIER parameters
//	@Override
//	public AST visitFunctionLitval(FunctionLitvalContext ctx) {
//		// Visita o parametro da direita.
//		AST parameters = visit(ctx.functionLit().arguments());
//		// Visita o identificador da esquerda.
//		Token idToken = ctx.functionLit().IDENTIFIER().getSymbol();
//		AST func = checkFunc(idToken);
//
//		AST funccall = AST.newSubtree(FUNC_CALL_NODE, NO_TYPE, func);
//		return funccall;
//	}

	//parameters:
	//L_PAREN (parameterDecl (COMMA parameterDecl)* COMMA?)? R_PAREN;
//	@Override
//	public AST visitParameters(GoParser.ParametersContext ctx) {
//		AST list_params =  AST.newSubtree(PARAMS_LIST_NODE, NO_TYPE);
//
//		for (int i = 0; i < ctx.parameterDecl().size(); i++) {
//    		AST child = visit(ctx.parameterDecl(i));
//    		list_params.addChild(child);
//    	}
//		return list_params;
//	}
//
//
//	//parameterDecl: IDENTIFIER type_;
//	@Override
//	public AST visitParameterDecl(ParameterDeclContext ctx) {
//
//		AST var = newVar(ctx.IDENTIFIER().getSymbol());
//
//		visit(ctx.type_());
//
//		AST child = var.children.get(0);
//		child.type = lastDeclType;
//		vt.setType(child.intData, lastDeclType);
//
//		return var;
//	}

//	@Override
//	// Visita a regra expr: ID
//	public AST visitExprId(ExprIdContext ctx) {
//		// Fim da recurs??o, retorna um n?? de 'var use'.
//		return checkVar(ctx.ID().getSymbol());
//	}

}
