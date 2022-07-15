package checker;

import static ast.NodeKind.*;
import static typing.Conv.I2R;
import static typing.Type.BOOL_TYPE;
import static typing.Type.INT_TYPE;
import static typing.Type.NO_TYPE;
import static typing.Type.FLOAT_TYPE;
import static typing.Type.STRING_TYPE;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import ast.AST;
import ast.NodeKind;
import parser.GoLexer;
import parser.GoParser;
import parser.GoParser.SourceFileContext;
import parser.GoParser.FunctionDeclContext;
import parser.GoParser.BlockContext;
import parser.GoParser.StatementListContext;
import parser.GoParser.AssignmentContext;
//import parser.GoParser.Assign_stmtContext;
import parser.GoParser.VarDeclContext;
import parser.GoParser.EqLtContext;
//import parser.GoParser.ExprFalseContext;
import parser.GoParser.BoolLitContext;
import parser.GoParser.RelAndContext;
import parser.GoParser.RelOrContext;
import parser.GoParser.IntegerLitContext;
import parser.GoParser.FloatLitContext;
import parser.GoParser.StringLitContext;
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

import java.util.Objects;

/*
 * Analisador semântico de EZLang implementado como um visitor
 * da ParseTree do ANTLR. A classe EZParserBaseVisitor é gerada
 * automaticamente e já possui métodos padrão aonde o comportamento
 * é só visitar todos os filhos. Por conta disto, basta sobreescrever
 * os métodos que a gente quer alterar. Neste caso, todos foram sobreescritos.
 *
 * No laboratório anterior, foi usado Type no tipo genérico do
 * EZParserBaseVisitor porque a gente só estava fazendo uma verificação
 * simples dos tipos primitivos. Agora o tipo declarado é AST, pois o
 * analisador semântico também realiza a construção da AST na mesma passada.
 * Assim, se a análise semântica (uso de variáveis e tipos) terminar sem erros,
 * então temos no final uma AST que representa o programa de entrada.
 * Em linguagens mais complexas é provável que sejam necessárias mais passadas,
 * por exemplo, uma para análise semântica e outra para a construção da AST.
 * Neste caso, talvez você tenha de implementar dois visitadores diferentes.
 *
 * Lembre que o caminhamento pela Parse Tree é top-down. Assim, é preciso sempre
 * visitar os filhos de um nó primeiro para construir as subárvores dos filhos.
 * No Bison isso já acontecia automaticamente porque o parsing lá é bottom-up e
 * as ações semânticas do parser já faziam a construção da AST junto com a análise
 * sintática. Aqui, é o inverso, por isso temos que visitar os filhos primeiro.
 */
public class SemanticChecker extends GoParserBaseVisitor<AST> {

	private StrTable st = new StrTable();   // Tabela de strings.
    private VarTable vt = new VarTable();   // Tabela de variáveis.
	private FuncTable ft = new FuncTable();

    Type lastDeclType;  // Variável "global" com o último tipo declarado.

    AST root; // Nó raiz da AST sendo construída.

    // Testa se o dado token foi declarado antes.
    // Se sim, cria e retorna um nó de 'var use'.
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

    // Cria uma nova variável a partir do dado token.
    // Retorna um nó do tipo 'var declaration'.
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
        idx = vt.addVar(text, line, NO_TYPE);
        return new AST(VAR_DECL_NODE, idx, NO_TYPE);
    }

	// Testa se o dado token foi declarado antes.
    // Se sim, cria e retorna um nó de 'var use'.
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
    	return new AST(FUNC_USE_NODE, idx, ft.getType(idx));
    }

    // Cria uma nova variável a partir do dado token.
    // Retorna um nó do tipo 'var declaration'.
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
        idx = ft.addFunc(text, line, NO_TYPE);
        return new AST(FUNC_DECL_NODE, idx, NO_TYPE);
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

    // Exibe o conteúdo das tabelas em stdout.
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
    void printAST() {
    	AST.printDot(root, vt);
    }

    // ----------------------------------------------------------------------------
    // Visitadores.
	
	// Visita a regra sourceFile: ((functionDecl) eos)* EOF #funcDeclLoop
	//							| ((methodDecl) eos)* EOF #methDeclLoop
	//							| ((declaration) eos)* EOF #DeclvarLoop;
    @Override
	public AST visitSourceFile(SourceFileContext ctx) {
    	this.root = AST.newSubtree(PROGRAM_NODE, NO_TYPE);
    	// No caso de não-terminais com fechos (* ou +), a chamada do método
    	// correspondente retornatype_ uma lista com todos os elementos da Parse
    	// Tree que entraram no fecho. Assim, podemos percorrer (visitar) a
    	// lista para construir as subárvores dos filhos.
    	// Também é possível usar o iterador da lista aqui mas prefiro esse
    	// estilo de loop clássico...

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
    	
    	// Como esta é a regra inicial, chegamos na raiz da AST.
		return this.root;
	}

	//visita a regra functionDecl: FUNC IDENTIFIER (signature block?);
	@Override
	public AST visitFunctionDecl(FunctionDeclContext ctx) {
    	

		
    	
		AST func = newFunc(ctx.IDENTIFIER().getSymbol()); // Precisa pegar o tipo da funcao

		AST sig = visit(ctx.signature());

		AST block = visit(ctx.block());

		//func.addChild(sig);
		func.addChild(block);
		

		return func;
	
	}

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
            // System.out.printf("[visitBlock] Caiu exception statementList [%s]\n",e.toString());
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

	
	

    // Visita a regra vars_sect: VAR var_decl*ctx.IDENTIFIER(i)PE);
    // 	// No caso de não-terminais com fechos (* ou +), a chamada do método
    // 	// correspondente retorna uma lista com todos os elementos da Parse
    // 	// Tree que entraram no fecho. Assim, podemos percorrer (visitar) a
    // 	// lista para construir as subárvores dos filhos.
    // 	// Também é possível usar o iterador da lista aqui mas prefiro esse
    // 	// estilo de loop clássico...
    // 	for (int i = 0; i < ctx.var_decl().size(); i++) {
    // 		AST child = visit(ctx.var_decl(i));
    // 		node.addChild(child);
    // 	}
    // 	return node;
	// }

	// Visita a regra varDecl: VAR varSpec ;
    

	// varSpec:
	// identifierList type_ (ASSIGN expressionList)?;
	// Agora a regra está assim
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
			//vartable
			vt.setType(child.intData, lastDeclType);
    	}
    	
    	return idList;
	}

	//identifierList: IDENTIFIER (COMMA IDENTIFIER)*;
	@Override
	public AST visitIdentifierList(GoParser.IdentifierListContext ctx) {
    
		AST varList = AST.newSubtree(VAR_LIST_NODE, NO_TYPE);
    	
		for (int i = 0; i < ctx.IDENTIFIER().size(); i++) {
    		AST node = newVar(ctx.IDENTIFIER(i).getSymbol()); // Precisa pegar o tipo da variável
			varList.addChild(node);
    	}
    	
    	return varList;
	}

	// Visita a regra typeName: BOOL
    @Override
    public AST visitBoolType(GoParser.BoolTypeContext ctx) {
    	this.lastDeclType = Type.BOOL_TYPE;
    	// Não tem problema retornar null aqui porque o método chamador
    	// ignora o valor de retorno.
    	return null;
    }

	// Visita a regra typeName: INT
	@Override
	public AST visitIntType(GoParser.IntTypeContext ctx) {
		this.lastDeclType = Type.INT_TYPE;
		// Não tem problema retornar null aqui porque o método chamador
    	// ignora o valor de retorno.
		return null;
	}

	@Override
	public AST visitStringType(GoParser.StringTypeContext ctx){
		this.lastDeclType = Type.STRING_TYPE;
//
//		this.st.add(ctx.string_().getStop().getText());

    	return null; // Java says must return something even when Void	
	}

	// Visita a regra typeName: FLOAT
	@Override
	public AST visitFloatType(GoParser.FloatTypeContext ctx) {
		this.lastDeclType = Type.FLOAT_TYPE;
		// Não tem problema retornar null aqui porque o método chamador
    	// ignora o valor de retorno.
		return null;
	}

	//!MUDAR ARRAY TYPE
	@Override
	public AST visitArrayType(GoParser.ArrayTypeContext ctx){
		this.lastDeclType = Type.ARRAY_TYPE;
    	return null; // Java says must return something even when Void
	}

	// Visita a regra assignment: IDENTIFIER index? ASSIGN expression;
	@Override
	public AST visitAssignment(AssignmentContext ctx) {
		// Visita a expressão da direita.
		AST exprNode = visit(ctx.expression());
		// Visita o identificador da esquerda.
		Token idToken = ctx.IDENTIFIER().getSymbol();
		AST idNode = checkVar(idToken);
		// Faz as verificações de tipos.
		return checkAssign(idToken.getLine(), idNode, exprNode);
	}

    // Visita a regra typeDecl: type_spec ID SEMI
    // @Override
    // public AST visitTypeDecl(GoParser.TypeDeclContext ctx) {
    // 	// Visita a definição do tipo da variável.
    // 	visit(ctx.type_spec());
    // 	// Cria e retorna um nó para a variável.
    // 	return newVar(ctx.ID().getSymbol());
    // }

    // Visita a regra stmt_sect: BEGIN stmt+ END
//    @Override
//	public AST visitStmt_sect(Stmt_sectContext ctx) {
//    	// Valem os mesmos comentários de visitVars_sect.
//    	AST node = AST.newSubtree(BLOCK_NODE, NO_TYPE);
//    	for (int i = 0; i < ctx.stmt().size(); i++) {
//    		AST child = visit(ctx.stmt(i));
//    		node.addChild(child);
//    	}
//    	return node;
//	}

    // Visita a regra stmt para todos os possíveis tipos.
    // Esse método não precisava ser sobreescrito, mas era o único
    // que ficou faltando... :P
//	@Override
//	public AST visitStmt(StmtContext ctx) {
//		return super.visitStmt(ctx);
//	}

	// Visita a regra assign_stmt: ID ASSIGN expr SEMI
//	@Override
//	public AST visitVarDecl(VarDeclContext ctx) {
//		// Visita a expressão da direita.
//		AST exprNode = visit(ctx.expr());
//		// Visita o identificador da esquerda.
//		Token idToken = ctx.ID().getSymbol();
//		AST idNode = checkVar(idToken);
//		// Faz as verificações de tipos.
//		return checkAssign(idToken.getLine(), idNode, exprNode);
//	}

	// Método auxiliar criado só para separar melhor a parte de visitador
	// da parte de análise semântica.
    private static AST checkAssign(int lineNo, AST l, AST r) {
    	Type lt = l.type;
    	Type rt = r.type;

        if (lt == BOOL_TYPE && rt != BOOL_TYPE) typeError(lineNo, ":=", lt, rt);
        if (lt == STRING_TYPE  && rt != STRING_TYPE)  typeError(lineNo, ":=", lt, rt);
        if (lt == INT_TYPE  && rt != INT_TYPE)  typeError(lineNo, ":=", lt, rt);

        if (lt == FLOAT_TYPE) {
        	if (rt == INT_TYPE) {
        		r = Conv.createConvNode(I2R, r);
        	} else if (rt != FLOAT_TYPE) {
        		typeError(lineNo, ":=", lt, rt);
            }
        }

        return AST.newSubtree(ASSIGN_NODE, NO_TYPE, l, r);
    }

	// Visita a regra if_stmt: IF expr THEN stmt+ (ELSE stmt+)? END
	@Override
	public AST visitIfStmt(GoParser.IfStmtContext ctx) {
		// Esse é o método mais complicado da AST. A complicação surge
		// principalmente porque o bloco de ELSE é opcional, e por conta
		// dos dois fechos 'stmt+', pois o ANTLR não faz distinção entre eles.
		// Assim, comandos dos blocos de THEN e ELSE ficam todos na mesma
		// lista de 'stmt'. Pelo menos eles ficam em ordem.

		// Uma forma mais simples de resolver esse problema seria criar
		// dois novos não-terminais como
		// then_part: stmt+
		// else_part: stmt+
		// Assim, daria para identificar e usar as partes com métodos diferentes.

		// Preferi não fazer desse jeito porque acho que vocês vão ter casos
		// parecidos nas gramáticas, e assim dá para aproveitar e mostar um pouco
		// da magia negra que dá para fazer com o ANTLR... :P

		// Analisa a expressão booleana.
		AST exprNode = visit(ctx.expression());
		checkBoolExpr(ctx.IF().getSymbol().getLine(), "if", exprNode.type);

		// Vamos dividir o código em duas partes, um IF sem ou com ELSE.
		// Para saber se um símbolo opcional está presente, basta chamar o
		// seu método. Se for retornado nulo, o símbolo não existe.
		if (ctx.ELSE() == null) {
			// Caso em que não existe um bloco de ELSE. Aí fica simples
			// porque todos os comandos pertencem ao bloco do THEN.
			AST thenNode = AST.newSubtree(BLOCK_NODE, NO_TYPE);
			for (int i = 0; i < ctx.block().size(); i++) {
	    		AST child = visit(ctx.block(i));
	    		thenNode.addChild(child);
			}
			return AST.newSubtree(IF_NODE, NO_TYPE, exprNode, thenNode);
		} else {
			// Caso em que existe um bloco de ELSE. Aí precisamos separar
			// os 'stmt' entre os blocos de THEN e ELSE. Vamos usar o
			// Token do ELSE para fazer essa separação. Mas para isso
			// precisamos identificar o índice do ELSE na lista de todos
			// os filhos da Parse Tree.

			// Faz uma busca pelo token na lista de filhos.
			TerminalNode elseToken = ctx.ELSE();
			int elseIdx = -1;
			for (int i = 0; i < ctx.children.size(); i++) {
				if (ctx.children.get(i).equals(elseToken)) {
					elseIdx = i;
					break;
				}
			}

			// Temos que elseIdx é o índice na lista de todos os filhos.
			// Por outro lado, a lista de 'stmts' começa do índice zero.
			// O offset entre as duas listas é 3 porque a regra começa com
			// IF expr THEN
			// ou seja, há 3 símbolos antes do primeiro bloco de 'stmt+'.
			int thenEnd = elseIdx - 3;

			// Cria o nó com o bloco de comandos do THEN.
			AST thenNode = AST.newSubtree(BLOCK_NODE, NO_TYPE);
			for (int i = 0; i < thenEnd; i++) {
	    		AST child = visit(ctx.block(i));
	    		thenNode.addChild(child);
			}

			// Cria o nó com o bloco de comandos do ELSE.
			AST elseNode = AST.newSubtree(BLOCK_NODE, NO_TYPE);
			for (int i = thenEnd; i < ctx.block().size(); i++) {
	    		AST child = visit(ctx.block(i));
	    		elseNode.addChild(child);
			}

			return AST.newSubtree(IF_NODE, NO_TYPE, exprNode, thenNode, elseNode);
		}
	}

	// Visita a regra read_stmt: READ ID SEMI
	// ----
	// Visita o ioStmt:scanIo
	@Override
	public AST visitScanIO(GoParser.ScanIOContext ctx) {
//		AST idNode = checkVar(ctx.ID().getSymbol());
		AST exprNode = visit(ctx.arguments());
		return AST.newSubtree(SCAN_NODE, NO_TYPE, exprNode);
	}
	// Visita o ioStmt:printIo
	@Override
	public AST visitPrintIO(GoParser.PrintIOContext ctx) {
//		AST idNode = checkVar(ctx.ID().getSymbol());
		AST exprNode = visit(ctx.arguments());
		return AST.newSubtree(PRINT_NODE, NO_TYPE, exprNode);
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
		// Visita recursivamente as duas subexpressões.
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));

		// Faz a unificação dos tipos para determinar o tipo resultante.
		Type lt = l.type;
		Type rt = r.type;
		Unif unif = lt.unifyOtherArith(rt);

		if (unif.type == NO_TYPE) {
			typeError(ctx.mul_op.getLine(), ctx.mul_op.getText(), lt, rt);
		}

		// Cria os nós de conversão que forem necessários segundo a
		// estrutura de conversão.
		l = Conv.createConvNode(unif.lc, l);
		r = Conv.createConvNode(unif.rc, r);

		// Olha qual é o operador e cria o nó correspondente na AST.
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
		// Visita recursivamente as duas subexpressões.
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));

		// Faz a unificação dos tipos para determinar o tipo resultante.
		Type lt = l.type;
		Type rt = r.type;
		Unif unif;
		// É preciso diferenciar '+' e '-' na unificação por conta da semântica.
		if (ctx.add_op.getType() == GoParser.PLUS) {
			unif = lt.unifyPlus(rt);
		} else { // MINUS
			unif = lt.unifyOtherArith(rt);
		}

		if (unif.type == NO_TYPE) {
			typeError(ctx.add_op.getLine(), ctx.add_op.getText(), lt, rt);
		}

		// Cria os nós de conversão que forem necessários segundo a
		// estrutura de conversão.
		l = Conv.createConvNode(unif.lc, l);
		r = Conv.createConvNode(unif.rc, r);

		// Olha qual é o operador e cria o nó correspondente na AST.
		if (ctx.add_op.getType() == GoParser.PLUS) {
			return AST.newSubtree(PLUS_NODE, unif.type, l, r);
		} else { // MINUS
			return AST.newSubtree(MINUS_NODE, unif.type, l, r);
		}
	}

	// Visita a regra expr: expr op=(EQ | LT) expr
	@Override
	public AST visitEqLt(EqLtContext ctx) {
		// Visita recursivamente as duas subexpressões.
		AST l = visit(ctx.expression(0));
		AST r = visit(ctx.expression(1));

		// Faz a unificação dos tipos para determinar o tipo resultante.
		Type lt = l.type;
		Type rt = r.type;
		Unif unif = lt.unifyComp(rt);

		if (unif.type == NO_TYPE) {
			typeError(ctx.rel_op.getLine(), ctx.rel_op.getText(), lt, rt);
		}

		// Cria os nós de conversão que forem necessários segundo a
		// estrutura de conversão.
		l = Conv.createConvNode(unif.lc, l);
		r = Conv.createConvNode(unif.rc, r);

		// Olha qual é o operador e cria o nó correspondente na AST.
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

		// Cria os nós de conversão que forem necessários segundo a
		// estrutura de conversão.
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

		// Cria os nós de conversão que forem necessários segundo a
		// estrutura de conversão.
		l = Conv.createConvNode(unif.lc, l);
		r = Conv.createConvNode(unif.rc, r);

		return AST.newSubtree(AND_NODE, unif.type, l, r);
	}


	// Visita a regra expr: TRUE
	@Override
//	public AST visitExprTrue(ExprTrueContext ctx) {
//		// Fim da recursão, representa 'true' como o inteiro '1'.
//		return new AST(BOOL_VAL_NODE, 1, BOOL_TYPE);
//	}
//
//	// Visita a regra expr: FALSE
//	@Override
//	public AST visitExprFalse(ExprFalseContext ctx) {
//		// Fim da recursão, representa 'false' como o inteiro '0'.
//		return new AST(BOOL_VAL_NODE, 0, BOOL_TYPE);
//	}
	// Esse visitExprTrue ou False são convertido nesse visitor
	public AST visitBoolLit(BoolLitContext ctx){
		if (ctx.bool_v.getType() == GoParser.FALSE){ // checa se o valor de bool é false
			return new AST(BOOL_VAL_NODE, 0,BOOL_TYPE);
		} else{
			return new AST(BOOL_VAL_NODE, 1,BOOL_TYPE);
		}
	}

	// Visita a regra expr: INT_VAL
	@Override
	public AST visitIntegerLit(GoParser.IntegerLitContext ctx) {
		// Fim da recursão, converte o lexema da constante inteira.
		int intData = Integer.parseInt(ctx.getText());
		return new AST(INT_VAL_NODE, intData, INT_TYPE);
	}

	// Visita a regra expr: REAL_VAL
	@Override
	public AST visitFloatLit(GoParser.FloatLitContext ctx) {
		// Fim da recursão, converte o lexema da constante real.
		float floatData = Float.parseFloat(ctx.getText());
		return new AST(REAL_VAL_NODE, floatData, FLOAT_TYPE);
	}

	@Override
	// Visita a regra expr: STR_VAL
	public AST visitStringLit(GoParser.StringLitContext ctx) {
		// Adiciona a string na tabela de strings.
		String value = ctx.str_v.getText();
		int idx = st.addStr(value);
		// Campo 'data' do nó da AST guarda o índice na tabela.
		return new AST(STR_VAL_NODE, idx, STRING_TYPE);
	}

//	@Override
//	// Visita a regra expr: ID
//	public AST visitExprId(ExprIdContext ctx) {
//		// Fim da recursão, retorna um nó de 'var use'.
//		return checkVar(ctx.ID().getSymbol());
//	}

}
