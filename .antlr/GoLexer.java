// Generated from c:\Users\AbraaoAbe\Documents\UFES\Trab_comp\Compiladores--Go-para-Mips\GoLexer.g4 by ANTLR 4.9.2

    package parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GoLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BREAK=1, FUNC=2, ELSE=3, PACKAGE=4, IF=5, CONTINUE=6, FOR=7, IMPORT=8, 
		RETURN=9, VAR=10, INT=11, FLOAT=12, STRING=13, BOOL=14, IDENTIFIER=15, 
		L_PAREN=16, R_PAREN=17, L_CURLY=18, R_CURLY=19, L_BRACKET=20, R_BRACKET=21, 
		ASSIGN=22, COMMA=23, SEMI=24, COLON=25, DOT=26, PLUS_PLUS=27, MINUS_MINUS=28, 
		LOGICAL_OR=29, LOGICAL_AND=30, EQUALS=31, NOT_EQUALS=32, LESS=33, LESS_OR_EQUALS=34, 
		GREATER=35, GREATER_OR_EQUALS=36, OR=37, DIV=38, MOD=39, EXCLAMATION=40, 
		PLUS=41, MINUS=42, CARET=43, TIMES=44, FALSE=45, TRUE=46, DECIMAL_LIT=47, 
		FLOAT_LIT=48, DECIMAL_FLOAT_LIT=49, RAW_STRING_LIT=50, INTERPRETED_STRING_LIT=51, 
		WS=52, COMMENT=53, TERMINATOR=54, LINE_COMMENT=55, PRINT=56, SCAN=57, 
		WS_NLSEMI=58, COMMENT_NLSEMI=59, LINE_COMMENT_NLSEMI=60, EOS=61, OTHER=62;
	public static final int
		NLSEMI=1;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "NLSEMI"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"BREAK", "FUNC", "ELSE", "PACKAGE", "IF", "CONTINUE", "FOR", "IMPORT", 
			"RETURN", "VAR", "INT", "FLOAT", "STRING", "BOOL", "IDENTIFIER", "L_PAREN", 
			"R_PAREN", "L_CURLY", "R_CURLY", "L_BRACKET", "R_BRACKET", "ASSIGN", 
			"COMMA", "SEMI", "COLON", "DOT", "PLUS_PLUS", "MINUS_MINUS", "LOGICAL_OR", 
			"LOGICAL_AND", "EQUALS", "NOT_EQUALS", "LESS", "LESS_OR_EQUALS", "GREATER", 
			"GREATER_OR_EQUALS", "OR", "DIV", "MOD", "EXCLAMATION", "PLUS", "MINUS", 
			"CARET", "TIMES", "FALSE", "TRUE", "DECIMAL_LIT", "FLOAT_LIT", "DECIMAL_FLOAT_LIT", 
			"RAW_STRING_LIT", "INTERPRETED_STRING_LIT", "WS", "COMMENT", "TERMINATOR", 
			"LINE_COMMENT", "DECIMALS", "EXPONENT", "LETTER", "PRINT", "SCAN", "WS_NLSEMI", 
			"COMMENT_NLSEMI", "LINE_COMMENT_NLSEMI", "EOS", "OTHER"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'break'", "'func'", "'else'", null, "'if'", "'continue'", "'for'", 
			"'import \"fmt\"'", "'return'", "'var'", null, null, "'string'", "'bool'", 
			null, "'('", "')'", "'{'", "'}'", "'['", "']'", "'='", "','", "';'", 
			"':'", "'.'", "'++'", "'--'", "'||'", "'&&'", "'=='", "'!='", "'<'", 
			"'<='", "'>'", "'>='", "'|'", "'/'", "'%'", "'!'", "'+'", "'-'", "'^'", 
			"'*'", "'false'", "'true'", null, null, null, null, null, null, null, 
			null, null, "'fmt.Println'", "'fmt.Scan'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BREAK", "FUNC", "ELSE", "PACKAGE", "IF", "CONTINUE", "FOR", "IMPORT", 
			"RETURN", "VAR", "INT", "FLOAT", "STRING", "BOOL", "IDENTIFIER", "L_PAREN", 
			"R_PAREN", "L_CURLY", "R_CURLY", "L_BRACKET", "R_BRACKET", "ASSIGN", 
			"COMMA", "SEMI", "COLON", "DOT", "PLUS_PLUS", "MINUS_MINUS", "LOGICAL_OR", 
			"LOGICAL_AND", "EQUALS", "NOT_EQUALS", "LESS", "LESS_OR_EQUALS", "GREATER", 
			"GREATER_OR_EQUALS", "OR", "DIV", "MOD", "EXCLAMATION", "PLUS", "MINUS", 
			"CARET", "TIMES", "FALSE", "TRUE", "DECIMAL_LIT", "FLOAT_LIT", "DECIMAL_FLOAT_LIT", 
			"RAW_STRING_LIT", "INTERPRETED_STRING_LIT", "WS", "COMMENT", "TERMINATOR", 
			"LINE_COMMENT", "PRINT", "SCAN", "WS_NLSEMI", "COMMENT_NLSEMI", "LINE_COMMENT_NLSEMI", 
			"EOS", "OTHER"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public GoLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "GoLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2@\u022d\b\1\b\1\4"+
		"\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n"+
		"\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3"+
		"\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\7\5\u00a3\n\5\f\5\16\5\u00a6\13\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\5\f\u00e5\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00fa\n\r\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\7\20\u010b\n\20\f\20\16"+
		"\20\u010e\13\20\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\24"+
		"\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\31"+
		"\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35"+
		"\3\35\3\36\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\3#\3#\3"+
		"#\3$\3$\3%\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3."+
		"\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3\60\3\60\3\60\5\60\u016c\n\60\3\60\7\60"+
		"\u016f\n\60\f\60\16\60\u0172\13\60\5\60\u0174\n\60\3\60\3\60\3\61\3\61"+
		"\3\61\3\61\3\62\3\62\3\62\5\62\u017f\n\62\3\62\5\62\u0182\n\62\3\62\5"+
		"\62\u0185\n\62\3\62\3\62\3\62\5\62\u018a\n\62\5\62\u018c\n\62\3\63\3\63"+
		"\7\63\u0190\n\63\f\63\16\63\u0193\13\63\3\63\3\63\3\63\3\63\3\64\3\64"+
		"\7\64\u019b\n\64\f\64\16\64\u019e\13\64\3\64\3\64\3\64\3\64\3\65\6\65"+
		"\u01a5\n\65\r\65\16\65\u01a6\3\65\3\65\3\66\3\66\3\66\3\66\7\66\u01af"+
		"\n\66\f\66\16\66\u01b2\13\66\3\66\3\66\3\66\3\66\3\66\3\67\6\67\u01ba"+
		"\n\67\r\67\16\67\u01bb\3\67\3\67\38\38\38\38\78\u01c4\n8\f8\168\u01c7"+
		"\138\38\38\39\39\59\u01cd\n9\39\79\u01d0\n9\f9\169\u01d3\139\3:\3:\5:"+
		"\u01d7\n:\3:\3:\3;\5;\u01dc\n;\3<\3<\3<\3<\3<\3<\3<\3<\3<\3<\3<\3<\3="+
		"\3=\3=\3=\3=\3=\3=\3=\3=\3>\6>\u01f4\n>\r>\16>\u01f5\3>\3>\3?\3?\3?\3"+
		"?\7?\u01fe\n?\f?\16?\u0201\13?\3?\3?\3?\3?\3?\3@\3@\3@\3@\7@\u020c\n@"+
		"\f@\16@\u020f\13@\3@\3@\3A\6A\u0214\nA\rA\16A\u0215\3A\3A\3A\3A\3A\7A"+
		"\u021d\nA\fA\16A\u0220\13A\3A\3A\3A\5A\u0225\nA\3A\3A\3B\3B\3B\3B\3B\5"+
		"\u01b0\u01ff\u021e\2C\4\3\6\4\b\5\n\6\f\7\16\b\20\t\22\n\24\13\26\f\30"+
		"\r\32\16\34\17\36\20 \21\"\22$\23&\24(\25*\26,\27.\30\60\31\62\32\64\33"+
		"\66\348\35:\36<\37> @!B\"D#F$H%J&L\'N(P)R*T+V,X-Z.\\/^\60`\61b\62d\63"+
		"f\64h\65j\66l\67n8p9r\2t\2v\2x:z;|<~=\u0080>\u0082?\u0084@\4\2\3\f\4\2"+
		"C\\c|\3\2\63;\3\2\62;\3\2bb\4\2$$^^\4\2\13\13\"\"\4\2\f\f\17\17\4\2GG"+
		"gg\4\2--//\5\2C\\aac|\2\u0248\2\4\3\2\2\2\2\6\3\2\2\2\2\b\3\2\2\2\2\n"+
		"\3\2\2\2\2\f\3\2\2\2\2\16\3\2\2\2\2\20\3\2\2\2\2\22\3\2\2\2\2\24\3\2\2"+
		"\2\2\26\3\2\2\2\2\30\3\2\2\2\2\32\3\2\2\2\2\34\3\2\2\2\2\36\3\2\2\2\2"+
		" \3\2\2\2\2\"\3\2\2\2\2$\3\2\2\2\2&\3\2\2\2\2(\3\2\2\2\2*\3\2\2\2\2,\3"+
		"\2\2\2\2.\3\2\2\2\2\60\3\2\2\2\2\62\3\2\2\2\2\64\3\2\2\2\2\66\3\2\2\2"+
		"\28\3\2\2\2\2:\3\2\2\2\2<\3\2\2\2\2>\3\2\2\2\2@\3\2\2\2\2B\3\2\2\2\2D"+
		"\3\2\2\2\2F\3\2\2\2\2H\3\2\2\2\2J\3\2\2\2\2L\3\2\2\2\2N\3\2\2\2\2P\3\2"+
		"\2\2\2R\3\2\2\2\2T\3\2\2\2\2V\3\2\2\2\2X\3\2\2\2\2Z\3\2\2\2\2\\\3\2\2"+
		"\2\2^\3\2\2\2\2`\3\2\2\2\2b\3\2\2\2\2d\3\2\2\2\2f\3\2\2\2\2h\3\2\2\2\2"+
		"j\3\2\2\2\2l\3\2\2\2\2n\3\2\2\2\2p\3\2\2\2\2x\3\2\2\2\2z\3\2\2\2\3|\3"+
		"\2\2\2\3~\3\2\2\2\3\u0080\3\2\2\2\3\u0082\3\2\2\2\3\u0084\3\2\2\2\4\u0086"+
		"\3\2\2\2\6\u008e\3\2\2\2\b\u0093\3\2\2\2\n\u0098\3\2\2\2\f\u00a9\3\2\2"+
		"\2\16\u00ac\3\2\2\2\20\u00b7\3\2\2\2\22\u00bb\3\2\2\2\24\u00ca\3\2\2\2"+
		"\26\u00d3\3\2\2\2\30\u00e4\3\2\2\2\32\u00f9\3\2\2\2\34\u00fb\3\2\2\2\36"+
		"\u0102\3\2\2\2 \u0107\3\2\2\2\"\u0111\3\2\2\2$\u0113\3\2\2\2&\u0117\3"+
		"\2\2\2(\u0119\3\2\2\2*\u011d\3\2\2\2,\u011f\3\2\2\2.\u0123\3\2\2\2\60"+
		"\u0125\3\2\2\2\62\u0127\3\2\2\2\64\u0129\3\2\2\2\66\u012b\3\2\2\28\u012d"+
		"\3\2\2\2:\u0132\3\2\2\2<\u0137\3\2\2\2>\u013a\3\2\2\2@\u013d\3\2\2\2B"+
		"\u0140\3\2\2\2D\u0143\3\2\2\2F\u0145\3\2\2\2H\u0148\3\2\2\2J\u014a\3\2"+
		"\2\2L\u014d\3\2\2\2N\u014f\3\2\2\2P\u0151\3\2\2\2R\u0153\3\2\2\2T\u0155"+
		"\3\2\2\2V\u0157\3\2\2\2X\u0159\3\2\2\2Z\u015b\3\2\2\2\\\u015d\3\2\2\2"+
		"^\u0163\3\2\2\2`\u0173\3\2\2\2b\u0177\3\2\2\2d\u018b\3\2\2\2f\u018d\3"+
		"\2\2\2h\u0198\3\2\2\2j\u01a4\3\2\2\2l\u01aa\3\2\2\2n\u01b9\3\2\2\2p\u01bf"+
		"\3\2\2\2r\u01ca\3\2\2\2t\u01d4\3\2\2\2v\u01db\3\2\2\2x\u01dd\3\2\2\2z"+
		"\u01e9\3\2\2\2|\u01f3\3\2\2\2~\u01f9\3\2\2\2\u0080\u0207\3\2\2\2\u0082"+
		"\u0224\3\2\2\2\u0084\u0228\3\2\2\2\u0086\u0087\7d\2\2\u0087\u0088\7t\2"+
		"\2\u0088\u0089\7g\2\2\u0089\u008a\7c\2\2\u008a\u008b\7m\2\2\u008b\u008c"+
		"\3\2\2\2\u008c\u008d\b\2\2\2\u008d\5\3\2\2\2\u008e\u008f\7h\2\2\u008f"+
		"\u0090\7w\2\2\u0090\u0091\7p\2\2\u0091\u0092\7e\2\2\u0092\7\3\2\2\2\u0093"+
		"\u0094\7g\2\2\u0094\u0095\7n\2\2\u0095\u0096\7u\2\2\u0096\u0097\7g\2\2"+
		"\u0097\t\3\2\2\2\u0098\u0099\7r\2\2\u0099\u009a\7c\2\2\u009a\u009b\7e"+
		"\2\2\u009b\u009c\7m\2\2\u009c\u009d\7c\2\2\u009d\u009e\7i\2\2\u009e\u009f"+
		"\7g\2\2\u009f\u00a0\7\"\2\2\u00a0\u00a4\3\2\2\2\u00a1\u00a3\t\2\2\2\u00a2"+
		"\u00a1\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2"+
		"\2\2\u00a5\u00a7\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a7\u00a8\b\5\3\2\u00a8"+
		"\13\3\2\2\2\u00a9\u00aa\7k\2\2\u00aa\u00ab\7h\2\2\u00ab\r\3\2\2\2\u00ac"+
		"\u00ad\7e\2\2\u00ad\u00ae\7q\2\2\u00ae\u00af\7p\2\2\u00af\u00b0\7v\2\2"+
		"\u00b0\u00b1\7k\2\2\u00b1\u00b2\7p\2\2\u00b2\u00b3\7w\2\2\u00b3\u00b4"+
		"\7g\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\b\7\2\2\u00b6\17\3\2\2\2\u00b7"+
		"\u00b8\7h\2\2\u00b8\u00b9\7q\2\2\u00b9\u00ba\7t\2\2\u00ba\21\3\2\2\2\u00bb"+
		"\u00bc\7k\2\2\u00bc\u00bd\7o\2\2\u00bd\u00be\7r\2\2\u00be\u00bf\7q\2\2"+
		"\u00bf\u00c0\7t\2\2\u00c0\u00c1\7v\2\2\u00c1\u00c2\7\"\2\2\u00c2\u00c3"+
		"\7$\2\2\u00c3\u00c4\7h\2\2\u00c4\u00c5\7o\2\2\u00c5\u00c6\7v\2\2\u00c6"+
		"\u00c7\7$\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00c9\b\t\3\2\u00c9\23\3\2\2\2"+
		"\u00ca\u00cb\7t\2\2\u00cb\u00cc\7g\2\2\u00cc\u00cd\7v\2\2\u00cd\u00ce"+
		"\7w\2\2\u00ce\u00cf\7t\2\2\u00cf\u00d0\7p\2\2\u00d0\u00d1\3\2\2\2\u00d1"+
		"\u00d2\b\n\2\2\u00d2\25\3\2\2\2\u00d3\u00d4\7x\2\2\u00d4\u00d5\7c\2\2"+
		"\u00d5\u00d6\7t\2\2\u00d6\27\3\2\2\2\u00d7\u00d8\7k\2\2\u00d8\u00d9\7"+
		"p\2\2\u00d9\u00e5\7v\2\2\u00da\u00db\7k\2\2\u00db\u00dc\7p\2\2\u00dc\u00dd"+
		"\7v\2\2\u00dd\u00de\7\65\2\2\u00de\u00e5\7\64\2\2\u00df\u00e0\7k\2\2\u00e0"+
		"\u00e1\7p\2\2\u00e1\u00e2\7v\2\2\u00e2\u00e3\78\2\2\u00e3\u00e5\7\66\2"+
		"\2\u00e4\u00d7\3\2\2\2\u00e4\u00da\3\2\2\2\u00e4\u00df\3\2\2\2\u00e5\31"+
		"\3\2\2\2\u00e6\u00e7\7h\2\2\u00e7\u00e8\7n\2\2\u00e8\u00e9\7q\2\2\u00e9"+
		"\u00ea\7c\2\2\u00ea\u00fa\7v\2\2\u00eb\u00ec\7h\2\2\u00ec\u00ed\7n\2\2"+
		"\u00ed\u00ee\7q\2\2\u00ee\u00ef\7c\2\2\u00ef\u00f0\7v\2\2\u00f0\u00f1"+
		"\7\65\2\2\u00f1\u00fa\7\64\2\2\u00f2\u00f3\7h\2\2\u00f3\u00f4\7n\2\2\u00f4"+
		"\u00f5\7q\2\2\u00f5\u00f6\7c\2\2\u00f6\u00f7\7v\2\2\u00f7\u00f8\78\2\2"+
		"\u00f8\u00fa\7\66\2\2\u00f9\u00e6\3\2\2\2\u00f9\u00eb\3\2\2\2\u00f9\u00f2"+
		"\3\2\2\2\u00fa\33\3\2\2\2\u00fb\u00fc\7u\2\2\u00fc\u00fd\7v\2\2\u00fd"+
		"\u00fe\7t\2\2\u00fe\u00ff\7k\2\2\u00ff\u0100\7p\2\2\u0100\u0101\7i\2\2"+
		"\u0101\35\3\2\2\2\u0102\u0103\7d\2\2\u0103\u0104\7q\2\2\u0104\u0105\7"+
		"q\2\2\u0105\u0106\7n\2\2\u0106\37\3\2\2\2\u0107\u010c\5v;\2\u0108\u010b"+
		"\5v;\2\u0109\u010b\5r9\2\u010a\u0108\3\2\2\2\u010a\u0109\3\2\2\2\u010b"+
		"\u010e\3\2\2\2\u010c\u010a\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u010f\3\2"+
		"\2\2\u010e\u010c\3\2\2\2\u010f\u0110\b\20\2\2\u0110!\3\2\2\2\u0111\u0112"+
		"\7*\2\2\u0112#\3\2\2\2\u0113\u0114\7+\2\2\u0114\u0115\3\2\2\2\u0115\u0116"+
		"\b\22\2\2\u0116%\3\2\2\2\u0117\u0118\7}\2\2\u0118\'\3\2\2\2\u0119\u011a"+
		"\7\177\2\2\u011a\u011b\3\2\2\2\u011b\u011c\b\24\2\2\u011c)\3\2\2\2\u011d"+
		"\u011e\7]\2\2\u011e+\3\2\2\2\u011f\u0120\7_\2\2\u0120\u0121\3\2\2\2\u0121"+
		"\u0122\b\26\2\2\u0122-\3\2\2\2\u0123\u0124\7?\2\2\u0124/\3\2\2\2\u0125"+
		"\u0126\7.\2\2\u0126\61\3\2\2\2\u0127\u0128\7=\2\2\u0128\63\3\2\2\2\u0129"+
		"\u012a\7<\2\2\u012a\65\3\2\2\2\u012b\u012c\7\60\2\2\u012c\67\3\2\2\2\u012d"+
		"\u012e\7-\2\2\u012e\u012f\7-\2\2\u012f\u0130\3\2\2\2\u0130\u0131\b\34"+
		"\2\2\u01319\3\2\2\2\u0132\u0133\7/\2\2\u0133\u0134\7/\2\2\u0134\u0135"+
		"\3\2\2\2\u0135\u0136\b\35\2\2\u0136;\3\2\2\2\u0137\u0138\7~\2\2\u0138"+
		"\u0139\7~\2\2\u0139=\3\2\2\2\u013a\u013b\7(\2\2\u013b\u013c\7(\2\2\u013c"+
		"?\3\2\2\2\u013d\u013e\7?\2\2\u013e\u013f\7?\2\2\u013fA\3\2\2\2\u0140\u0141"+
		"\7#\2\2\u0141\u0142\7?\2\2\u0142C\3\2\2\2\u0143\u0144\7>\2\2\u0144E\3"+
		"\2\2\2\u0145\u0146\7>\2\2\u0146\u0147\7?\2\2\u0147G\3\2\2\2\u0148\u0149"+
		"\7@\2\2\u0149I\3\2\2\2\u014a\u014b\7@\2\2\u014b\u014c\7?\2\2\u014cK\3"+
		"\2\2\2\u014d\u014e\7~\2\2\u014eM\3\2\2\2\u014f\u0150\7\61\2\2\u0150O\3"+
		"\2\2\2\u0151\u0152\7\'\2\2\u0152Q\3\2\2\2\u0153\u0154\7#\2\2\u0154S\3"+
		"\2\2\2\u0155\u0156\7-\2\2\u0156U\3\2\2\2\u0157\u0158\7/\2\2\u0158W\3\2"+
		"\2\2\u0159\u015a\7`\2\2\u015aY\3\2\2\2\u015b\u015c\7,\2\2\u015c[\3\2\2"+
		"\2\u015d\u015e\7h\2\2\u015e\u015f\7c\2\2\u015f\u0160\7n\2\2\u0160\u0161"+
		"\7u\2\2\u0161\u0162\7g\2\2\u0162]\3\2\2\2\u0163\u0164\7v\2\2\u0164\u0165"+
		"\7t\2\2\u0165\u0166\7w\2\2\u0166\u0167\7g\2\2\u0167_\3\2\2\2\u0168\u0174"+
		"\7\62\2\2\u0169\u0170\t\3\2\2\u016a\u016c\7a\2\2\u016b\u016a\3\2\2\2\u016b"+
		"\u016c\3\2\2\2\u016c\u016d\3\2\2\2\u016d\u016f\t\4\2\2\u016e\u016b\3\2"+
		"\2\2\u016f\u0172\3\2\2\2\u0170\u016e\3\2\2\2\u0170\u0171\3\2\2\2\u0171"+
		"\u0174\3\2\2\2\u0172\u0170\3\2\2\2\u0173\u0168\3\2\2\2\u0173\u0169\3\2"+
		"\2\2\u0174\u0175\3\2\2\2\u0175\u0176\b\60\2\2\u0176a\3\2\2\2\u0177\u0178"+
		"\5d\62\2\u0178\u0179\3\2\2\2\u0179\u017a\b\61\2\2\u017ac\3\2\2\2\u017b"+
		"\u0184\5r9\2\u017c\u017e\7\60\2\2\u017d\u017f\5r9\2\u017e\u017d\3\2\2"+
		"\2\u017e\u017f\3\2\2\2\u017f\u0181\3\2\2\2\u0180\u0182\5t:\2\u0181\u0180"+
		"\3\2\2\2\u0181\u0182\3\2\2\2\u0182\u0185\3\2\2\2\u0183\u0185\5t:\2\u0184"+
		"\u017c\3\2\2\2\u0184\u0183\3\2\2\2\u0185\u018c\3\2\2\2\u0186\u0187\7\60"+
		"\2\2\u0187\u0189\5r9\2\u0188\u018a\5t:\2\u0189\u0188\3\2\2\2\u0189\u018a"+
		"\3\2\2\2\u018a\u018c\3\2\2\2\u018b\u017b\3\2\2\2\u018b\u0186\3\2\2\2\u018c"+
		"e\3\2\2\2\u018d\u0191\7b\2\2\u018e\u0190\n\5\2\2\u018f\u018e\3\2\2\2\u0190"+
		"\u0193\3\2\2\2\u0191\u018f\3\2\2\2\u0191\u0192\3\2\2\2\u0192\u0194\3\2"+
		"\2\2\u0193\u0191\3\2\2\2\u0194\u0195\7b\2\2\u0195\u0196\3\2\2\2\u0196"+
		"\u0197\b\63\2\2\u0197g\3\2\2\2\u0198\u019c\7$\2\2\u0199\u019b\n\6\2\2"+
		"\u019a\u0199\3\2\2\2\u019b\u019e\3\2\2\2\u019c\u019a\3\2\2\2\u019c\u019d"+
		"\3\2\2\2\u019d\u019f\3\2\2\2\u019e\u019c\3\2\2\2\u019f\u01a0\7$\2\2\u01a0"+
		"\u01a1\3\2\2\2\u01a1\u01a2\b\64\2\2\u01a2i\3\2\2\2\u01a3\u01a5\t\7\2\2"+
		"\u01a4\u01a3\3\2\2\2\u01a5\u01a6\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a6\u01a7"+
		"\3\2\2\2\u01a7\u01a8\3\2\2\2\u01a8\u01a9\b\65\4\2\u01a9k\3\2\2\2\u01aa"+
		"\u01ab\7\61\2\2\u01ab\u01ac\7,\2\2\u01ac\u01b0\3\2\2\2\u01ad\u01af\13"+
		"\2\2\2\u01ae\u01ad\3\2\2\2\u01af\u01b2\3\2\2\2\u01b0\u01b1\3\2\2\2\u01b0"+
		"\u01ae\3\2\2\2\u01b1\u01b3\3\2\2\2\u01b2\u01b0\3\2\2\2\u01b3\u01b4\7,"+
		"\2\2\u01b4\u01b5\7\61\2\2\u01b5\u01b6\3\2\2\2\u01b6\u01b7\b\66\4\2\u01b7"+
		"m\3\2\2\2\u01b8\u01ba\t\b\2\2\u01b9\u01b8\3\2\2\2\u01ba\u01bb\3\2\2\2"+
		"\u01bb\u01b9\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc\u01bd\3\2\2\2\u01bd\u01be"+
		"\b\67\4\2\u01beo\3\2\2\2\u01bf\u01c0\7\61\2\2\u01c0\u01c1\7\61\2\2\u01c1"+
		"\u01c5\3\2\2\2\u01c2\u01c4\n\b\2\2\u01c3\u01c2\3\2\2\2\u01c4\u01c7\3\2"+
		"\2\2\u01c5\u01c3\3\2\2\2\u01c5\u01c6\3\2\2\2\u01c6\u01c8\3\2\2\2\u01c7"+
		"\u01c5\3\2\2\2\u01c8\u01c9\b8\4\2\u01c9q\3\2\2\2\u01ca\u01d1\t\4\2\2\u01cb"+
		"\u01cd\7a\2\2\u01cc\u01cb\3\2\2\2\u01cc\u01cd\3\2\2\2\u01cd\u01ce\3\2"+
		"\2\2\u01ce\u01d0\t\4\2\2\u01cf\u01cc\3\2\2\2\u01d0\u01d3\3\2\2\2\u01d1"+
		"\u01cf\3\2\2\2\u01d1\u01d2\3\2\2\2\u01d2s\3\2\2\2\u01d3\u01d1\3\2\2\2"+
		"\u01d4\u01d6\t\t\2\2\u01d5\u01d7\t\n\2\2\u01d6\u01d5\3\2\2\2\u01d6\u01d7"+
		"\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d8\u01d9\5r9\2\u01d9u\3\2\2\2\u01da\u01dc"+
		"\t\13\2\2\u01db\u01da\3\2\2\2\u01dcw\3\2\2\2\u01dd\u01de\7h\2\2\u01de"+
		"\u01df\7o\2\2\u01df\u01e0\7v\2\2\u01e0\u01e1\7\60\2\2\u01e1\u01e2\7R\2"+
		"\2\u01e2\u01e3\7t\2\2\u01e3\u01e4\7k\2\2\u01e4\u01e5\7p\2\2\u01e5\u01e6"+
		"\7v\2\2\u01e6\u01e7\7n\2\2\u01e7\u01e8\7p\2\2\u01e8y\3\2\2\2\u01e9\u01ea"+
		"\7h\2\2\u01ea\u01eb\7o\2\2\u01eb\u01ec\7v\2\2\u01ec\u01ed\7\60\2\2\u01ed"+
		"\u01ee\7U\2\2\u01ee\u01ef\7e\2\2\u01ef\u01f0\7c\2\2\u01f0\u01f1\7p\2\2"+
		"\u01f1{\3\2\2\2\u01f2\u01f4\t\7\2\2\u01f3\u01f2\3\2\2\2\u01f4\u01f5\3"+
		"\2\2\2\u01f5\u01f3\3\2\2\2\u01f5\u01f6\3\2\2\2\u01f6\u01f7\3\2\2\2\u01f7"+
		"\u01f8\b>\4\2\u01f8}\3\2\2\2\u01f9\u01fa\7\61\2\2\u01fa\u01fb\7,\2\2\u01fb"+
		"\u01ff\3\2\2\2\u01fc\u01fe\n\b\2\2\u01fd\u01fc\3\2\2\2\u01fe\u0201\3\2"+
		"\2\2\u01ff\u0200\3\2\2\2\u01ff\u01fd\3\2\2\2\u0200\u0202\3\2\2\2\u0201"+
		"\u01ff\3\2\2\2\u0202\u0203\7,\2\2\u0203\u0204\7\61\2\2\u0204\u0205\3\2"+
		"\2\2\u0205\u0206\b?\4\2\u0206\177\3\2\2\2\u0207\u0208\7\61\2\2\u0208\u0209"+
		"\7\61\2\2\u0209\u020d\3\2\2\2\u020a\u020c\n\b\2\2\u020b\u020a\3\2\2\2"+
		"\u020c\u020f\3\2\2\2\u020d\u020b\3\2\2\2\u020d\u020e\3\2\2\2\u020e\u0210"+
		"\3\2\2\2\u020f\u020d\3\2\2\2\u0210\u0211\b@\4\2\u0211\u0081\3\2\2\2\u0212"+
		"\u0214\t\b\2\2\u0213\u0212\3\2\2\2\u0214\u0215\3\2\2\2\u0215\u0213\3\2"+
		"\2\2\u0215\u0216\3\2\2\2\u0216\u0225\3\2\2\2\u0217\u0225\7=\2\2\u0218"+
		"\u0219\7\61\2\2\u0219\u021a\7,\2\2\u021a\u021e\3\2\2\2\u021b\u021d\13"+
		"\2\2\2\u021c\u021b\3\2\2\2\u021d\u0220\3\2\2\2\u021e\u021f\3\2\2\2\u021e"+
		"\u021c\3\2\2\2\u021f\u0221\3\2\2\2\u0220\u021e\3\2\2\2\u0221\u0222\7,"+
		"\2\2\u0222\u0225\7\61\2\2\u0223\u0225\7\2\2\3\u0224\u0213\3\2\2\2\u0224"+
		"\u0217\3\2\2\2\u0224\u0218\3\2\2\2\u0224\u0223\3\2\2\2\u0225\u0226\3\2"+
		"\2\2\u0226\u0227\bA\5\2\u0227\u0083\3\2\2\2\u0228\u0229\3\2\2\2\u0229"+
		"\u022a\3\2\2\2\u022a\u022b\bB\5\2\u022b\u022c\bB\4\2\u022c\u0085\3\2\2"+
		"\2!\2\3\u00a4\u00e4\u00f9\u010a\u010c\u016b\u0170\u0173\u017e\u0181\u0184"+
		"\u0189\u018b\u0191\u019c\u01a6\u01b0\u01bb\u01c5\u01cc\u01d1\u01d6\u01db"+
		"\u01f5\u01ff\u020d\u0215\u021e\u0224\6\4\3\2\b\2\2\2\3\2\4\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}