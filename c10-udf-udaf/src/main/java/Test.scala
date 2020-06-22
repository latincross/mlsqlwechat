import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.FunctionIdentifier
import org.apache.spark.sql.catalyst.analysis.FunctionRegistry.FunctionBuilder
import org.apache.spark.sql.catalyst.expressions.{Expression, ScalaUDF}
import org.apache.spark.sql.execution.aggregate.ScalaUDAF
import org.apache.spark.sql.expressions.UserDefinedFunction

/*
 * Created by respect on 15/06/2020
 * Email: wanp1989@126.com
 */

object Test {
  def main(args: Array[String]): Unit = {
    val warehouseLocation = "spark-warehouse"

    val spark = SparkSession
      .builder()
      .appName("etl_manager")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .master("local[*]")
      .getOrCreate()

    runUdf(spark)

    //runUdaf(spark)
  }

  def runUdf(spark: SparkSession): Unit ={
    val scalaScript =
      """
        |def apply(a:Double,b:Double)={
        |   a + b
        |}
      """.stripMargin

    val (className, src) = ScalaSourceUDF.wrapClass(scalaScript)
//    val clazz = ScriptCodeCompiler.compileScala(ScriptCodeCompiler.prepareScala(src, className))

    val (func, returnType) = ScalaSourceUDF(src, className, Some("apply"))

//    val newFunc = (e: Seq[Expression]) => ScalaUDF(func, returnType, e, Nil, Nil, None, true, true)
//
//    spark.sessionState.functionRegistry.registerFunction(FunctionIdentifier("wowPlus") ,newFunc)

//    println(func.asInstanceOf[Function2[Double, Double, Any]](1,2))

    spark.sessionState.udfRegistration.register("wowPlus" ,UserDefinedFunction(func ,returnType ,None))

    import spark.implicits._

    val testDf = Seq(1 ,2 ,3).toDF("a").createOrReplaceTempView("test")

    spark.sql("select wowPlus(a ,1) a from test").show(false)

    "11".toInt
  }

  def runUdaf(spark: SparkSession): Unit ={
    val scalaScript =
      """
        |import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
        |import org.apache.spark.sql.types._
        |import org.apache.spark.sql.Row
        |class SumAggregation extends UserDefinedAggregateFunction with Serializable{
        |    def inputSchema: StructType = new StructType().add("a", LongType)
        |    def bufferSchema: StructType =  new StructType().add("total", LongType)
        |    def dataType: DataType = LongType
        |    def deterministic: Boolean = true
        |    def initialize(buffer: MutableAggregationBuffer): Unit = {
        |      buffer.update(0, 0l)
        |    }
        |    def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
        |      val sum   = buffer.getLong(0)
        |      val newitem = input.getLong(0)
        |      buffer.update(0, sum + newitem)
        |    }
        |    def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
        |      buffer1.update(0, buffer1.getLong(0) + buffer2.getLong(0))
        |    }
        |    def evaluate(buffer: Row): Any = {
        |      buffer.getLong(0)
        |    }
        |}
      """.stripMargin

//    val newFunc = (e: Seq[Expression]) => ScalaUDAF(e, ScalaSourceUDAF(scalaScript, "SumAggregation"))
//
//    spark.sessionState.functionRegistry.registerFunction(FunctionIdentifier("wowSum") ,newFunc)

    spark.sessionState.udfRegistration.register("wowSum" ,ScalaSourceUDAF(scalaScript, "SumAggregation"))

    import spark.implicits._

    val testDf = Seq(1 ,2 ,3).toDF("a").createOrReplaceTempView("test")

    spark.sql("select wowSum(a) a from test").show(false)
  }
}
