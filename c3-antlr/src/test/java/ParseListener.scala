import test.dsl.parser.DSLSQLBaseListener
import test.dsl.parser.DSLSQLParser.{BooleanExpressionContext, ExpressionContext, FormatContext, PathContext, SqlContext, TableNameContext}

/*
 * Created by respect on 21/04/2020
 * Email: wanp1989@126.com
 */

class ParseListener extends DSLSQLBaseListener{
  override def exitSql(ctx: SqlContext): Unit = {
    ctx.getChild(0).getText.toLowerCase() match {
      case "load" =>
        parse(ctx)
      case _ =>
        println("todo")
    }
  }

  def parse(ctx: SqlContext): Unit = {
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
  }

  def cleanStr(str: String) = {
    if (str.startsWith("`"))
      str.substring(1, str.length - 1)
    else str
  }
}
