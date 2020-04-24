import calculator.dsl.parser.{CalculatorBaseVisitor, CalculatorParser}

/*
 * Created by respect on 22/04/2020
 * Email: wanp1989@126.com
 */

class CalculatorVisitor extends CalculatorBaseVisitor[Integer]{

  override def visitAddSub(ctx: CalculatorParser.AddSubContext): Integer = {
    val left = visit(ctx.expr(0))
    val right = visit(ctx.expr(1))
    if (ctx.MINUS() != null) {
      left - right
    } else {
      left + right
    }
  }

  override def visitInt(ctx: CalculatorParser.IntContext): Integer = {
    Integer.valueOf(ctx.getText)
  }
}
