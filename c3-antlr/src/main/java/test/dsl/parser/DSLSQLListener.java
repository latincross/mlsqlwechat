// Generated from /Users/aston/mlsql/develop/wechat/c3-antlr/src/main/resources/DSLSQL.g4 by ANTLR 4.7.2

  package test.dsl.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DSLSQLParser}.
 */
public interface DSLSQLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DSLSQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(DSLSQLParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLSQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(DSLSQLParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLSQLParser#sql}.
	 * @param ctx the parse tree
	 */
	void enterSql(DSLSQLParser.SqlContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLSQLParser#sql}.
	 * @param ctx the parse tree
	 */
	void exitSql(DSLSQLParser.SqlContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLSQLParser#ender}.
	 * @param ctx the parse tree
	 */
	void enterEnder(DSLSQLParser.EnderContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLSQLParser#ender}.
	 * @param ctx the parse tree
	 */
	void exitEnder(DSLSQLParser.EnderContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLSQLParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExpression(DSLSQLParser.BooleanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLSQLParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExpression(DSLSQLParser.BooleanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLSQLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(DSLSQLParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLSQLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(DSLSQLParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLSQLParser#format}.
	 * @param ctx the parse tree
	 */
	void enterFormat(DSLSQLParser.FormatContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLSQLParser#format}.
	 * @param ctx the parse tree
	 */
	void exitFormat(DSLSQLParser.FormatContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLSQLParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(DSLSQLParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLSQLParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(DSLSQLParser.PathContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLSQLParser#tableName}.
	 * @param ctx the parse tree
	 */
	void enterTableName(DSLSQLParser.TableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLSQLParser#tableName}.
	 * @param ctx the parse tree
	 */
	void exitTableName(DSLSQLParser.TableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLSQLParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(DSLSQLParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLSQLParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(DSLSQLParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLSQLParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(DSLSQLParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLSQLParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(DSLSQLParser.IdentifierContext ctx);
}