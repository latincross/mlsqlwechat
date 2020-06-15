import java.io.File
import java.net.{URL, URLClassLoader}

import api.{MethodAuth, MethodExt, MethodRegister}
import org.apache.spark.sql.SparkSession

/*
 * Created by respect on 13/06/2020
 * Email: wanp1989@126.com
 */

//tech.mlsql.dsl.adaptor.TrainAdaptor

object CallMethod {
  def main(args: Array[String]): Unit = {
    val warehouseLocation = "spark-warehouse"

    val spark = SparkSession
      .builder()
      .appName("etl_manager")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .master("local[*]")
      .getOrCreate()

    testRunShowExt(spark)
    testRunShowExtAuth(spark)
    testRunRepartitionExt(spark)

  }

  def findMethod(name: String): MethodExt ={
    MethodRegister.getMapping.get(name.capitalize) match {
      case Some(clzz) =>
        Class.forName(clzz).newInstance().asInstanceOf[MethodExt]
      case None =>
        throw new RuntimeException(s"${name} is not found")
    }
  }

  def loadJarInDriver(path: String) = {
    val systemClassLoader = Thread.currentThread().getContextClassLoader.asInstanceOf[URLClassLoader]
    val method = classOf[URLClassLoader].getDeclaredMethod("addURL", classOf[URL])
    method.setAccessible(true)
    method.invoke(systemClassLoader, new File(path).toURI.toURL)
  }

  def testRunShowExt(spark: SparkSession): Unit ={
    val testTable = spark.sql("select 1 a").createOrReplaceTempView("test")

    val commandDf = spark.sql("select 'command' command")

    val methodExt = findMethod("showExt")

    val df = methodExt.run(commandDf ,Map("db" -> "default"))

    df.show(false)
  }

  def testRunShowExtAuth(spark: SparkSession): Unit ={
    val commandDf = spark.sql("select 'command' command")

    val methodExt = findMethod("showExt")

    if (methodExt.isInstanceOf[MethodAuth]){
      methodExt.asInstanceOf[MethodAuth].auth(Map("db" -> "bdm"))
    }
  }

  def testRunRepartitionExt(spark: SparkSession): Unit ={
    import spark.implicits._

    loadJarInDriver("c9-register/jars/c9-register-method-1.0-SNAPSHOT.jar")

    MethodRegister.register("Repartition" ,"methods.RepartitionExt")

    val testDf = Seq("1" ,"1" ,"1" ,"2" ,"2").toDF("a")

    val df = findMethod("repartition").run(testDf ,Map("partitionNum" -> "3"))

    println("Partition nums: " + df.rdd.partitions.size)
  }
}
