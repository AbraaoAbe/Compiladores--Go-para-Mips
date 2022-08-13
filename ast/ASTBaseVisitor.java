package ast;

/*
 * Classe abstrata que define a interface do visitor para a AST.
 * Implementa o despacho do método 'visit' conforme o 'kind' do nó.
 * Com isso, basta herdar desta classe para criar um interpretador
 * ou gerador de código.
 */
public abstract class ASTBaseVisitor<T> {

	// Único método público. Começa a visita a partir do nó raiz
	// passado. Precisa ter outro nome porque tem a mesma assinatura
	// que o método "genérico" 'visit'.
	public void execute(AST root) {
		visit(root);
	}
	
	// Método "genérico" que despacha a visitação para os métodos
	// especializados conforme o 'kind' do nó atual. Igual ao código
	// em C. Novamente fica o argumento sobre usar OO ou não aqui.
	// Se tivéssemos trocentas classes especializando o nó da AST
	// esse despacho seria feito pela JVM. Aqui precisa fazer na mão.
	// Por outro lado, assim não precisa de trocentas classes com o
	// código todo espalhado entre elas...
	protected T visit(AST node) {
		switch(node.kind) {
	        case ASSIGN_NODE:           return visitAssign(node);
			case INT_VAL_NODE:          return visitIntVal(node);
//	        case MINUS_NODE:            return visitMinus(node);
	        // case DIV_NODE:              return visitDiv(node);
            // case MOD_NODE:              return visitMod(node);
	        case PLUS_NODE:             return visitPlus(node);
	        case PROGRAM_NODE:          return visitProgram(node);
	        // case REAL_VAL_NODE:         return visitRealVal(node);
	        // case STR_VAL_NODE:          return visitStrVal(node);
	        // case TIMES_NODE:            return visitTimes(node);
            // case LESS_NODE:             return visitLess(node);
            // case GREATER_NODE:          return visitGreater(node);
            // case LESS_OR_EQUALS_NODE:   return visitLess_Equals(node);
            // case GREATER_OR_EQUALS_NODE:return visitGreater_Equals(node);
	        case VAR_DECL_NODE:         return visitVarDecl(node);
	        case VAR_LIST_NODE:         return visitVarList(node);
	        case VAR_USE_NODE:          return visitVarUse(node);
            // case FUNC_DECL_NODE:        return visitFunc_Decl(node);
            // case FUNC_USE_NODE:         return visitFunc_Use(node);
            // case RETURN_NODE:           return visitReturn(node);
            // case PARAMS_LIST_NODE:      return visitParams_List(node);
            // case PARAMS_NODE:           return visitParams(node);
	
	        // case B2I_NODE:              return visitB2I(node);
	        // case B2R_NODE:              return visitB2R(node);
	        // case B2S_NODE:              return visitB2S(node);
	        // case I2R_NODE:              return visitI2R(node);
	        // case I2S_NODE:              return visitI2S(node);
	        // case R2S_NODE:              return visitR2S(node);
            // case R2I_NODE:              return visitR2I(node);

            // case PRINT_NODE:            return visitPrint(node);
            // case SCAN_NODE:             return visitScan(node);
            // case ARRAY_NODE:            return visitArray(node);
            // case FUNC_NODE:             return visitFunc(node);
            
            // case AND_NODE:              return visitAnd(node);
            // case OR_NODE:               return visitOr(node);
            // case NEQ_NODE:              return visitNeq(node);
            // case STMT_LIST_NODE:        return visitStmt_list(node);
            // case BREAK_NODE:            return visitBreak(node);
            // case INDEX_NODE:            return visitIndex(node);


	
	        default:
	            System.err.printf("Invalid kind: %s!\n", node.kind.toString());
	            System.exit(1);
	            return null;
		}
	}
	
	// Métodos especializados para visitar um nó com um certo 'kind'.

	protected abstract T visitAssign(AST node);
	// protected abstract T visitEq(AST node);
	// protected abstract T visitBlock(AST node);
	// protected abstract T visitBoolVal(AST node);
	// protected abstract T visitIf(AST node);
    // protected abstract T visitFor(AST node);
	protected abstract T visitIntVal(AST node);
	// protected abstract T visitMinus(AST node);
	// protected abstract T visitDiv(AST node);
    // protected abstract T visitMod(AST node);
	protected abstract T visitPlus(AST node);
	protected abstract T visitProgram(AST node);
	// protected abstract T visitRealVal(AST node);
	// protected abstract T visitStrVal(AST node);
	// protected abstract T visitTimes(AST node);
    // protected abstract T visitLess(AST node);
    // protected abstract T visitGreater(AST node);
    // protected abstract T visitLess_Equals(AST node);
    // protected abstract T visitGreater_Equals(AST node);
	protected abstract T visitVarDecl(AST node);
	protected abstract T visitVarList(AST node);
	protected abstract T visitVarUse(AST node);
    // protected abstract T visitFunc_Decl(AST node);
    // protected abstract T visitFunc_Use(AST node);
    // protected abstract T visitReturn(AST node);
    // protected abstract T visitParams_List(AST node);
    // protected abstract T visitParams(AST node);

	// protected abstract T visitB2I(AST node);
	// protected abstract T visitB2R(AST node);
	// protected abstract T visitB2S(AST node);
	// protected abstract T visitI2R(AST node);
	// protected abstract T visitI2S(AST node);
	// protected abstract T visitR2S(AST node);
    // protected abstract T visitR2I(AST node);

    // protected abstract T visitPrint(AST node);
    // protected abstract T visitScan(AST node);
    // protected abstract T visitArray(AST node);
    // protected abstract T visitFunc(AST node);
    // protected abstract T visitAnd(AST node);
    // protected abstract T visitOr(AST node);
    // protected abstract T visitNeq(AST node);
    // protected abstract T visitStmt_list(AST node);
    // protected abstract T visitBreak(AST node);
    // protected abstract T visitIndex(AST node);

}	

