// Generated from /Users/aston/mlsql/develop/wechat/c3-antlr/src/main/resources/DSLSQL.g4 by ANTLR 4.7.2

  package test.dsl.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DSLSQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, AS=5, LOAD=6, SELECT=7, OPTIONS=8, STRING=9, 
		BACKQUOTED_IDENTIFIER=10, IDENTIFIER=11, BRACKETED_EMPTY_COMMENT=12, BRACKETED_COMMENT=13, 
		WS=14, UNRECOGNIZED=15;
	public static final int
		RULE_statement = 0, RULE_sql = 1, RULE_ender = 2, RULE_booleanExpression = 3, 
		RULE_expression = 4, RULE_format = 5, RULE_path = 6, RULE_tableName = 7, 
		RULE_qualifiedName = 8, RULE_identifier = 9;
	private static String[] makeRuleNames() {
		return new String[] {
			"statement", "sql", "ender", "booleanExpression", "expression", "format", 
			"path", "tableName", "qualifiedName", "identifier"
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

	@Override
	public String getGrammarFileName() { return "DSLSQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DSLSQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class StatementContext extends ParserRuleContext {
		public List<SqlContext> sql() {
			return getRuleContexts(SqlContext.class);
		}
		public SqlContext sql(int i) {
			return getRuleContext(SqlContext.class,i);
		}
		public List<EnderContext> ender() {
			return getRuleContexts(EnderContext.class);
		}
		public EnderContext ender(int i) {
			return getRuleContext(EnderContext.class,i);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLSQLVisitor ) return ((DSLSQLVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LOAD || _la==SELECT) {
				{
				{
				setState(20);
				sql();
				setState(21);
				ender();
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SqlContext extends ParserRuleContext {
		public TerminalNode LOAD() { return getToken(DSLSQLParser.LOAD, 0); }
		public FormatContext format() {
			return getRuleContext(FormatContext.class,0);
		}
		public PathContext path() {
			return getRuleContext(PathContext.class,0);
		}
		public TerminalNode AS() { return getToken(DSLSQLParser.AS, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode OPTIONS() { return getToken(DSLSQLParser.OPTIONS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<BooleanExpressionContext> booleanExpression() {
			return getRuleContexts(BooleanExpressionContext.class);
		}
		public BooleanExpressionContext booleanExpression(int i) {
			return getRuleContext(BooleanExpressionContext.class,i);
		}
		public TerminalNode SELECT() { return getToken(DSLSQLParser.SELECT, 0); }
		public SqlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sql; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).enterSql(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).exitSql(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLSQLVisitor ) return ((DSLSQLVisitor<? extends T>)visitor).visitSql(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SqlContext sql() throws RecognitionException {
		SqlContext _localctx = new SqlContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_sql);
		int _la;
		try {
			int _alt;
			setState(56);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LOAD:
				enterOuterAlt(_localctx, 1);
				{
				setState(28);
				match(LOAD);
				setState(29);
				format();
				setState(30);
				match(T__0);
				setState(31);
				path();
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OPTIONS) {
					{
					setState(32);
					match(OPTIONS);
					}
				}

				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==BACKQUOTED_IDENTIFIER || _la==IDENTIFIER) {
					{
					setState(35);
					expression();
					}
				}

				setState(41);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(38);
					booleanExpression();
					}
					}
					setState(43);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(44);
				match(AS);
				setState(45);
				tableName();
				}
				break;
			case SELECT:
				enterOuterAlt(_localctx, 2);
				{
				setState(47);
				match(SELECT);
				setState(51);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(48);
						_la = _input.LA(1);
						if ( _la <= 0 || (_la==T__1) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						} 
					}
					setState(53);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				}
				setState(54);
				match(AS);
				setState(55);
				tableName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnderContext extends ParserRuleContext {
		public EnderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ender; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).enterEnder(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).exitEnder(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLSQLVisitor ) return ((DSLSQLVisitor<? extends T>)visitor).visitEnder(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnderContext ender() throws RecognitionException {
		EnderContext _localctx = new EnderContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_ender);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BooleanExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).enterBooleanExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).exitBooleanExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLSQLVisitor ) return ((DSLSQLVisitor<? extends T>)visitor).visitBooleanExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanExpressionContext booleanExpression() throws RecognitionException {
		BooleanExpressionContext _localctx = new BooleanExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_booleanExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(T__2);
			setState(61);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode STRING() { return getToken(DSLSQLParser.STRING, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLSQLVisitor ) return ((DSLSQLVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			qualifiedName();
			setState(64);
			match(T__3);
			setState(65);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormatContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public FormatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_format; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).enterFormat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).exitFormat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLSQLVisitor ) return ((DSLSQLVisitor<? extends T>)visitor).visitFormat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormatContext format() throws RecognitionException {
		FormatContext _localctx = new FormatContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_format);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public PathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).enterPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).exitPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLSQLVisitor ) return ((DSLSQLVisitor<? extends T>)visitor).visitPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathContext path() throws RecognitionException {
		PathContext _localctx = new PathContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_path);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TableNameContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TableNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).enterTableName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).exitTableName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLSQLVisitor ) return ((DSLSQLVisitor<? extends T>)visitor).visitTableName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableNameContext tableName() throws RecognitionException {
		TableNameContext _localctx = new TableNameContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_tableName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QualifiedNameContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).enterQualifiedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).exitQualifiedName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLSQLVisitor ) return ((DSLSQLVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			identifier();
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(74);
				match(T__0);
				setState(75);
				identifier();
				}
				}
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(DSLSQLParser.IDENTIFIER, 0); }
		public TerminalNode BACKQUOTED_IDENTIFIER() { return getToken(DSLSQLParser.BACKQUOTED_IDENTIFIER, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLSQLListener ) ((DSLSQLListener)listener).exitIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLSQLVisitor ) return ((DSLSQLVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_identifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			_la = _input.LA(1);
			if ( !(_la==BACKQUOTED_IDENTIFIER || _la==IDENTIFIER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\21V\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\3"+
		"\2\3\2\3\2\7\2\32\n\2\f\2\16\2\35\13\2\3\3\3\3\3\3\3\3\3\3\5\3$\n\3\3"+
		"\3\5\3\'\n\3\3\3\7\3*\n\3\f\3\16\3-\13\3\3\3\3\3\3\3\3\3\3\3\7\3\64\n"+
		"\3\f\3\16\3\67\13\3\3\3\3\3\5\3;\n\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3"+
		"\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\7\nO\n\n\f\n\16\nR\13\n\3\13\3"+
		"\13\3\13\2\2\f\2\4\6\b\n\f\16\20\22\24\2\4\3\2\4\4\3\2\f\r\2R\2\33\3\2"+
		"\2\2\4:\3\2\2\2\6<\3\2\2\2\b>\3\2\2\2\nA\3\2\2\2\fE\3\2\2\2\16G\3\2\2"+
		"\2\20I\3\2\2\2\22K\3\2\2\2\24S\3\2\2\2\26\27\5\4\3\2\27\30\5\6\4\2\30"+
		"\32\3\2\2\2\31\26\3\2\2\2\32\35\3\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34"+
		"\3\3\2\2\2\35\33\3\2\2\2\36\37\7\b\2\2\37 \5\f\7\2 !\7\3\2\2!#\5\16\b"+
		"\2\"$\7\n\2\2#\"\3\2\2\2#$\3\2\2\2$&\3\2\2\2%\'\5\n\6\2&%\3\2\2\2&\'\3"+
		"\2\2\2\'+\3\2\2\2(*\5\b\5\2)(\3\2\2\2*-\3\2\2\2+)\3\2\2\2+,\3\2\2\2,."+
		"\3\2\2\2-+\3\2\2\2./\7\7\2\2/\60\5\20\t\2\60;\3\2\2\2\61\65\7\t\2\2\62"+
		"\64\n\2\2\2\63\62\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\66"+
		"8\3\2\2\2\67\65\3\2\2\289\7\7\2\29;\5\20\t\2:\36\3\2\2\2:\61\3\2\2\2;"+
		"\5\3\2\2\2<=\7\4\2\2=\7\3\2\2\2>?\7\5\2\2?@\5\n\6\2@\t\3\2\2\2AB\5\22"+
		"\n\2BC\7\6\2\2CD\7\13\2\2D\13\3\2\2\2EF\5\24\13\2F\r\3\2\2\2GH\5\24\13"+
		"\2H\17\3\2\2\2IJ\5\24\13\2J\21\3\2\2\2KP\5\24\13\2LM\7\3\2\2MO\5\24\13"+
		"\2NL\3\2\2\2OR\3\2\2\2PN\3\2\2\2PQ\3\2\2\2Q\23\3\2\2\2RP\3\2\2\2ST\t\3"+
		"\2\2T\25\3\2\2\2\t\33#&+\65:P";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}