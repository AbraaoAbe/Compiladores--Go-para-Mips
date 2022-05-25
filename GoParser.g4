parser grammar GoParser;

options {
	tokenVocab = GoLexer;
	superClass = GoParserBase;
}

sourceFile:
	((functionDecl | methodDecl | declaration) eos
	)* EOF;

//DECLARACAO DE CONSTANTES, TIPOS CONJUNTOS E VARIAVEIS SIMPLES
declaration: constDecl | typeDecl | varDecl;

constDecl: CONST (constSpec | L_PAREN (constSpec eos)* R_PAREN);

constSpec: identifierList (type_? ASSIGN expressionList)?;

identifierList: IDENTIFIER (COMMA IDENTIFIER)*;

expressionList: expression (COMMA expression)*;

typeDecl: TYPE (typeSpec | L_PAREN (typeSpec eos)* R_PAREN);

typeSpec: IDENTIFIER ASSIGN? type_;


// DECLARACAO DE FUNCAO

functionDecl: FUNC IDENTIFIER (signature block?);

methodDecl: FUNC receiver IDENTIFIER ( signature block?);

receiver: parameters;

//TIPO VAR (DEIXA OU TIRA?)
varDecl: VAR (varSpec | L_PAREN (varSpec eos)* R_PAREN);

varSpec:
	identifierList (
		type_ (ASSIGN expressionList)?
		| ASSIGN expressionList
	);

//BLOCO DE STATUS
block: L_CURLY statementList? R_CURLY;

statementList: (statement eos)+;
//statementList: ((SEMI? | {closingBracket()}?) statement)+;

//STATUS
statement:
	declaration
	| labeledStmt
	| simpleStmt
	| returnStmt
	| breakStmt
	| continueStmt
	| fallthroughStmt
	| block
	| ifStmt
	| switchStmt
	| forStmt;

simpleStmt:
	incDecStmt
	| assignment
	| expressionStmt
	| shortVarDecl;

expressionStmt: expression;

incDecStmt: expression (PLUS_PLUS | MINUS_MINUS);

//DECLARACAO MULTIPLA x, y, z = 1, 2, 3
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
shortVarDecl: identifierList DECLARE_ASSIGN expressionList;

emptyStmt: EOS | SEMI;

labeledStmt: IDENTIFIER COLON statement?;

returnStmt: RETURN expressionList?;

breakStmt: BREAK IDENTIFIER?;

continueStmt: CONTINUE IDENTIFIER?;

fallthroughStmt: FALLTHROUGH;

//CLAUSULA DO IF 
ifStmt:
	IF ( expression
			| eos expression
			| simpleStmt eos expression
			) block (
		ELSE (ifStmt | block)
	)?;

//CLAUSULA DO SWITCH E TYPESWITCH 
switchStmt: exprSwitchStmt | typeSwitchStmt;

exprSwitchStmt:
	SWITCH (expression?
					| simpleStmt? eos expression?
					) L_CURLY exprCaseClause* R_CURLY;

exprCaseClause: exprSwitchCase COLON statementList?;

exprSwitchCase: CASE expressionList | DEFAULT;

typeSwitchStmt:
	SWITCH ( typeSwitchGuard
					| eos typeSwitchGuard
					| simpleStmt eos typeSwitchGuard)
					 L_CURLY typeCaseClause* R_CURLY;

typeSwitchGuard: (IDENTIFIER DECLARE_ASSIGN)? primaryExpr DOT L_PAREN TYPE R_PAREN;

typeCaseClause: typeSwitchCase COLON statementList?;

typeSwitchCase: CASE typeList | DEFAULT;

typeList: (type_ | NIL_LIT) (COMMA (type_ | NIL_LIT))*;

recvStmt: (expressionList ASSIGN | identifierList DECLARE_ASSIGN)? recvExpr = expression;

//CLAUSULA DO FOR()

forStmt: FOR (expression? | forClause | rangeClause?) block;

forClause:
	initStmt = simpleStmt? eos expression? eos postStmt = simpleStmt?;

//CLAUSULA DO RANGE
rangeClause: (
		expressionList ASSIGN
		| identifierList DECLARE_ASSIGN
	)? RANGE expression;

type_: typeName | typeLit | L_PAREN type_ R_PAREN;

typeName: qualifiedIdent | IDENTIFIER;

typeLit:
	arrayType
	| functionType
	| sliceType
	| mapType;

arrayType: L_BRACKET arrayLength R_BRACKET elementType;

arrayLength: expression;

elementType: type_;

sliceType: L_BRACKET R_BRACKET elementType;

// It's possible to replace `type` with more restricted typeLit list and also pay attention to nil maps
mapType: MAP L_BRACKET type_ R_BRACKET elementType;

methodSpec:
	IDENTIFIER parameters result
	| IDENTIFIER parameters;

functionType: FUNC signature;
// func main()
// fmt.Println()
// fmt.Scan()

signature:
	parameters result
	| parameters;

result: parameters | type_;

parameters:
	L_PAREN (parameterDecl (COMMA parameterDecl)* COMMA?)? R_PAREN;

parameterDecl: identifierList? type_;

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
	| conversion
	| methodExpr
	| primaryExpr (
		(DOT IDENTIFIER)
		| index
		| slice_
		| typeAssertion
		| arguments
	);


conversion: nonNamedType L_PAREN expression COMMA? R_PAREN;

nonNamedType: typeLit | L_PAREN nonNamedType R_PAREN;

operand: literal | operandName | L_PAREN expression R_PAREN;

literal: basicLit | compositeLit | functionLit;

basicLit:
	NIL_LIT
	| integer
	| string_
	| float;

integer:
	DECIMAL_LIT;

float:
    FLOAT_LIT;

operandName: IDENTIFIER;

qualifiedIdent: IDENTIFIER DOT IDENTIFIER;

compositeLit: literalType literalValue;

//TIPOS LITERAIS
literalType:
	structType
	| arrayType
	| sliceType
	| mapType
	| typeName;

literalValue: L_CURLY (elementList COMMA?)? R_CURLY;

elementList: keyedElement (COMMA keyedElement)*;

keyedElement: (key COLON)? element;

key: expression | literalValue;

element: expression | literalValue;

//TIPO STRUCT
structType: STRUCT L_CURLY (fieldDecl eos)* R_CURLY;

//fieldDecl: (
//		{noTerminatorBetween(2)}? identifierList type_
//		| embeddedField
//	) tag = string_?;
fieldDecl: (
		identifierList type_
		| embeddedField
	) tag = string_?;

string_: RAW_STRING_LIT | INTERPRETED_STRING_LIT;

embeddedField: STAR? typeName;

functionLit: FUNC (signature block?); // function

index: L_BRACKET expression R_BRACKET;

slice_:
	L_BRACKET (
		expression? COLON expression?
		| expression? COLON expression COLON expression
	) R_BRACKET;

typeAssertion: DOT L_PAREN type_ R_PAREN;

arguments:
	L_PAREN (
		(expressionList | nonNamedType (COMMA expressionList)?) COMMA?
	)? R_PAREN;

methodExpr: nonNamedType DOT IDENTIFIER;

//receiverType: typeName | '(' ('*' typeName | receiverType) ')';

receiverType: type_;

eos:
	SEMI
	| EOF
	| EOS
	| {closingBracket()}?
	;
//eos:
//	SEMI
//	| EOF
//	| EOS
//	;
