lexer grammar GoLexer;

@header{
    package parser;
}

// Keywords

BREAK                  : 'break' -> mode(NLSEMI);
//DEFAULT                : 'default';
FUNC                   : 'func';
//SELECT                 : 'select';
//MAP                    : 'map';
//STRUCT                 : 'struct';
ELSE                   : 'else';
PACKAGE                : 'package '([a-zA-Z])* -> skip;

//CONST                  : 'const';
//FALLTHROUGH            : 'fallthrough' -> mode(NLSEMI);
IF                     : 'if';
//RANGE                  : 'range';
//TYPE                   : 'type';
CONTINUE               : 'continue' -> mode(NLSEMI);
FOR                    : 'for';
IMPORT                 : 'import "fmt"' -> skip;
RETURN                 : 'return' -> mode(NLSEMI);
VAR                    : 'var';

// Remover o NULL
//NIL_LIT                : 'nil' -> mode(NLSEMI);

INT                    :    'int'| 'int32' | 'int64';
FLOAT                  :    'float' | 'float32' | 'float64';     
STRING                 :    'string';
BOOL                   :    'bool';

IDENTIFIER             : LETTER (LETTER | DECIMALS)* -> mode(NLSEMI);



// Punctuation

L_PAREN                : '(';
R_PAREN                : ')' -> mode(NLSEMI);
L_CURLY                : '{';
R_CURLY                : '}' -> mode(NLSEMI);
L_BRACKET              : '[';
R_BRACKET              : ']' -> mode(NLSEMI);
ASSIGN                 : '=';
COMMA                  : ',';
SEMI                   : ';';
COLON                  : ':';
DOT                    : '.';
PLUS_PLUS              : '++' -> mode(NLSEMI);
MINUS_MINUS            : '--' -> mode(NLSEMI);

// Logical

LOGICAL_OR             : '||';
LOGICAL_AND            : '&&';

// Relation operators

EQUALS                 : '==';
NOT_EQUALS             : '!=';
LESS                   : '<';
LESS_OR_EQUALS         : '<=';
GREATER                : '>';
GREATER_OR_EQUALS      : '>=';

// Arithmetic operators

OR                     : '|';
DIV                    : '/';
MOD                    : '%';

// Unary operators

EXCLAMATION            : '!';

// Mixed operators

PLUS                   : '+';
MINUS                  : '-';
CARET                  : '^';
STAR                   : '*';

// Bool decl
FALSE                  : 'false';
TRUE                   : 'true';

// Number literals


DECIMAL_LIT            : ('0' | [1-9] ('_'? [0-9])*) -> mode(NLSEMI);

FLOAT_LIT : (DECIMAL_FLOAT_LIT) -> mode(NLSEMI);

DECIMAL_FLOAT_LIT      : DECIMALS ('.' DECIMALS? EXPONENT? | EXPONENT)
                       | '.' DECIMALS EXPONENT?
                       ;


// String literals

RAW_STRING_LIT         : '`' ~'`'*                      '`' -> mode(NLSEMI);
INTERPRETED_STRING_LIT : '"' (~["\\])*  '"' -> mode(NLSEMI);

// Hidden tokens
WS                     : [ \t]+             -> channel(HIDDEN);
COMMENT                : '/*' .*? '*/'      -> channel(HIDDEN);
TERMINATOR             : [\r\n]+            -> channel(HIDDEN);
LINE_COMMENT           : '//' ~[\r\n]*      -> channel(HIDDEN);

fragment DECIMALS
    : [0-9] ('_'? [0-9])*
    ;

fragment EXPONENT
    : [eE] [+-]? DECIMALS
    ;

fragment LETTER
    : [a-z]
    | [A-Z]
    | '_'
    ;

/*
    Customize Tokens for IO opertations
 */
PRINT : 'fmt.Println';
SCAN : 'fmt.Scan';

mode NLSEMI;
// Treat whitespace as normal
WS_NLSEMI                     : [ \t]+             -> channel(HIDDEN);
// Ignore any comments that only span one line
COMMENT_NLSEMI                : '/*' ~[\r\n]*? '*/'      -> channel(HIDDEN);
LINE_COMMENT_NLSEMI : '//' ~[\r\n]*      -> channel(HIDDEN);
// Emit an EOS token for any newlines, semicolon, multiline comments or the EOF and 
//return to normal lexing
EOS:              ([\r\n]+ | ';' | '/*' .*? '*/' | EOF)            -> mode(DEFAULT_MODE);
// Did not find an EOS, so go back to normal lexing
OTHER: -> mode(DEFAULT_MODE), channel(HIDDEN);
