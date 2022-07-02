// Generated from c:\Users\Abraão Santos\Dropbox\My PC (AbraaumSantos)\Documents\GitHub\Trab_Comp\GoLexer.g4 by ANTLR 4.9.2
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
		BREAK=1, DEFAULT=2, FUNC=3, SELECT=4, CASE=5, MAP=6, STRUCT=7, ELSE=8, 
		PACKAGE=9, SWITCH=10, CONST=11, FALLTHROUGH=12, IF=13, RANGE=14, TYPE=15, 
		CONTINUE=16, FOR=17, IMPORT=18, RETURN=19, VAR=20, IDENTIFIER=21, L_PAREN=22, 
		R_PAREN=23, L_CURLY=24, R_CURLY=25, L_BRACKET=26, R_BRACKET=27, ASSIGN=28, 
		COMMA=29, SEMI=30, COLON=31, DOT=32, PLUS_PLUS=33, MINUS_MINUS=34, LOGICAL_OR=35, 
		LOGICAL_AND=36, EQUALS=37, NOT_EQUALS=38, LESS=39, LESS_OR_EQUALS=40, 
		GREATER=41, GREATER_OR_EQUALS=42, OR=43, DIV=44, MOD=45, EXCLAMATION=46, 
		PLUS=47, MINUS=48, CARET=49, STAR=50, DECIMAL_LIT=51, FLOAT_LIT=52, DECIMAL_FLOAT_LIT=53, 
		RAW_STRING_LIT=54, INTERPRETED_STRING_LIT=55, WS=56, COMMENT=57, TERMINATOR=58, 
		LINE_COMMENT=59, PRINT=60, SCAN=61, WS_NLSEMI=62, COMMENT_NLSEMI=63, LINE_COMMENT_NLSEMI=64, 
		EOS=65, OTHER=66;
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
			"BREAK", "DEFAULT", "FUNC", "SELECT", "CASE", "MAP", "STRUCT", "ELSE", 
			"PACKAGE", "SWITCH", "CONST", "FALLTHROUGH", "IF", "RANGE", "TYPE", "CONTINUE", 
			"FOR", "IMPORT", "RETURN", "VAR", "IDENTIFIER", "L_PAREN", "R_PAREN", 
			"L_CURLY", "R_CURLY", "L_BRACKET", "R_BRACKET", "ASSIGN", "COMMA", "SEMI", 
			"COLON", "DOT", "PLUS_PLUS", "MINUS_MINUS", "LOGICAL_OR", "LOGICAL_AND", 
			"EQUALS", "NOT_EQUALS", "LESS", "LESS_OR_EQUALS", "GREATER", "GREATER_OR_EQUALS", 
			"OR", "DIV", "MOD", "EXCLAMATION", "PLUS", "MINUS", "CARET", "STAR", 
			"DECIMAL_LIT", "FLOAT_LIT", "DECIMAL_FLOAT_LIT", "RAW_STRING_LIT", "INTERPRETED_STRING_LIT", 
			"WS", "COMMENT", "TERMINATOR", "LINE_COMMENT", "DECIMALS", "EXPONENT", 
			"LETTER", "PRINT", "SCAN", "WS_NLSEMI", "COMMENT_NLSEMI", "LINE_COMMENT_NLSEMI", 
			"EOS", "OTHER"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'break'", "'default'", "'func'", "'select'", "'case'", "'map'", 
			"'struct'", "'else'", null, "'switch'", "'const'", "'fallthrough'", "'if'", 
			"'range'", "'type'", "'continue'", "'for'", "'import \"fmt\"'", "'return'", 
			"'var'", null, "'('", "')'", "'{'", "'}'", "'['", "']'", "'='", "','", 
			"';'", "':'", "'.'", "'++'", "'--'", "'||'", "'&&'", "'=='", "'!='", 
			"'<'", "'<='", "'>'", "'>='", "'|'", "'/'", "'%'", "'!'", "'+'", "'-'", 
			"'^'", "'*'", null, null, null, null, null, null, null, null, null, "'fmt.Println'", 
			"'fmt.Scan'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BREAK", "DEFAULT", "FUNC", "SELECT", "CASE", "MAP", "STRUCT", 
			"ELSE", "PACKAGE", "SWITCH", "CONST", "FALLTHROUGH", "IF", "RANGE", "TYPE", 
			"CONTINUE", "FOR", "IMPORT", "RETURN", "VAR", "IDENTIFIER", "L_PAREN", 
			"R_PAREN", "L_CURLY", "R_CURLY", "L_BRACKET", "R_BRACKET", "ASSIGN", 
			"COMMA", "SEMI", "COLON", "DOT", "PLUS_PLUS", "MINUS_MINUS", "LOGICAL_OR", 
			"LOGICAL_AND", "EQUALS", "NOT_EQUALS", "LESS", "LESS_OR_EQUALS", "GREATER", 
			"GREATER_OR_EQUALS", "OR", "DIV", "MOD", "EXCLAMATION", "PLUS", "MINUS", 
			"CARET", "STAR", "DECIMAL_LIT", "FLOAT_LIT", "DECIMAL_FLOAT_LIT", "RAW_STRING_LIT", 
			"INTERPRETED_STRING_LIT", "WS", "COMMENT", "TERMINATOR", "LINE_COMMENT", 
			"PRINT", "SCAN", "WS_NLSEMI", "COMMENT_NLSEMI", "LINE_COMMENT_NLSEMI", 
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2D\u023f\b\1\b\1\4"+
		"\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n"+
		"\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\7\n\u00ca\n\n\f\n\16\n\u00cd\13\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\7\26\u0128\n\26\f\26"+
		"\16\26\u012b\13\26\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3"+
		"\32\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\36\3\36\3"+
		"\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3%\3"+
		"%\3%\3&\3&\3&\3\'\3\'\3\'\3(\3(\3)\3)\3)\3*\3*\3+\3+\3+\3,\3,\3-\3-\3"+
		".\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\64\5\64"+
		"\u017e\n\64\3\64\7\64\u0181\n\64\f\64\16\64\u0184\13\64\5\64\u0186\n\64"+
		"\3\64\3\64\3\65\3\65\3\65\3\65\3\66\3\66\3\66\5\66\u0191\n\66\3\66\5\66"+
		"\u0194\n\66\3\66\5\66\u0197\n\66\3\66\3\66\3\66\5\66\u019c\n\66\5\66\u019e"+
		"\n\66\3\67\3\67\7\67\u01a2\n\67\f\67\16\67\u01a5\13\67\3\67\3\67\3\67"+
		"\3\67\38\38\78\u01ad\n8\f8\168\u01b0\138\38\38\38\38\39\69\u01b7\n9\r"+
		"9\169\u01b8\39\39\3:\3:\3:\3:\7:\u01c1\n:\f:\16:\u01c4\13:\3:\3:\3:\3"+
		":\3:\3;\6;\u01cc\n;\r;\16;\u01cd\3;\3;\3<\3<\3<\3<\7<\u01d6\n<\f<\16<"+
		"\u01d9\13<\3<\3<\3=\3=\5=\u01df\n=\3=\7=\u01e2\n=\f=\16=\u01e5\13=\3>"+
		"\3>\5>\u01e9\n>\3>\3>\3?\5?\u01ee\n?\3@\3@\3@\3@\3@\3@\3@\3@\3@\3@\3@"+
		"\3@\3A\3A\3A\3A\3A\3A\3A\3A\3A\3B\6B\u0206\nB\rB\16B\u0207\3B\3B\3C\3"+
		"C\3C\3C\7C\u0210\nC\fC\16C\u0213\13C\3C\3C\3C\3C\3C\3D\3D\3D\3D\7D\u021e"+
		"\nD\fD\16D\u0221\13D\3D\3D\3E\6E\u0226\nE\rE\16E\u0227\3E\3E\3E\3E\3E"+
		"\7E\u022f\nE\fE\16E\u0232\13E\3E\3E\3E\5E\u0237\nE\3E\3E\3F\3F\3F\3F\3"+
		"F\5\u01c2\u0211\u0230\2G\4\3\6\4\b\5\n\6\f\7\16\b\20\t\22\n\24\13\26\f"+
		"\30\r\32\16\34\17\36\20 \21\"\22$\23&\24(\25*\26,\27.\30\60\31\62\32\64"+
		"\33\66\348\35:\36<\37> @!B\"D#F$H%J&L\'N(P)R*T+V,X-Z.\\/^\60`\61b\62d"+
		"\63f\64h\65j\66l\67n8p9r:t;v<x=z\2|\2~\2\u0080>\u0082?\u0084@\u0086A\u0088"+
		"B\u008aC\u008cD\4\2\3\f\4\2C\\c|\3\2\63;\3\2\62;\3\2bb\4\2$$^^\4\2\13"+
		"\13\"\"\4\2\f\f\17\17\4\2GGgg\4\2--//\5\2C\\aac|\2\u0256\2\4\3\2\2\2\2"+
		"\6\3\2\2\2\2\b\3\2\2\2\2\n\3\2\2\2\2\f\3\2\2\2\2\16\3\2\2\2\2\20\3\2\2"+
		"\2\2\22\3\2\2\2\2\24\3\2\2\2\2\26\3\2\2\2\2\30\3\2\2\2\2\32\3\2\2\2\2"+
		"\34\3\2\2\2\2\36\3\2\2\2\2 \3\2\2\2\2\"\3\2\2\2\2$\3\2\2\2\2&\3\2\2\2"+
		"\2(\3\2\2\2\2*\3\2\2\2\2,\3\2\2\2\2.\3\2\2\2\2\60\3\2\2\2\2\62\3\2\2\2"+
		"\2\64\3\2\2\2\2\66\3\2\2\2\28\3\2\2\2\2:\3\2\2\2\2<\3\2\2\2\2>\3\2\2\2"+
		"\2@\3\2\2\2\2B\3\2\2\2\2D\3\2\2\2\2F\3\2\2\2\2H\3\2\2\2\2J\3\2\2\2\2L"+
		"\3\2\2\2\2N\3\2\2\2\2P\3\2\2\2\2R\3\2\2\2\2T\3\2\2\2\2V\3\2\2\2\2X\3\2"+
		"\2\2\2Z\3\2\2\2\2\\\3\2\2\2\2^\3\2\2\2\2`\3\2\2\2\2b\3\2\2\2\2d\3\2\2"+
		"\2\2f\3\2\2\2\2h\3\2\2\2\2j\3\2\2\2\2l\3\2\2\2\2n\3\2\2\2\2p\3\2\2\2\2"+
		"r\3\2\2\2\2t\3\2\2\2\2v\3\2\2\2\2x\3\2\2\2\2\u0080\3\2\2\2\2\u0082\3\2"+
		"\2\2\3\u0084\3\2\2\2\3\u0086\3\2\2\2\3\u0088\3\2\2\2\3\u008a\3\2\2\2\3"+
		"\u008c\3\2\2\2\4\u008e\3\2\2\2\6\u0096\3\2\2\2\b\u009e\3\2\2\2\n\u00a3"+
		"\3\2\2\2\f\u00aa\3\2\2\2\16\u00af\3\2\2\2\20\u00b3\3\2\2\2\22\u00ba\3"+
		"\2\2\2\24\u00bf\3\2\2\2\26\u00d0\3\2\2\2\30\u00d7\3\2\2\2\32\u00dd\3\2"+
		"\2\2\34\u00eb\3\2\2\2\36\u00ee\3\2\2\2 \u00f4\3\2\2\2\"\u00f9\3\2\2\2"+
		"$\u0104\3\2\2\2&\u0108\3\2\2\2(\u0117\3\2\2\2*\u0120\3\2\2\2,\u0124\3"+
		"\2\2\2.\u012e\3\2\2\2\60\u0130\3\2\2\2\62\u0134\3\2\2\2\64\u0136\3\2\2"+
		"\2\66\u013a\3\2\2\28\u013c\3\2\2\2:\u0140\3\2\2\2<\u0142\3\2\2\2>\u0144"+
		"\3\2\2\2@\u0146\3\2\2\2B\u0148\3\2\2\2D\u014a\3\2\2\2F\u014f\3\2\2\2H"+
		"\u0154\3\2\2\2J\u0157\3\2\2\2L\u015a\3\2\2\2N\u015d\3\2\2\2P\u0160\3\2"+
		"\2\2R\u0162\3\2\2\2T\u0165\3\2\2\2V\u0167\3\2\2\2X\u016a\3\2\2\2Z\u016c"+
		"\3\2\2\2\\\u016e\3\2\2\2^\u0170\3\2\2\2`\u0172\3\2\2\2b\u0174\3\2\2\2"+
		"d\u0176\3\2\2\2f\u0178\3\2\2\2h\u0185\3\2\2\2j\u0189\3\2\2\2l\u019d\3"+
		"\2\2\2n\u019f\3\2\2\2p\u01aa\3\2\2\2r\u01b6\3\2\2\2t\u01bc\3\2\2\2v\u01cb"+
		"\3\2\2\2x\u01d1\3\2\2\2z\u01dc\3\2\2\2|\u01e6\3\2\2\2~\u01ed\3\2\2\2\u0080"+
		"\u01ef\3\2\2\2\u0082\u01fb\3\2\2\2\u0084\u0205\3\2\2\2\u0086\u020b\3\2"+
		"\2\2\u0088\u0219\3\2\2\2\u008a\u0236\3\2\2\2\u008c\u023a\3\2\2\2\u008e"+
		"\u008f\7d\2\2\u008f\u0090\7t\2\2\u0090\u0091\7g\2\2\u0091\u0092\7c\2\2"+
		"\u0092\u0093\7m\2\2\u0093\u0094\3\2\2\2\u0094\u0095\b\2\2\2\u0095\5\3"+
		"\2\2\2\u0096\u0097\7f\2\2\u0097\u0098\7g\2\2\u0098\u0099\7h\2\2\u0099"+
		"\u009a\7c\2\2\u009a\u009b\7w\2\2\u009b\u009c\7n\2\2\u009c\u009d\7v\2\2"+
		"\u009d\7\3\2\2\2\u009e\u009f\7h\2\2\u009f\u00a0\7w\2\2\u00a0\u00a1\7p"+
		"\2\2\u00a1\u00a2\7e\2\2\u00a2\t\3\2\2\2\u00a3\u00a4\7u\2\2\u00a4\u00a5"+
		"\7g\2\2\u00a5\u00a6\7n\2\2\u00a6\u00a7\7g\2\2\u00a7\u00a8\7e\2\2\u00a8"+
		"\u00a9\7v\2\2\u00a9\13\3\2\2\2\u00aa\u00ab\7e\2\2\u00ab\u00ac\7c\2\2\u00ac"+
		"\u00ad\7u\2\2\u00ad\u00ae\7g\2\2\u00ae\r\3\2\2\2\u00af\u00b0\7o\2\2\u00b0"+
		"\u00b1\7c\2\2\u00b1\u00b2\7r\2\2\u00b2\17\3\2\2\2\u00b3\u00b4\7u\2\2\u00b4"+
		"\u00b5\7v\2\2\u00b5\u00b6\7t\2\2\u00b6\u00b7\7w\2\2\u00b7\u00b8\7e\2\2"+
		"\u00b8\u00b9\7v\2\2\u00b9\21\3\2\2\2\u00ba\u00bb\7g\2\2\u00bb\u00bc\7"+
		"n\2\2\u00bc\u00bd\7u\2\2\u00bd\u00be\7g\2\2\u00be\23\3\2\2\2\u00bf\u00c0"+
		"\7r\2\2\u00c0\u00c1\7c\2\2\u00c1\u00c2\7e\2\2\u00c2\u00c3\7m\2\2\u00c3"+
		"\u00c4\7c\2\2\u00c4\u00c5\7i\2\2\u00c5\u00c6\7g\2\2\u00c6\u00c7\7\"\2"+
		"\2\u00c7\u00cb\3\2\2\2\u00c8\u00ca\t\2\2\2\u00c9\u00c8\3\2\2\2\u00ca\u00cd"+
		"\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00ce\3\2\2\2\u00cd"+
		"\u00cb\3\2\2\2\u00ce\u00cf\b\n\3\2\u00cf\25\3\2\2\2\u00d0\u00d1\7u\2\2"+
		"\u00d1\u00d2\7y\2\2\u00d2\u00d3\7k\2\2\u00d3\u00d4\7v\2\2\u00d4\u00d5"+
		"\7e\2\2\u00d5\u00d6\7j\2\2\u00d6\27\3\2\2\2\u00d7\u00d8\7e\2\2\u00d8\u00d9"+
		"\7q\2\2\u00d9\u00da\7p\2\2\u00da\u00db\7u\2\2\u00db\u00dc\7v\2\2\u00dc"+
		"\31\3\2\2\2\u00dd\u00de\7h\2\2\u00de\u00df\7c\2\2\u00df\u00e0\7n\2\2\u00e0"+
		"\u00e1\7n\2\2\u00e1\u00e2\7v\2\2\u00e2\u00e3\7j\2\2\u00e3\u00e4\7t\2\2"+
		"\u00e4\u00e5\7q\2\2\u00e5\u00e6\7w\2\2\u00e6\u00e7\7i\2\2\u00e7\u00e8"+
		"\7j\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00ea\b\r\2\2\u00ea\33\3\2\2\2\u00eb"+
		"\u00ec\7k\2\2\u00ec\u00ed\7h\2\2\u00ed\35\3\2\2\2\u00ee\u00ef\7t\2\2\u00ef"+
		"\u00f0\7c\2\2\u00f0\u00f1\7p\2\2\u00f1\u00f2\7i\2\2\u00f2\u00f3\7g\2\2"+
		"\u00f3\37\3\2\2\2\u00f4\u00f5\7v\2\2\u00f5\u00f6\7{\2\2\u00f6\u00f7\7"+
		"r\2\2\u00f7\u00f8\7g\2\2\u00f8!\3\2\2\2\u00f9\u00fa\7e\2\2\u00fa\u00fb"+
		"\7q\2\2\u00fb\u00fc\7p\2\2\u00fc\u00fd\7v\2\2\u00fd\u00fe\7k\2\2\u00fe"+
		"\u00ff\7p\2\2\u00ff\u0100\7w\2\2\u0100\u0101\7g\2\2\u0101\u0102\3\2\2"+
		"\2\u0102\u0103\b\21\2\2\u0103#\3\2\2\2\u0104\u0105\7h\2\2\u0105\u0106"+
		"\7q\2\2\u0106\u0107\7t\2\2\u0107%\3\2\2\2\u0108\u0109\7k\2\2\u0109\u010a"+
		"\7o\2\2\u010a\u010b\7r\2\2\u010b\u010c\7q\2\2\u010c\u010d\7t\2\2\u010d"+
		"\u010e\7v\2\2\u010e\u010f\7\"\2\2\u010f\u0110\7$\2\2\u0110\u0111\7h\2"+
		"\2\u0111\u0112\7o\2\2\u0112\u0113\7v\2\2\u0113\u0114\7$\2\2\u0114\u0115"+
		"\3\2\2\2\u0115\u0116\b\23\3\2\u0116\'\3\2\2\2\u0117\u0118\7t\2\2\u0118"+
		"\u0119\7g\2\2\u0119\u011a\7v\2\2\u011a\u011b\7w\2\2\u011b\u011c\7t\2\2"+
		"\u011c\u011d\7p\2\2\u011d\u011e\3\2\2\2\u011e\u011f\b\24\2\2\u011f)\3"+
		"\2\2\2\u0120\u0121\7x\2\2\u0121\u0122\7c\2\2\u0122\u0123\7t\2\2\u0123"+
		"+\3\2\2\2\u0124\u0129\5~?\2\u0125\u0128\5~?\2\u0126\u0128\5z=\2\u0127"+
		"\u0125\3\2\2\2\u0127\u0126\3\2\2\2\u0128\u012b\3\2\2\2\u0129\u0127\3\2"+
		"\2\2\u0129\u012a\3\2\2\2\u012a\u012c\3\2\2\2\u012b\u0129\3\2\2\2\u012c"+
		"\u012d\b\26\2\2\u012d-\3\2\2\2\u012e\u012f\7*\2\2\u012f/\3\2\2\2\u0130"+
		"\u0131\7+\2\2\u0131\u0132\3\2\2\2\u0132\u0133\b\30\2\2\u0133\61\3\2\2"+
		"\2\u0134\u0135\7}\2\2\u0135\63\3\2\2\2\u0136\u0137\7\177\2\2\u0137\u0138"+
		"\3\2\2\2\u0138\u0139\b\32\2\2\u0139\65\3\2\2\2\u013a\u013b\7]\2\2\u013b"+
		"\67\3\2\2\2\u013c\u013d\7_\2\2\u013d\u013e\3\2\2\2\u013e\u013f\b\34\2"+
		"\2\u013f9\3\2\2\2\u0140\u0141\7?\2\2\u0141;\3\2\2\2\u0142\u0143\7.\2\2"+
		"\u0143=\3\2\2\2\u0144\u0145\7=\2\2\u0145?\3\2\2\2\u0146\u0147\7<\2\2\u0147"+
		"A\3\2\2\2\u0148\u0149\7\60\2\2\u0149C\3\2\2\2\u014a\u014b\7-\2\2\u014b"+
		"\u014c\7-\2\2\u014c\u014d\3\2\2\2\u014d\u014e\b\"\2\2\u014eE\3\2\2\2\u014f"+
		"\u0150\7/\2\2\u0150\u0151\7/\2\2\u0151\u0152\3\2\2\2\u0152\u0153\b#\2"+
		"\2\u0153G\3\2\2\2\u0154\u0155\7~\2\2\u0155\u0156\7~\2\2\u0156I\3\2\2\2"+
		"\u0157\u0158\7(\2\2\u0158\u0159\7(\2\2\u0159K\3\2\2\2\u015a\u015b\7?\2"+
		"\2\u015b\u015c\7?\2\2\u015cM\3\2\2\2\u015d\u015e\7#\2\2\u015e\u015f\7"+
		"?\2\2\u015fO\3\2\2\2\u0160\u0161\7>\2\2\u0161Q\3\2\2\2\u0162\u0163\7>"+
		"\2\2\u0163\u0164\7?\2\2\u0164S\3\2\2\2\u0165\u0166\7@\2\2\u0166U\3\2\2"+
		"\2\u0167\u0168\7@\2\2\u0168\u0169\7?\2\2\u0169W\3\2\2\2\u016a\u016b\7"+
		"~\2\2\u016bY\3\2\2\2\u016c\u016d\7\61\2\2\u016d[\3\2\2\2\u016e\u016f\7"+
		"\'\2\2\u016f]\3\2\2\2\u0170\u0171\7#\2\2\u0171_\3\2\2\2\u0172\u0173\7"+
		"-\2\2\u0173a\3\2\2\2\u0174\u0175\7/\2\2\u0175c\3\2\2\2\u0176\u0177\7`"+
		"\2\2\u0177e\3\2\2\2\u0178\u0179\7,\2\2\u0179g\3\2\2\2\u017a\u0186\7\62"+
		"\2\2\u017b\u0182\t\3\2\2\u017c\u017e\7a\2\2\u017d\u017c\3\2\2\2\u017d"+
		"\u017e\3\2\2\2\u017e\u017f\3\2\2\2\u017f\u0181\t\4\2\2\u0180\u017d\3\2"+
		"\2\2\u0181\u0184\3\2\2\2\u0182\u0180\3\2\2\2\u0182\u0183\3\2\2\2\u0183"+
		"\u0186\3\2\2\2\u0184\u0182\3\2\2\2\u0185\u017a\3\2\2\2\u0185\u017b\3\2"+
		"\2\2\u0186\u0187\3\2\2\2\u0187\u0188\b\64\2\2\u0188i\3\2\2\2\u0189\u018a"+
		"\5l\66\2\u018a\u018b\3\2\2\2\u018b\u018c\b\65\2\2\u018ck\3\2\2\2\u018d"+
		"\u0196\5z=\2\u018e\u0190\7\60\2\2\u018f\u0191\5z=\2\u0190\u018f\3\2\2"+
		"\2\u0190\u0191\3\2\2\2\u0191\u0193\3\2\2\2\u0192\u0194\5|>\2\u0193\u0192"+
		"\3\2\2\2\u0193\u0194\3\2\2\2\u0194\u0197\3\2\2\2\u0195\u0197\5|>\2\u0196"+
		"\u018e\3\2\2\2\u0196\u0195\3\2\2\2\u0197\u019e\3\2\2\2\u0198\u0199\7\60"+
		"\2\2\u0199\u019b\5z=\2\u019a\u019c\5|>\2\u019b\u019a\3\2\2\2\u019b\u019c"+
		"\3\2\2\2\u019c\u019e\3\2\2\2\u019d\u018d\3\2\2\2\u019d\u0198\3\2\2\2\u019e"+
		"m\3\2\2\2\u019f\u01a3\7b\2\2\u01a0\u01a2\n\5\2\2\u01a1\u01a0\3\2\2\2\u01a2"+
		"\u01a5\3\2\2\2\u01a3\u01a1\3\2\2\2\u01a3\u01a4\3\2\2\2\u01a4\u01a6\3\2"+
		"\2\2\u01a5\u01a3\3\2\2\2\u01a6\u01a7\7b\2\2\u01a7\u01a8\3\2\2\2\u01a8"+
		"\u01a9\b\67\2\2\u01a9o\3\2\2\2\u01aa\u01ae\7$\2\2\u01ab\u01ad\n\6\2\2"+
		"\u01ac\u01ab\3\2\2\2\u01ad\u01b0\3\2\2\2\u01ae\u01ac\3\2\2\2\u01ae\u01af"+
		"\3\2\2\2\u01af\u01b1\3\2\2\2\u01b0\u01ae\3\2\2\2\u01b1\u01b2\7$\2\2\u01b2"+
		"\u01b3\3\2\2\2\u01b3\u01b4\b8\2\2\u01b4q\3\2\2\2\u01b5\u01b7\t\7\2\2\u01b6"+
		"\u01b5\3\2\2\2\u01b7\u01b8\3\2\2\2\u01b8\u01b6\3\2\2\2\u01b8\u01b9\3\2"+
		"\2\2\u01b9\u01ba\3\2\2\2\u01ba\u01bb\b9\4\2\u01bbs\3\2\2\2\u01bc\u01bd"+
		"\7\61\2\2\u01bd\u01be\7,\2\2\u01be\u01c2\3\2\2\2\u01bf\u01c1\13\2\2\2"+
		"\u01c0\u01bf\3\2\2\2\u01c1\u01c4\3\2\2\2\u01c2\u01c3\3\2\2\2\u01c2\u01c0"+
		"\3\2\2\2\u01c3\u01c5\3\2\2\2\u01c4\u01c2\3\2\2\2\u01c5\u01c6\7,\2\2\u01c6"+
		"\u01c7\7\61\2\2\u01c7\u01c8\3\2\2\2\u01c8\u01c9\b:\4\2\u01c9u\3\2\2\2"+
		"\u01ca\u01cc\t\b\2\2\u01cb\u01ca\3\2\2\2\u01cc\u01cd\3\2\2\2\u01cd\u01cb"+
		"\3\2\2\2\u01cd\u01ce\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01d0\b;\4\2\u01d0"+
		"w\3\2\2\2\u01d1\u01d2\7\61\2\2\u01d2\u01d3\7\61\2\2\u01d3\u01d7\3\2\2"+
		"\2\u01d4\u01d6\n\b\2\2\u01d5\u01d4\3\2\2\2\u01d6\u01d9\3\2\2\2\u01d7\u01d5"+
		"\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d8\u01da\3\2\2\2\u01d9\u01d7\3\2\2\2\u01da"+
		"\u01db\b<\4\2\u01dby\3\2\2\2\u01dc\u01e3\t\4\2\2\u01dd\u01df\7a\2\2\u01de"+
		"\u01dd\3\2\2\2\u01de\u01df\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0\u01e2\t\4"+
		"\2\2\u01e1\u01de\3\2\2\2\u01e2\u01e5\3\2\2\2\u01e3\u01e1\3\2\2\2\u01e3"+
		"\u01e4\3\2\2\2\u01e4{\3\2\2\2\u01e5\u01e3\3\2\2\2\u01e6\u01e8\t\t\2\2"+
		"\u01e7\u01e9\t\n\2\2\u01e8\u01e7\3\2\2\2\u01e8\u01e9\3\2\2\2\u01e9\u01ea"+
		"\3\2\2\2\u01ea\u01eb\5z=\2\u01eb}\3\2\2\2\u01ec\u01ee\t\13\2\2\u01ed\u01ec"+
		"\3\2\2\2\u01ee\177\3\2\2\2\u01ef\u01f0\7h\2\2\u01f0\u01f1\7o\2\2\u01f1"+
		"\u01f2\7v\2\2\u01f2\u01f3\7\60\2\2\u01f3\u01f4\7R\2\2\u01f4\u01f5\7t\2"+
		"\2\u01f5\u01f6\7k\2\2\u01f6\u01f7\7p\2\2\u01f7\u01f8\7v\2\2\u01f8\u01f9"+
		"\7n\2\2\u01f9\u01fa\7p\2\2\u01fa\u0081\3\2\2\2\u01fb\u01fc\7h\2\2\u01fc"+
		"\u01fd\7o\2\2\u01fd\u01fe\7v\2\2\u01fe\u01ff\7\60\2\2\u01ff\u0200\7U\2"+
		"\2\u0200\u0201\7e\2\2\u0201\u0202\7c\2\2\u0202\u0203\7p\2\2\u0203\u0083"+
		"\3\2\2\2\u0204\u0206\t\7\2\2\u0205\u0204\3\2\2\2\u0206\u0207\3\2\2\2\u0207"+
		"\u0205\3\2\2\2\u0207\u0208\3\2\2\2\u0208\u0209\3\2\2\2\u0209\u020a\bB"+
		"\4\2\u020a\u0085\3\2\2\2\u020b\u020c\7\61\2\2\u020c\u020d\7,\2\2\u020d"+
		"\u0211\3\2\2\2\u020e\u0210\n\b\2\2\u020f\u020e\3\2\2\2\u0210\u0213\3\2"+
		"\2\2\u0211\u0212\3\2\2\2\u0211\u020f\3\2\2\2\u0212\u0214\3\2\2\2\u0213"+
		"\u0211\3\2\2\2\u0214\u0215\7,\2\2\u0215\u0216\7\61\2\2\u0216\u0217\3\2"+
		"\2\2\u0217\u0218\bC\4\2\u0218\u0087\3\2\2\2\u0219\u021a\7\61\2\2\u021a"+
		"\u021b\7\61\2\2\u021b\u021f\3\2\2\2\u021c\u021e\n\b\2\2\u021d\u021c\3"+
		"\2\2\2\u021e\u0221\3\2\2\2\u021f\u021d\3\2\2\2\u021f\u0220\3\2\2\2\u0220"+
		"\u0222\3\2\2\2\u0221\u021f\3\2\2\2\u0222\u0223\bD\4\2\u0223\u0089\3\2"+
		"\2\2\u0224\u0226\t\b\2\2\u0225\u0224\3\2\2\2\u0226\u0227\3\2\2\2\u0227"+
		"\u0225\3\2\2\2\u0227\u0228\3\2\2\2\u0228\u0237\3\2\2\2\u0229\u0237\7="+
		"\2\2\u022a\u022b\7\61\2\2\u022b\u022c\7,\2\2\u022c\u0230\3\2\2\2\u022d"+
		"\u022f\13\2\2\2\u022e\u022d\3\2\2\2\u022f\u0232\3\2\2\2\u0230\u0231\3"+
		"\2\2\2\u0230\u022e\3\2\2\2\u0231\u0233\3\2\2\2\u0232\u0230\3\2\2\2\u0233"+
		"\u0234\7,\2\2\u0234\u0237\7\61\2\2\u0235\u0237\7\2\2\3\u0236\u0225\3\2"+
		"\2\2\u0236\u0229\3\2\2\2\u0236\u022a\3\2\2\2\u0236\u0235\3\2\2\2\u0237"+
		"\u0238\3\2\2\2\u0238\u0239\bE\5\2\u0239\u008b\3\2\2\2\u023a\u023b\3\2"+
		"\2\2\u023b\u023c\3\2\2\2\u023c\u023d\bF\5\2\u023d\u023e\bF\4\2\u023e\u008d"+
		"\3\2\2\2\37\2\3\u00cb\u0127\u0129\u017d\u0182\u0185\u0190\u0193\u0196"+
		"\u019b\u019d\u01a3\u01ae\u01b8\u01c2\u01cd\u01d7\u01de\u01e3\u01e8\u01ed"+
		"\u0207\u0211\u021f\u0227\u0230\u0236\6\4\3\2\b\2\2\2\3\2\4\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}