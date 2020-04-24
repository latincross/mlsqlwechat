import calculator.dsl.parser.{CalculatorLexer, CalculatorParser}
import org.antlr.v4.runtime.{ANTLRInputStream, CharStreams, CommonTokenStream}
import org.antlr.v4.runtime.tree.ParseTreeWalker
import test.dsl.parser.{DSLSQLLexer, DSLSQLParser}

object Test{
  def main(args: Array[String]): Unit = {
    listenerTest()
    visitorTest()
  }

  def listenerTest(): Unit ={
    val script =
      """
        |load csv.`/tmp/test` options
        |  header="true"
        |as test;
      """.stripMargin

    val input = CharStreams.fromString(script)
    val lexer = new DSLSQLLexer(input)
    val tokens = new CommonTokenStream(lexer)
    val parser = new DSLSQLParser(tokens)
    val tree = parser.sql()
    val listener = new ParseListener()
    ParseTreeWalker.DEFAULT.walk(listener ,tree)

    val visitor = new ParseVisitor()
    visitor.visit(tree)
  }

  def visitorTest(): Unit ={
    val expr = "1+5-3"
    val input = CharStreams.fromString(expr)
    val lexer = new CalculatorLexer(input)
    val tokens = new CommonTokenStream(lexer)
    val parser = new CalculatorParser(tokens)
    val visitor = new CalculatorVisitor()

    println("expr 1+5: " + visitor.visit(parser.expr().children.get(0)))
  }
}