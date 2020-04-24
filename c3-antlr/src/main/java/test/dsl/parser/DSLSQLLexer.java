// Generated from /Users/aston/mlsql/develop/wechat/c3-antlr/src/main/resources/DSLSQL.g4 by ANTLR 4.7.2

  package test.dsl.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DSLSQLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, AS=5, LOAD=6, SELECT=7, OPTIONS=8, STRING=9, 
		BACKQUOTED_IDENTIFIER=10, IDENTIFIER=11, BRACKETED_EMPTY_COMMENT=12, BRACKETED_COMMENT=13, 
		WS=14, UNRECOGNIZED=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "AS", "LOAD", "SELECT", "OPTIONS", "STRING", 
			"BACKQUOTED_IDENTIFIER", "DIGIT", "LETTER", "IDENTIFIER", "BRACKETED_EMPTY_COMMENT", 
			"BRACKETED_COMMENT", "WS", "UNRECOGNIZED"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'.'", "';'", "'and'", "'='", null, null, null, null, null, null, 
			null, "'/**/'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "AS", "LOAD", "SELECT", "OPTIONS", "STRING", 
			"BACKQUOTED_IDENTIFIER", "IDENTIFIER", "BRACKETED_EMPTY_COMMENT", "BRACKETED_COMMENT", 
			"WS", "UNRECOGNIZED"
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


	public DSLSQLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DSLSQL.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\21\u0091\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7"+
		"\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\n\3\n\3\n\3\n\7\nK\n\n\f\n\16\nN\13\n\3\n\3\n\3\n\3\n\3\n\7\nU\n\n\f"+
		"\n\16\nX\13\n\3\n\5\n[\n\n\3\13\3\13\3\13\3\13\7\13a\n\13\f\13\16\13d"+
		"\13\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\16\6\16o\n\16\r\16\16\16"+
		"p\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\7\20\177"+
		"\n\20\f\20\16\20\u0082\13\20\3\20\3\20\3\20\3\20\3\20\3\21\6\21\u008a"+
		"\n\21\r\21\16\21\u008b\3\21\3\21\3\22\3\22\3\u0080\2\23\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\25\f\27\2\31\2\33\r\35\16\37\17!\20#\21\3"+
		"\2\24\4\2CCcc\4\2UUuu\4\2NNnn\4\2QQqq\4\2FFff\4\2GGgg\4\2EEee\4\2VVvv"+
		"\4\2RRrr\4\2KKkk\4\2PPpp\4\2))^^\4\2$$^^\3\2bb\3\2\62;\4\2C\\c|\3\2--"+
		"\5\2\13\f\17\17\"\"\2\u009a\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3"+
		"\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\3%\3\2\2\2\5\'\3\2\2\2\7)\3\2\2\2\t-\3\2\2\2\13/\3\2\2\2\r\62\3\2"+
		"\2\2\17\67\3\2\2\2\21>\3\2\2\2\23Z\3\2\2\2\25\\\3\2\2\2\27g\3\2\2\2\31"+
		"i\3\2\2\2\33n\3\2\2\2\35r\3\2\2\2\37y\3\2\2\2!\u0089\3\2\2\2#\u008f\3"+
		"\2\2\2%&\7\60\2\2&\4\3\2\2\2\'(\7=\2\2(\6\3\2\2\2)*\7c\2\2*+\7p\2\2+,"+
		"\7f\2\2,\b\3\2\2\2-.\7?\2\2.\n\3\2\2\2/\60\t\2\2\2\60\61\t\3\2\2\61\f"+
		"\3\2\2\2\62\63\t\4\2\2\63\64\t\5\2\2\64\65\t\2\2\2\65\66\t\6\2\2\66\16"+
		"\3\2\2\2\678\t\3\2\289\t\7\2\29:\t\4\2\2:;\t\7\2\2;<\t\b\2\2<=\t\t\2\2"+
		"=\20\3\2\2\2>?\t\5\2\2?@\t\n\2\2@A\t\t\2\2AB\t\13\2\2BC\t\5\2\2CD\t\f"+
		"\2\2DE\t\3\2\2E\22\3\2\2\2FL\7)\2\2GK\n\r\2\2HI\7^\2\2IK\13\2\2\2JG\3"+
		"\2\2\2JH\3\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2MO\3\2\2\2NL\3\2\2\2O[\7"+
		")\2\2PV\7$\2\2QU\n\16\2\2RS\7^\2\2SU\13\2\2\2TQ\3\2\2\2TR\3\2\2\2UX\3"+
		"\2\2\2VT\3\2\2\2VW\3\2\2\2WY\3\2\2\2XV\3\2\2\2Y[\7$\2\2ZF\3\2\2\2ZP\3"+
		"\2\2\2[\24\3\2\2\2\\b\7b\2\2]a\n\17\2\2^_\7b\2\2_a\7b\2\2`]\3\2\2\2`^"+
		"\3\2\2\2ad\3\2\2\2b`\3\2\2\2bc\3\2\2\2ce\3\2\2\2db\3\2\2\2ef\7b\2\2f\26"+
		"\3\2\2\2gh\t\20\2\2h\30\3\2\2\2ij\t\21\2\2j\32\3\2\2\2ko\5\31\r\2lo\5"+
		"\27\f\2mo\7a\2\2nk\3\2\2\2nl\3\2\2\2nm\3\2\2\2op\3\2\2\2pn\3\2\2\2pq\3"+
		"\2\2\2q\34\3\2\2\2rs\7\61\2\2st\7,\2\2tu\7,\2\2uv\7\61\2\2vw\3\2\2\2w"+
		"x\b\17\2\2x\36\3\2\2\2yz\7\61\2\2z{\7,\2\2{|\3\2\2\2|\u0080\n\22\2\2}"+
		"\177\13\2\2\2~}\3\2\2\2\177\u0082\3\2\2\2\u0080\u0081\3\2\2\2\u0080~\3"+
		"\2\2\2\u0081\u0083\3\2\2\2\u0082\u0080\3\2\2\2\u0083\u0084\7,\2\2\u0084"+
		"\u0085\7\61\2\2\u0085\u0086\3\2\2\2\u0086\u0087\b\20\2\2\u0087 \3\2\2"+
		"\2\u0088\u008a\t\23\2\2\u0089\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b"+
		"\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008e\b\21"+
		"\2\2\u008e\"\3\2\2\2\u008f\u0090\13\2\2\2\u0090$\3\2\2\2\16\2JLTVZ`bn"+
		"p\u0080\u008b\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}