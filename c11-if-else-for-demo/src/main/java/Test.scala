import org.apache.spark.sql.SparkSession

/*
 * Created by respect on 13/10/2020
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

    val a = "jack,2"

    val Array(name ,num) = a.split(",")

    spark.sql("select 1.1 as a").createOrReplaceTempView("b1")
    spark.sql("select 1.2 as a").createOrReplaceTempView("b2")
    spark.sql("select 2   as a").createOrReplaceTempView("b3")

    if (name == "jack" && num == 3){
      if (2 == 1){
        spark.table("b1")
      }else{
        spark.table("b2")
      }
    }else{
      spark.table("b3").toDF()
    }.createOrReplaceTempView("b")

    spark.sql("select * from b").createOrReplaceTempView("output")

    spark.table("output").collect().foreach(println(_))

    spark.stop()
  }
}
