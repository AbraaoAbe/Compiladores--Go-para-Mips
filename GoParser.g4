parser grammar GoParser;

@header{
    package parser;
}


options {
	tokenVocab = GoLexer;
	superClass = GoParserBase;
}

//Antigo sourceFile 
sourceFile:
	( (functionDecl eos) | (varDecl) | (assignment eos)
	)* EOF;
//	| ((methodDecl) eos)* EOF #methDeclLoop;


//!MODIFICACAO CANCELADA
//?Modifiquei o sourceFile com intuito de
//?facilicar a AST, ainda nao testado
//?Para criar uma regra root sem looping
//sourceFile:
//	functionDecl eos EOF #funcDecl
//	| methodDecl eos EOF #methDecl
//	| declaration eos EOF #Declvar;

//DECLARACAO DE CONSTANTES, TIPOS CONJUNTOS E VARIAVEIS SIMPLES
//Pelo que ta escrito no lab5 nao precisa ser sobrescrito
//declaration: 
//	constDecl
//	typeDecl
//	varDecl;

//constDecl:
//	CONST constSpec #constSpecUniq
//	| CONST L_PAREN (constSpec eos)* R_PAREN #constSpecLoop;

//constSpec: identifierList (type_? ASSIGN expressionList)?;

identifierList: IDENTIFIER (COMMA IDENTIFIER)*;

expressionList: expression (COMMA expression)*;

//typeDecl:
//	TYPE typeSpec #typeDeclUnic
//	|TYPE L_PAREN (typeSpec eos)* R_PAREN #typeDeclLoop;
//
//typeSpec: IDENTIFIER ASSIGN? type_;


// DECLARACAO DE FUNCAO

functionDecl: FUNC IDENTIFIER (signature block?);

//methodDecl: FUNC receiver IDENTIFIER ( signature block?);
//
//receiver: parameters;

//varDecl: VAR (varSpec | L_PAREN (varSpec eos)* R_PAREN);

varDecl: VAR varSpec;

varSpec:
	identifierList type_;
 	// var a, b, x int = 1, 2, 3

//BLOCO DE STATUS
block: L_CURLY statementList R_CURLY;

statementList: (statement eos?)+;
//statementList: ((SEMI? | {closingBracket()}?) statement)+;

//STATUS
statement:
	varDecl #stmtVarDecl
	| simpleStmt #stmtSimple
	| returnStmt #stmtReturn
	| BREAK #stmtBreak
	| CONTINUE #stmtContinue
	| block #stmtBlock
	| ifStmt #stmtIf
	| forStmt #stmtFor
//	| ioStmt
	;

simpleStmt:
//	incDecStmt 
	ioStmt
	| assignment
	| expressionStmt
//	| shortVarDecl
	;

ioStmt: (SCAN paramsCaller) #scanIO
        | (PRINT paramsCaller) #printIO;

expressionStmt: expression;

incDecStmt: expression (PLUS_PLUS | MINUS_MINUS);

//assignment: expressionList assign_op expressionList;
//alterado o assignment pois do jeito que tava, aceitava maluquice: 1 = 2
//index? indica o acesso ao arrayASSIGN
assignment: IDENTIFIER index? ASSIGN expression;


//DECLARACAO IMPLICITA x := 0 (FAZ ELE SER INT)
//shortVarDecl: identifierList DECLARE_ASSIGN expressionList;

emptyStmt: EOS | SEMI;

//labeledStmt: IDENTIFIER COLON statement?;

returnStmt: RETURN expression?;

//breakStmt: BREAK;

//continueStmt: CONTINUE;

//fallthroughStmt: FALLTHROUGH;

//CLAUSULA DO IF 
ifStmt:
	IF ( expression  ) block eos? ( ELSE block )?;

//CLAUSULA DO IF
//ifStmt:
//	IF ( expression | eos expression | simpleStmt eos expression )
//		block ( ELSE (ifStmt | block) )?;


//CLAUSULA DO SWITCH E TYPESWITCH 
//switchStmt: exprSwitchStmt | typeSwitchStmt;

//exprSwitchStmt:
//	SWITCH (expression?
//					| simpleStmt? eos expression?
//					) L_CURLY exprCaseClause* R_CURLY;

//exprCaseClause: exprSwitchCase COLON statementList?;

//exprSwitchCase: CASE expressionList | DEFAULT;

//typeSwitchStmt:
//	SWITCH ( typeSwitchGuard
//					| eos typeSwitchGuard
//					| simpleStmt eos typeSwitchGuard)
//					 L_CURLY typeCaseClause* R_CURLY;

//typeSwitchGuard: primaryExpr DOT L_PAREN TYPE R_PAREN;
//typeSwitchGuard: (IDENTIFIER DECLARE_ASSIGN)? primaryExpr DOT L_PAREN TYPE R_PAREN;

//typeCaseClause: typeSwitchCase COLON statementList?;

//typeSwitchCase: CASE typeList | DEFAULT;

//typeList: (type_) (COMMA (type_))*;
//typeList: (type_ | NIL_LIT) (COMMA (type_ | NIL_LIT))*;

// Removi o DECLARE_ASSIGN do lexer
recvStmt: (expressionList ASSIGN)? recvExpr = expression;
//recvStmt: (expressionList ASSIGN | identifierList DECLARE_ASSIGN)? recvExpr = expression;

//CLAUSULA DO FOR()

//forStmt: FOR (expression? | forClause | rangeClause?) block;
//forStmt: FOR (expression? | forClause) block;
forStmt: FOR forClause block;

forClause:
	initStmt = simpleStmt? eos expression? eos postStmt = simpleStmt?;

//CLAUSULA DO RANGE
// Removi o DECLARE_ASSIGN do lexer
//rangeClause: (
//		expressionList ASSIGN
////		| identifierList DECLARE_ASSIGN
//	)? RANGE expression;

type_: typeName | arrayType;

arrayType: L_BRACKET arrayLength R_BRACKET typeName;

arrayLength: DECIMAL_LIT;

typeName: INT #intType
		| FLOAT #floatType
		| STRING #stringType
		| BOOL #boolType
		;



//typeLit:
//	arrayType
//	| structType
//	| functionType;
//	| sliceType;
//	| mapType;

methodSpec:
//	IDENTIFIER parameters result
	IDENTIFIER parameters parameters
	| IDENTIFIER parameters;

//functionType: FUNC signature;
// func main()
// fmt.Println()
// fmt.Scan()

//chamada de funcao
functionCaller: IDENTIFIER paramsCaller;

//parametros da chamada de funcao
paramsCaller: L_PAREN expressionList? R_PAREN;

signature:
	parameters type_?;
    // os parâmetros da função mais o tipo do retorno, caso não tenho tipo do retorn a função não retorna nada
//result: parameters | type_;

parameters:
	L_PAREN (parameterDecl (COMMA parameterDecl)* COMMA?)? R_PAREN;

//parameterDecl: identifierList? type_;
parameterDecl: IDENTIFIER type_;


//REMOVIDO: unary_op (nao faz sentido pra mim )
//REMOVIDO: primaryExpr (muito amplo para 4 tipos primitivos e 1 tipo array)

	//!SIMPLIFICADO O add_op
	//| expression add_op = ( PLUS | MINUS | OR | CARET ) expression
	//primaryExpr
	//| unary_op = ( PLUS | MINUS | EXCLAMATION | CARET | TIMES ) expression
// Já foi feito os visitors
expression:
	//primaryExpr #primaryExp
	bool_v = (FALSE | TRUE) 		#boolLit
	| expression mul_op = ( TIMES| DIV | MOD ) expression # timesOver
	| expression add_op = ( PLUS | MINUS ) expression # plusMinus
	| expression rel_op = ( EQUALS | NOT_EQUALS | LESS | LESS_OR_EQUALS | GREATER | GREATER_OR_EQUALS ) expression # eqLt
	| expression LOGICAL_AND expression # relAnd
	| expression LOGICAL_OR expression #relOr
	| L_PAREN expression R_PAREN	# exprPar
	| DECIMAL_LIT   				#integerLit
	| str_v = (RAW_STRING_LIT | INTERPRETED_STRING_LIT)   #stringLit
	| FLOAT_LIT        				#floatLit
//	| functionLit					#functionLitval
	| functionCaller                #funcCaller
	| IDENTIFIER index?					#exprId
	;

//functionLit: IDENTIFIER arguments ; // func() call()

//index: L_BRACKET DECIMAL_LIT R_BRACKET;
index: L_BRACKET expression R_BRACKET;

arguments:
	L_PAREN (
		(expressionList | (COMMA expressionList)?) COMMA?
	)? R_PAREN;


//methodExpr: nonNamedType DOT IDENTIFIER;

//receiverType: typeName | '(' ('*' typeName | receiverType) ')';

//receiverType: type_;

eos:
	SEMI
	| EOF
	| EOS
	| {closingBracket()}?
	;