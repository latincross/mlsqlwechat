import java.util

import org.apache.spark.TaskContext
import org.apache.spark.sql.{SparkSession, SparkUtils}
import org.apache.spark.sql.catalyst.encoders.RowEncoder
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import tech.mlsql.arrow.python.ispark.SparkContextImp
import tech.mlsql.arrow.python.runner.{ArrowPythonRunner, ChainedPythonFunctions, PythonConf, PythonFunction}
import tech.mlsql.common.utils.lang.sc.ScalaMethodMacros.str
import scala.collection.JavaConverters._

object MlsqlPython{
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .getOrCreate()
    import spark.implicits._

    val data = Seq(("http://docs.mlsql.tech" ,1),
      ("https://github.com/allwefantasy/pyjava" ,3),
      ("https://github.com/allwefantasy/pyjava" ,2),
      ("http://docs.mlsql.tech" ,2),
      ("http://docs.mlsql.tech" ,1),
      ("https://github.com/latincross/mlsqlwechat" ,3)
    ).toDF("url" ,"uid")

    data.createOrReplaceTempView("test")

    val df = spark.sql("select * from test distribute by url sort by uid")

    df.rdd.foreachPartition(it => {
      it.foreach(r=>println("partition id: " + TaskContext.get.partitionId + " ,row: " + r.toString()))
    })

    val struct = df.schema
    val timezoneid = spark.sessionState.conf.sessionLocalTimeZone
    val rest = df.rdd.mapPartitions { iter =>
      val enconder = RowEncoder.apply(struct).resolveAndBind()
      val envs = new util.HashMap[String, String]()
      envs.put(str(PythonConf.PYTHON_ENV), "source activate respect")
      //envs.put(str(PythonConf.PYTHON_ENV), ":") //使用本地python环境
      val batch = new ArrowPythonRunner(
        Seq(ChainedPythonFunctions(Seq(PythonFunction(
          """
            |#import os
            |#os.environ["MLSQL_DEV"]="1"
            |def process(_data_manager):
            |  pv = 0
            |  uv = 0
            |  lastUrl = None
            |  lastUid = None
            |  output = False
            |
            |  for item in _data_manager.fetch_once_as_rows():
            |    urlCol = item["url"]
            |    uidCol = item["uid"]
            |
            |    if lastUrl is None:
            |        pv = 1
            |        uv = 1
            |        lastUrl = urlCol
            |        lastUid = uidCol
            |    elif lastUrl == urlCol:
            |        pv = pv + 1
            |        if lastUid != uidCol:
            |          uv = uv + 1
            |          lastUid = uidCol
            |    else:
            |        #lastUrl变了，则输出
            |        output = True
            |        yield {"url":lastUrl,"pv":pv,"uv":uv}
            |
            |        #重置pv，uv，output
            |        pv = 1
            |        uv = 1
            |        lastUrl = urlCol
            |        lastUid = uidCol
            |        output = False
            |  #当只有一个url情况下，lastUrl为空表示_data_manager没有数据，不需要输出
            |  if not output and lastUrl is not None:
            |      yield {"url":lastUrl,"pv":pv ,"uv":uv}
            |data_manager.log_client.log_to_driver("come from worker")
            |items=process(data_manager)
            |data_manager.build_result(items, 1024)
          """.stripMargin, envs, "python", "3.6")))), struct,
        timezoneid, Map()
      )
      val newIter = iter.map { irow =>
        enconder.toRow(irow)
      }
      val commonTaskContext = new SparkContextImp(TaskContext.get(), batch)
      val columnarBatchIter = batch.compute(Iterator(newIter), TaskContext.getPartitionId(), commonTaskContext)
      columnarBatchIter.flatMap { batch =>
        batch.rowIterator.asScala.map(_.copy())
      }
    }

    //abc.collect().foreach(println(_))

    val a = rest.filter(_.toString != "[]")

    val wow = SparkUtils.internalCreateDataFrame(spark, a,
      StructType(Seq(StructField("url", StringType),StructField("pv", LongType),StructField("uv",LongType))), false)

    wow.collect().foreach(println(_))
  }

}