parser grammar GoParser;

options {
	tokenVocab = GoLexer;
	superClass = GoParserBase;
}

//Antigo sourceFile 
sourceFile:
	((functionDecl) eos)* EOF #funcDeclLoop
//	| ((methodDecl) eos)* EOF #methDeclLoop
	| ((varDecl) eos)* EOF #declvarLoop;


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

//TIPO VAR (DEIXA OU TIRA?)
//varDecl: VAR (varSpec | L_PAREN (varSpec eos)* R_PAREN);

varDecl: VAR varSpec;

varSpec:
	identifierList type_ (ASSIGN expressionList)?;
 	// var a, b, x int = 1, 2, 3

//BLOCO DE STATUS
block: L_CURLY statementList? R_CURLY;

statementList: (statement eos?)+;
//statementList: ((SEMI? | {closingBracket()}?) statement)+;

//STATUS
statement:
	varDecl
	| labeledStmt
	| simpleStmt
	| returnStmt
	| breakStmt
	| continueStmt
//	| fallthroughStmt
	| block
	| ifStmt
//	| switchStmt
	| forStmt;

simpleStmt:
	incDecStmt
	| ioStmt
	| assignment
	| expressionStmt
//	| shortVarDecl
	;

ioStmt: (SCAN arguments) #scanIO
        | (PRINT arguments) #printIO;

expressionStmt: expression;

incDecStmt: expression (PLUS_PLUS | MINUS_MINUS);

assignment: expressionList assign_op expressionList;

assign_op: (
		PLUS
		| MINUS
		| OR
		| CARET
		| STAR
		| DIV
		| MOD
	)? ASSIGN;

//DECLARACAO IMPLICITA x := 0 (FAZ ELE SER INT)
//shortVarDecl: identifierList DECLARE_ASSIGN expressionList;

emptyStmt: EOS | SEMI;

labeledStmt: IDENTIFIER COLON statement?;

returnStmt: RETURN expressionList?;

breakStmt: BREAK IDENTIFIER?;

continueStmt: CONTINUE IDENTIFIER?;

//fallthroughStmt: FALLTHROUGH;

//CLAUSULA DO IF 
ifStmt:
	IF ( expression
			| eos expression
			| simpleStmt eos expression
			) block (
		ELSE (ifStmt | block)
	)?;

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
forStmt: FOR (expression? | forClause) block;

forClause:
	initStmt = simpleStmt? eos expression? eos postStmt = simpleStmt?;

//CLAUSULA DO RANGE
// Removi o DECLARE_ASSIGN do lexer
//rangeClause: (
//		expressionList ASSIGN
////		| identifierList DECLARE_ASSIGN
//	)? RANGE expression;

type_: typeName | arrayType;

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

arrayType: L_BRACKET arrayLength R_BRACKET elementType;

arrayLength: expression;

elementType: type_;

//sliceType: L_BRACKET R_BRACKET elementType;

//mapType: MAP L_BRACKET type_ R_BRACKET elementType;

methodSpec:
//	IDENTIFIER parameters result
	IDENTIFIER parameters parameters
	| IDENTIFIER parameters;

functionType: FUNC signature;
// func main()
// fmt.Println()
// fmt.Scan()

signature:
//	parameters result
	parameters type_?;
    // os parâmetros da função mais o tipo do retorno, caso não tenho tipo do retorn a função não retorna nada
//result: parameters | type_;

parameters:
	L_PAREN (parameterDecl (COMMA parameterDecl)* COMMA?)? R_PAREN;

//parameterDecl: identifierList? type_;
parameterDecl: identifierList (basicLit | compositeLit);

expression:
	primaryExpr
	| unary_op = (
		PLUS
		| MINUS
		| EXCLAMATION
		| CARET
		| STAR
	) expression
	| expression mul_op = (
		STAR
		| DIV
		| MOD
	) expression
	| expression add_op = (PLUS | MINUS | OR | CARET) expression
	| expression rel_op = (
		EQUALS
		| NOT_EQUALS
		| LESS
		| LESS_OR_EQUALS
		| GREATER
		| GREATER_OR_EQUALS
	) expression
	| expression LOGICAL_AND expression
	| expression LOGICAL_OR expression;

primaryExpr:
	operand
//	| conversion
//	| methodExpr
	| primaryExpr (
		(DOT IDENTIFIER)
		| index
//		| slice_
//		| typeAssertion
		| arguments
	);


//conversion: nonNamedType L_PAREN expression COMMA? R_PAREN;
//
//nonNamedType: typeLit | L_PAREN nonNamedType R_PAREN;

operand: literal | operandName | L_PAREN expression R_PAREN;

literal: basicLit | compositeLit | functionLit;

basicLit:
//	NIL_LIT     #nullType
	integer   
	| string_   
	| float     
	| bool ;

bool:
    FALSE | TRUE;

integer:
	DECIMAL_LIT;

float:
    FLOAT_LIT;

operandName: IDENTIFIER;

//qualifiedIdent: IDENTIFIER DOT IDENTIFIER;

//compositeLit: literalType literalValue;
compositeLit: arrayType literalValue;

//TIPOS LITERAIS
//literalType:
//	structType
//	arrayType
//	| sliceType
//	| mapType
//	| typeName;

literalValue: L_CURLY (elementList COMMA?)? R_CURLY;

elementList: keyedElement (COMMA keyedElement)*;

keyedElement: (key COLON)? element;

key: expression | literalValue;

element: expression | literalValue;

//TIPO STRUCT
//structType: STRUCT L_CURLY (fieldDecl eos)* R_CURLY;

//fieldDecl: (
//		{noTerminatorBetween(2)}? identifierList type_
//		| embeddedField
//	) tag = string_?;
//fieldDecl: (
//		identifierList type_
//		| embeddedField
//	) tag = string_?;

string_: RAW_STRING_LIT | INTERPRETED_STRING_LIT;

//embeddedField: STAR? typeName;

functionLit: FUNC (signature block?); // function

index: L_BRACKET expression R_BRACKET;

//slice_:
//	L_BRACKET (
//		expression? COLON expression?
//		| expression? COLON expression COLON expression
//	) R_BRACKET;

//typeAssertion: DOT L_PAREN type_ R_PAREN;

arguments:
	L_PAREN (
		(expressionList | (COMMA expressionList)?) COMMA?
//		(expressionList | nonNamedType (COMMA expressionList)?) COMMA?
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