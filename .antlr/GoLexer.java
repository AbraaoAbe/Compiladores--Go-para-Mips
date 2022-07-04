// Generated from c:\Users\Abraão Santos\Dropbox\My PC (AbraaumSantos)\Documents\GitHub\Trab_Comp_Certo\Compiladores--Go-para-Mips\GoLexer.g4 by ANTLR 4.9.2
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
		RETURN=9, VAR=10, IDENTIFIER=11, L_PAREN=12, R_PAREN=13, L_CURLY=14, R_CURLY=15, 
		L_BRACKET=16, R_BRACKET=17, ASSIGN=18, COMMA=19, SEMI=20, COLON=21, DOT=22, 
		PLUS_PLUS=23, MINUS_MINUS=24, LOGICAL_OR=25, LOGICAL_AND=26, EQUALS=27, 
		NOT_EQUALS=28, LESS=29, LESS_OR_EQUALS=30, GREATER=31, GREATER_OR_EQUALS=32, 
		OR=33, DIV=34, MOD=35, EXCLAMATION=36, PLUS=37, MINUS=38, CARET=39, STAR=40, 
		FALSE=41, TRUE=42, DECIMAL_LIT=43, FLOAT_LIT=44, DECIMAL_FLOAT_LIT=45, 
		RAW_STRING_LIT=46, INTERPRETED_STRING_LIT=47, WS=48, COMMENT=49, TERMINATOR=50, 
		LINE_COMMENT=51, PRINT=52, SCAN=53, WS_NLSEMI=54, COMMENT_NLSEMI=55, LINE_COMMENT_NLSEMI=56, 
		EOS=57, OTHER=58;
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
			"RETURN", "VAR", "IDENTIFIER", "L_PAREN", "R_PAREN", "L_CURLY", "R_CURLY", 
			"L_BRACKET", "R_BRACKET", "ASSIGN", "COMMA", "SEMI", "COLON", "DOT", 
			"PLUS_PLUS", "MINUS_MINUS", "LOGICAL_OR", "LOGICAL_AND", "EQUALS", "NOT_EQUALS", 
			"LESS", "LESS_OR_EQUALS", "GREATER", "GREATER_OR_EQUALS", "OR", "DIV", 
			"MOD", "EXCLAMATION", "PLUS", "MINUS", "CARET", "STAR", "FALSE", "TRUE", 
			"DECIMAL_LIT", "FLOAT_LIT", "DECIMAL_FLOAT_LIT", "RAW_STRING_LIT", "INTERPRETED_STRING_LIT", 
			"WS", "COMMENT", "TERMINATOR", "LINE_COMMENT", "DECIMALS", "EXPONENT", 
			"LETTER", "PRINT", "SCAN", "WS_NLSEMI", "COMMENT_NLSEMI", "LINE_COMMENT_NLSEMI", 
			"EOS", "OTHER"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'break'", "'func'", "'else'", null, "'if'", "'continue'", "'for'", 
			"'import \"fmt\"'", "'return'", "'var'", null, "'('", "')'", "'{'", "'}'", 
			"'['", "']'", "'='", "','", "';'", "':'", "'.'", "'++'", "'--'", "'||'", 
			"'&&'", "'=='", "'!='", "'<'", "'<='", "'>'", "'>='", "'|'", "'/'", "'%'", 
			"'!'", "'+'", "'-'", "'^'", "'*'", "'false'", "'true'", null, null, null, 
			null, null, null, null, null, null, "'fmt.Println'", "'fmt.Scan'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BREAK", "FUNC", "ELSE", "PACKAGE", "IF", "CONTINUE", "FOR", "IMPORT", 
			"RETURN", "VAR", "IDENTIFIER", "L_PAREN", "R_PAREN", "L_CURLY", "R_CURLY", 
			"L_BRACKET", "R_BRACKET", "ASSIGN", "COMMA", "SEMI", "COLON", "DOT", 
			"PLUS_PLUS", "MINUS_MINUS", "LOGICAL_OR", "LOGICAL_AND", "EQUALS", "NOT_EQUALS", 
			"LESS", "LESS_OR_EQUALS", "GREATER", "GREATER_OR_EQUALS", "OR", "DIV", 
			"MOD", "EXCLAMATION", "PLUS", "MINUS", "CARET", "STAR", "FALSE", "TRUE", 
			"DECIMAL_LIT", "FLOAT_LIT", "DECIMAL_FLOAT_LIT", "RAW_STRING_LIT", "INTERPRETED_STRING_LIT", 
			"WS", "COMMENT", "TERMINATOR", "LINE_COMMENT", "PRINT", "SCAN", "WS_NLSEMI", 
			"COMMENT_NLSEMI", "LINE_COMMENT_NLSEMI", "EOS", "OTHER"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2<\u01f5\b\1\b\1\4"+
		"\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n"+
		"\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u009b\n\5\f\5\16"+
		"\5\u009e\13\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\7\f\u00d3\n\f\f\f\16\f\u00d6\13\f\3\f\3\f\3\r\3\r\3\16\3"+
		"\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3"+
		"\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3"+
		"\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3"+
		"\34\3\34\3\35\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3 \3 \3!\3!\3!\3\"\3"+
		"\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3*\3*\3*\3*\3+\3"+
		"+\3+\3+\3+\3,\3,\3,\5,\u0134\n,\3,\7,\u0137\n,\f,\16,\u013a\13,\5,\u013c"+
		"\n,\3,\3,\3-\3-\3-\3-\3.\3.\3.\5.\u0147\n.\3.\5.\u014a\n.\3.\5.\u014d"+
		"\n.\3.\3.\3.\5.\u0152\n.\5.\u0154\n.\3/\3/\7/\u0158\n/\f/\16/\u015b\13"+
		"/\3/\3/\3/\3/\3\60\3\60\7\60\u0163\n\60\f\60\16\60\u0166\13\60\3\60\3"+
		"\60\3\60\3\60\3\61\6\61\u016d\n\61\r\61\16\61\u016e\3\61\3\61\3\62\3\62"+
		"\3\62\3\62\7\62\u0177\n\62\f\62\16\62\u017a\13\62\3\62\3\62\3\62\3\62"+
		"\3\62\3\63\6\63\u0182\n\63\r\63\16\63\u0183\3\63\3\63\3\64\3\64\3\64\3"+
		"\64\7\64\u018c\n\64\f\64\16\64\u018f\13\64\3\64\3\64\3\65\3\65\5\65\u0195"+
		"\n\65\3\65\7\65\u0198\n\65\f\65\16\65\u019b\13\65\3\66\3\66\5\66\u019f"+
		"\n\66\3\66\3\66\3\67\5\67\u01a4\n\67\38\38\38\38\38\38\38\38\38\38\38"+
		"\38\39\39\39\39\39\39\39\39\39\3:\6:\u01bc\n:\r:\16:\u01bd\3:\3:\3;\3"+
		";\3;\3;\7;\u01c6\n;\f;\16;\u01c9\13;\3;\3;\3;\3;\3;\3<\3<\3<\3<\7<\u01d4"+
		"\n<\f<\16<\u01d7\13<\3<\3<\3=\6=\u01dc\n=\r=\16=\u01dd\3=\3=\3=\3=\3="+
		"\7=\u01e5\n=\f=\16=\u01e8\13=\3=\3=\3=\5=\u01ed\n=\3=\3=\3>\3>\3>\3>\3"+
		">\5\u0178\u01c7\u01e6\2?\4\3\6\4\b\5\n\6\f\7\16\b\20\t\22\n\24\13\26\f"+
		"\30\r\32\16\34\17\36\20 \21\"\22$\23&\24(\25*\26,\27.\30\60\31\62\32\64"+
		"\33\66\348\35:\36<\37> @!B\"D#F$H%J&L\'N(P)R*T+V,X-Z.\\/^\60`\61b\62d"+
		"\63f\64h\65j\2l\2n\2p\66r\67t8v9x:z;|<\4\2\3\f\4\2C\\c|\3\2\63;\3\2\62"+
		";\3\2bb\4\2$$^^\4\2\13\13\"\"\4\2\f\f\17\17\4\2GGgg\4\2--//\5\2C\\aac"+
		"|\2\u020c\2\4\3\2\2\2\2\6\3\2\2\2\2\b\3\2\2\2\2\n\3\2\2\2\2\f\3\2\2\2"+
		"\2\16\3\2\2\2\2\20\3\2\2\2\2\22\3\2\2\2\2\24\3\2\2\2\2\26\3\2\2\2\2\30"+
		"\3\2\2\2\2\32\3\2\2\2\2\34\3\2\2\2\2\36\3\2\2\2\2 \3\2\2\2\2\"\3\2\2\2"+
		"\2$\3\2\2\2\2&\3\2\2\2\2(\3\2\2\2\2*\3\2\2\2\2,\3\2\2\2\2.\3\2\2\2\2\60"+
		"\3\2\2\2\2\62\3\2\2\2\2\64\3\2\2\2\2\66\3\2\2\2\28\3\2\2\2\2:\3\2\2\2"+
		"\2<\3\2\2\2\2>\3\2\2\2\2@\3\2\2\2\2B\3\2\2\2\2D\3\2\2\2\2F\3\2\2\2\2H"+
		"\3\2\2\2\2J\3\2\2\2\2L\3\2\2\2\2N\3\2\2\2\2P\3\2\2\2\2R\3\2\2\2\2T\3\2"+
		"\2\2\2V\3\2\2\2\2X\3\2\2\2\2Z\3\2\2\2\2\\\3\2\2\2\2^\3\2\2\2\2`\3\2\2"+
		"\2\2b\3\2\2\2\2d\3\2\2\2\2f\3\2\2\2\2h\3\2\2\2\2p\3\2\2\2\2r\3\2\2\2\3"+
		"t\3\2\2\2\3v\3\2\2\2\3x\3\2\2\2\3z\3\2\2\2\3|\3\2\2\2\4~\3\2\2\2\6\u0086"+
		"\3\2\2\2\b\u008b\3\2\2\2\n\u0090\3\2\2\2\f\u00a1\3\2\2\2\16\u00a4\3\2"+
		"\2\2\20\u00af\3\2\2\2\22\u00b3\3\2\2\2\24\u00c2\3\2\2\2\26\u00cb\3\2\2"+
		"\2\30\u00cf\3\2\2\2\32\u00d9\3\2\2\2\34\u00db\3\2\2\2\36\u00df\3\2\2\2"+
		" \u00e1\3\2\2\2\"\u00e5\3\2\2\2$\u00e7\3\2\2\2&\u00eb\3\2\2\2(\u00ed\3"+
		"\2\2\2*\u00ef\3\2\2\2,\u00f1\3\2\2\2.\u00f3\3\2\2\2\60\u00f5\3\2\2\2\62"+
		"\u00fa\3\2\2\2\64\u00ff\3\2\2\2\66\u0102\3\2\2\28\u0105\3\2\2\2:\u0108"+
		"\3\2\2\2<\u010b\3\2\2\2>\u010d\3\2\2\2@\u0110\3\2\2\2B\u0112\3\2\2\2D"+
		"\u0115\3\2\2\2F\u0117\3\2\2\2H\u0119\3\2\2\2J\u011b\3\2\2\2L\u011d\3\2"+
		"\2\2N\u011f\3\2\2\2P\u0121\3\2\2\2R\u0123\3\2\2\2T\u0125\3\2\2\2V\u012b"+
		"\3\2\2\2X\u013b\3\2\2\2Z\u013f\3\2\2\2\\\u0153\3\2\2\2^\u0155\3\2\2\2"+
		"`\u0160\3\2\2\2b\u016c\3\2\2\2d\u0172\3\2\2\2f\u0181\3\2\2\2h\u0187\3"+
		"\2\2\2j\u0192\3\2\2\2l\u019c\3\2\2\2n\u01a3\3\2\2\2p\u01a5\3\2\2\2r\u01b1"+
		"\3\2\2\2t\u01bb\3\2\2\2v\u01c1\3\2\2\2x\u01cf\3\2\2\2z\u01ec\3\2\2\2|"+
		"\u01f0\3\2\2\2~\177\7d\2\2\177\u0080\7t\2\2\u0080\u0081\7g\2\2\u0081\u0082"+
		"\7c\2\2\u0082\u0083\7m\2\2\u0083\u0084\3\2\2\2\u0084\u0085\b\2\2\2\u0085"+
		"\5\3\2\2\2\u0086\u0087\7h\2\2\u0087\u0088\7w\2\2\u0088\u0089\7p\2\2\u0089"+
		"\u008a\7e\2\2\u008a\7\3\2\2\2\u008b\u008c\7g\2\2\u008c\u008d\7n\2\2\u008d"+
		"\u008e\7u\2\2\u008e\u008f\7g\2\2\u008f\t\3\2\2\2\u0090\u0091\7r\2\2\u0091"+
		"\u0092\7c\2\2\u0092\u0093\7e\2\2\u0093\u0094\7m\2\2\u0094\u0095\7c\2\2"+
		"\u0095\u0096\7i\2\2\u0096\u0097\7g\2\2\u0097\u0098\7\"\2\2\u0098\u009c"+
		"\3\2\2\2\u0099\u009b\t\2\2\2\u009a\u0099\3\2\2\2\u009b\u009e\3\2\2\2\u009c"+
		"\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009f\3\2\2\2\u009e\u009c\3\2"+
		"\2\2\u009f\u00a0\b\5\3\2\u00a0\13\3\2\2\2\u00a1\u00a2\7k\2\2\u00a2\u00a3"+
		"\7h\2\2\u00a3\r\3\2\2\2\u00a4\u00a5\7e\2\2\u00a5\u00a6\7q\2\2\u00a6\u00a7"+
		"\7p\2\2\u00a7\u00a8\7v\2\2\u00a8\u00a9\7k\2\2\u00a9\u00aa\7p\2\2\u00aa"+
		"\u00ab\7w\2\2\u00ab\u00ac\7g\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae\b\7\2"+
		"\2\u00ae\17\3\2\2\2\u00af\u00b0\7h\2\2\u00b0\u00b1\7q\2\2\u00b1\u00b2"+
		"\7t\2\2\u00b2\21\3\2\2\2\u00b3\u00b4\7k\2\2\u00b4\u00b5\7o\2\2\u00b5\u00b6"+
		"\7r\2\2\u00b6\u00b7\7q\2\2\u00b7\u00b8\7t\2\2\u00b8\u00b9\7v\2\2\u00b9"+
		"\u00ba\7\"\2\2\u00ba\u00bb\7$\2\2\u00bb\u00bc\7h\2\2\u00bc\u00bd\7o\2"+
		"\2\u00bd\u00be\7v\2\2\u00be\u00bf\7$\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00c1"+
		"\b\t\3\2\u00c1\23\3\2\2\2\u00c2\u00c3\7t\2\2\u00c3\u00c4\7g\2\2\u00c4"+
		"\u00c5\7v\2\2\u00c5\u00c6\7w\2\2\u00c6\u00c7\7t\2\2\u00c7\u00c8\7p\2\2"+
		"\u00c8\u00c9\3\2\2\2\u00c9\u00ca\b\n\2\2\u00ca\25\3\2\2\2\u00cb\u00cc"+
		"\7x\2\2\u00cc\u00cd\7c\2\2\u00cd\u00ce\7t\2\2\u00ce\27\3\2\2\2\u00cf\u00d4"+
		"\5n\67\2\u00d0\u00d3\5n\67\2\u00d1\u00d3\5j\65\2\u00d2\u00d0\3\2\2\2\u00d2"+
		"\u00d1\3\2\2\2\u00d3\u00d6\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5\3\2"+
		"\2\2\u00d5\u00d7\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d7\u00d8\b\f\2\2\u00d8"+
		"\31\3\2\2\2\u00d9\u00da\7*\2\2\u00da\33\3\2\2\2\u00db\u00dc\7+\2\2\u00dc"+
		"\u00dd\3\2\2\2\u00dd\u00de\b\16\2\2\u00de\35\3\2\2\2\u00df\u00e0\7}\2"+
		"\2\u00e0\37\3\2\2\2\u00e1\u00e2\7\177\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4"+
		"\b\20\2\2\u00e4!\3\2\2\2\u00e5\u00e6\7]\2\2\u00e6#\3\2\2\2\u00e7\u00e8"+
		"\7_\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00ea\b\22\2\2\u00ea%\3\2\2\2\u00eb"+
		"\u00ec\7?\2\2\u00ec\'\3\2\2\2\u00ed\u00ee\7.\2\2\u00ee)\3\2\2\2\u00ef"+
		"\u00f0\7=\2\2\u00f0+\3\2\2\2\u00f1\u00f2\7<\2\2\u00f2-\3\2\2\2\u00f3\u00f4"+
		"\7\60\2\2\u00f4/\3\2\2\2\u00f5\u00f6\7-\2\2\u00f6\u00f7\7-\2\2\u00f7\u00f8"+
		"\3\2\2\2\u00f8\u00f9\b\30\2\2\u00f9\61\3\2\2\2\u00fa\u00fb\7/\2\2\u00fb"+
		"\u00fc\7/\2\2\u00fc\u00fd\3\2\2\2\u00fd\u00fe\b\31\2\2\u00fe\63\3\2\2"+
		"\2\u00ff\u0100\7~\2\2\u0100\u0101\7~\2\2\u0101\65\3\2\2\2\u0102\u0103"+
		"\7(\2\2\u0103\u0104\7(\2\2\u0104\67\3\2\2\2\u0105\u0106\7?\2\2\u0106\u0107"+
		"\7?\2\2\u01079\3\2\2\2\u0108\u0109\7#\2\2\u0109\u010a\7?\2\2\u010a;\3"+
		"\2\2\2\u010b\u010c\7>\2\2\u010c=\3\2\2\2\u010d\u010e\7>\2\2\u010e\u010f"+
		"\7?\2\2\u010f?\3\2\2\2\u0110\u0111\7@\2\2\u0111A\3\2\2\2\u0112\u0113\7"+
		"@\2\2\u0113\u0114\7?\2\2\u0114C\3\2\2\2\u0115\u0116\7~\2\2\u0116E\3\2"+
		"\2\2\u0117\u0118\7\61\2\2\u0118G\3\2\2\2\u0119\u011a\7\'\2\2\u011aI\3"+
		"\2\2\2\u011b\u011c\7#\2\2\u011cK\3\2\2\2\u011d\u011e\7-\2\2\u011eM\3\2"+
		"\2\2\u011f\u0120\7/\2\2\u0120O\3\2\2\2\u0121\u0122\7`\2\2\u0122Q\3\2\2"+
		"\2\u0123\u0124\7,\2\2\u0124S\3\2\2\2\u0125\u0126\7h\2\2\u0126\u0127\7"+
		"c\2\2\u0127\u0128\7n\2\2\u0128\u0129\7u\2\2\u0129\u012a\7g\2\2\u012aU"+
		"\3\2\2\2\u012b\u012c\7v\2\2\u012c\u012d\7t\2\2\u012d\u012e\7w\2\2\u012e"+
		"\u012f\7g\2\2\u012fW\3\2\2\2\u0130\u013c\7\62\2\2\u0131\u0138\t\3\2\2"+
		"\u0132\u0134\7a\2\2\u0133\u0132\3\2\2\2\u0133\u0134\3\2\2\2\u0134\u0135"+
		"\3\2\2\2\u0135\u0137\t\4\2\2\u0136\u0133\3\2\2\2\u0137\u013a\3\2\2\2\u0138"+
		"\u0136\3\2\2\2\u0138\u0139\3\2\2\2\u0139\u013c\3\2\2\2\u013a\u0138\3\2"+
		"\2\2\u013b\u0130\3\2\2\2\u013b\u0131\3\2\2\2\u013c\u013d\3\2\2\2\u013d"+
		"\u013e\b,\2\2\u013eY\3\2\2\2\u013f\u0140\5\\.\2\u0140\u0141\3\2\2\2\u0141"+
		"\u0142\b-\2\2\u0142[\3\2\2\2\u0143\u014c\5j\65\2\u0144\u0146\7\60\2\2"+
		"\u0145\u0147\5j\65\2\u0146\u0145\3\2\2\2\u0146\u0147\3\2\2\2\u0147\u0149"+
		"\3\2\2\2\u0148\u014a\5l\66\2\u0149\u0148\3\2\2\2\u0149\u014a\3\2\2\2\u014a"+
		"\u014d\3\2\2\2\u014b\u014d\5l\66\2\u014c\u0144\3\2\2\2\u014c\u014b\3\2"+
		"\2\2\u014d\u0154\3\2\2\2\u014e\u014f\7\60\2\2\u014f\u0151\5j\65\2\u0150"+
		"\u0152\5l\66\2\u0151\u0150\3\2\2\2\u0151\u0152\3\2\2\2\u0152\u0154\3\2"+
		"\2\2\u0153\u0143\3\2\2\2\u0153\u014e\3\2\2\2\u0154]\3\2\2\2\u0155\u0159"+
		"\7b\2\2\u0156\u0158\n\5\2\2\u0157\u0156\3\2\2\2\u0158\u015b\3\2\2\2\u0159"+
		"\u0157\3\2\2\2\u0159\u015a\3\2\2\2\u015a\u015c\3\2\2\2\u015b\u0159\3\2"+
		"\2\2\u015c\u015d\7b\2\2\u015d\u015e\3\2\2\2\u015e\u015f\b/\2\2\u015f_"+
		"\3\2\2\2\u0160\u0164\7$\2\2\u0161\u0163\n\6\2\2\u0162\u0161\3\2\2\2\u0163"+
		"\u0166\3\2\2\2\u0164\u0162\3\2\2\2\u0164\u0165\3\2\2\2\u0165\u0167\3\2"+
		"\2\2\u0166\u0164\3\2\2\2\u0167\u0168\7$\2\2\u0168\u0169\3\2\2\2\u0169"+
		"\u016a\b\60\2\2\u016aa\3\2\2\2\u016b\u016d\t\7\2\2\u016c\u016b\3\2\2\2"+
		"\u016d\u016e\3\2\2\2\u016e\u016c\3\2\2\2\u016e\u016f\3\2\2\2\u016f\u0170"+
		"\3\2\2\2\u0170\u0171\b\61\4\2\u0171c\3\2\2\2\u0172\u0173\7\61\2\2\u0173"+
		"\u0174\7,\2\2\u0174\u0178\3\2\2\2\u0175\u0177\13\2\2\2\u0176\u0175\3\2"+
		"\2\2\u0177\u017a\3\2\2\2\u0178\u0179\3\2\2\2\u0178\u0176\3\2\2\2\u0179"+
		"\u017b\3\2\2\2\u017a\u0178\3\2\2\2\u017b\u017c\7,\2\2\u017c\u017d\7\61"+
		"\2\2\u017d\u017e\3\2\2\2\u017e\u017f\b\62\4\2\u017fe\3\2\2\2\u0180\u0182"+
		"\t\b\2\2\u0181\u0180\3\2\2\2\u0182\u0183\3\2\2\2\u0183\u0181\3\2\2\2\u0183"+
		"\u0184\3\2\2\2\u0184\u0185\3\2\2\2\u0185\u0186\b\63\4\2\u0186g\3\2\2\2"+
		"\u0187\u0188\7\61\2\2\u0188\u0189\7\61\2\2\u0189\u018d\3\2\2\2\u018a\u018c"+
		"\n\b\2\2\u018b\u018a\3\2\2\2\u018c\u018f\3\2\2\2\u018d\u018b\3\2\2\2\u018d"+
		"\u018e\3\2\2\2\u018e\u0190\3\2\2\2\u018f\u018d\3\2\2\2\u0190\u0191\b\64"+
		"\4\2\u0191i\3\2\2\2\u0192\u0199\t\4\2\2\u0193\u0195\7a\2\2\u0194\u0193"+
		"\3\2\2\2\u0194\u0195\3\2\2\2\u0195\u0196\3\2\2\2\u0196\u0198\t\4\2\2\u0197"+
		"\u0194\3\2\2\2\u0198\u019b\3\2\2\2\u0199\u0197\3\2\2\2\u0199\u019a\3\2"+
		"\2\2\u019ak\3\2\2\2\u019b\u0199\3\2\2\2\u019c\u019e\t\t\2\2\u019d\u019f"+
		"\t\n\2\2\u019e\u019d\3\2\2\2\u019e\u019f\3\2\2\2\u019f\u01a0\3\2\2\2\u01a0"+
		"\u01a1\5j\65\2\u01a1m\3\2\2\2\u01a2\u01a4\t\13\2\2\u01a3\u01a2\3\2\2\2"+
		"\u01a4o\3\2\2\2\u01a5\u01a6\7h\2\2\u01a6\u01a7\7o\2\2\u01a7\u01a8\7v\2"+
		"\2\u01a8\u01a9\7\60\2\2\u01a9\u01aa\7R\2\2\u01aa\u01ab\7t\2\2\u01ab\u01ac"+
		"\7k\2\2\u01ac\u01ad\7p\2\2\u01ad\u01ae\7v\2\2\u01ae\u01af\7n\2\2\u01af"+
		"\u01b0\7p\2\2\u01b0q\3\2\2\2\u01b1\u01b2\7h\2\2\u01b2\u01b3\7o\2\2\u01b3"+
		"\u01b4\7v\2\2\u01b4\u01b5\7\60\2\2\u01b5\u01b6\7U\2\2\u01b6\u01b7\7e\2"+
		"\2\u01b7\u01b8\7c\2\2\u01b8\u01b9\7p\2\2\u01b9s\3\2\2\2\u01ba\u01bc\t"+
		"\7\2\2\u01bb\u01ba\3\2\2\2\u01bc\u01bd\3\2\2\2\u01bd\u01bb\3\2\2\2\u01bd"+
		"\u01be\3\2\2\2\u01be\u01bf\3\2\2\2\u01bf\u01c0\b:\4\2\u01c0u\3\2\2\2\u01c1"+
		"\u01c2\7\61\2\2\u01c2\u01c3\7,\2\2\u01c3\u01c7\3\2\2\2\u01c4\u01c6\n\b"+
		"\2\2\u01c5\u01c4\3\2\2\2\u01c6\u01c9\3\2\2\2\u01c7\u01c8\3\2\2\2\u01c7"+
		"\u01c5\3\2\2\2\u01c8\u01ca\3\2\2\2\u01c9\u01c7\3\2\2\2\u01ca\u01cb\7,"+
		"\2\2\u01cb\u01cc\7\61\2\2\u01cc\u01cd\3\2\2\2\u01cd\u01ce\b;\4\2\u01ce"+
		"w\3\2\2\2\u01cf\u01d0\7\61\2\2\u01d0\u01d1\7\61\2\2\u01d1\u01d5\3\2\2"+
		"\2\u01d2\u01d4\n\b\2\2\u01d3\u01d2\3\2\2\2\u01d4\u01d7\3\2\2\2\u01d5\u01d3"+
		"\3\2\2\2\u01d5\u01d6\3\2\2\2\u01d6\u01d8\3\2\2\2\u01d7\u01d5\3\2\2\2\u01d8"+
		"\u01d9\b<\4\2\u01d9y\3\2\2\2\u01da\u01dc\t\b\2\2\u01db\u01da\3\2\2\2\u01dc"+
		"\u01dd\3\2\2\2\u01dd\u01db\3\2\2\2\u01dd\u01de\3\2\2\2\u01de\u01ed\3\2"+
		"\2\2\u01df\u01ed\7=\2\2\u01e0\u01e1\7\61\2\2\u01e1\u01e2\7,\2\2\u01e2"+
		"\u01e6\3\2\2\2\u01e3\u01e5\13\2\2\2\u01e4\u01e3\3\2\2\2\u01e5\u01e8\3"+
		"\2\2\2\u01e6\u01e7\3\2\2\2\u01e6\u01e4\3\2\2\2\u01e7\u01e9\3\2\2\2\u01e8"+
		"\u01e6\3\2\2\2\u01e9\u01ea\7,\2\2\u01ea\u01ed\7\61\2\2\u01eb\u01ed\7\2"+
		"\2\3\u01ec\u01db\3\2\2\2\u01ec\u01df\3\2\2\2\u01ec\u01e0\3\2\2\2\u01ec"+
		"\u01eb\3\2\2\2\u01ed\u01ee\3\2\2\2\u01ee\u01ef\b=\5\2\u01ef{\3\2\2\2\u01f0"+
		"\u01f1\3\2\2\2\u01f1\u01f2\3\2\2\2\u01f2\u01f3\b>\5\2\u01f3\u01f4\b>\4"+
		"\2\u01f4}\3\2\2\2\37\2\3\u009c\u00d2\u00d4\u0133\u0138\u013b\u0146\u0149"+
		"\u014c\u0151\u0153\u0159\u0164\u016e\u0178\u0183\u018d\u0194\u0199\u019e"+
		"\u01a3\u01bd\u01c7\u01d5\u01dd\u01e6\u01ec\6\4\3\2\b\2\2\2\3\2\4\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}