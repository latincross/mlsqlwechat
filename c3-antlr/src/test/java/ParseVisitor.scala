import test.dsl.parser.DSLSQLParser.{BooleanExpressionContext, ExpressionContext, FormatContext, PathContext, TableNameContext}
import test.dsl.parser.{DSLSQLBaseVisitor, DSLSQLParser}

/*
 * Created by respect on 22/04/2020
 * Email: wanp1989@126.com
 */

class ParseVisitor extends DSLSQLBaseVisitor[Object]{
  override def visitSql(ctx: DSLSQLParser.SqlContext): Object = {
    var format = ""
    var option = Map[String, String]()
    var path = ""
    var tableName = ""
    (0 to ctx.getChildCount() - 1).foreach { tokenIndex =>
      ctx.getChild(tokenIndex) match {
        case s: FormatContext =>
          format = s.getText
        case s: ExpressionContext =>
          option += (s.qualifiedName().getText -> s.STRING().getText)
        case s: BooleanExpressionContext =>
          option += (s.expression().qualifiedName().getText -> s.expression().STRING().getText)
        case s: PathContext =>
          path = cleanStr(s.getText)

        case s: TableNameContext =>
          tableName = s.getText
        case _ =>
      }
    }

    println(s"format: ${format} ,option: ${option} ,path: ${path} ,tableName: ${tableName}")

    "null"
  }

  def cleanStr(str: String) = {
    if (str.startsWith("`"))
      str.substring(1, str.length - 1)
    else str
  }
}
