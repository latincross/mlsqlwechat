import aston.{ConnectionInfo, JdbcTypeUtils}
import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import org.apache.spark.sql.catalyst.expressions.JsonToStructs
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Column, Row, SparkSession, functions => F}

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

/*
 * Created by respect on 01/06/2020
 * Email: wanp1989@126.com
 */

object SparkCanalTest {
  val canal =
    """
      |{
      |    "data":[
      |        {
      |            "id":"1",
      |            "name":"0",
      |            "ts":"2020-05-15 11:45:55"
      |        },
      |        {
      |            "id":"2",
      |            "name":"0",
      |            "ts":"2020-05-15 15:09:58"
      |        }
      |    ],
      |    "database":"wow",
      |    "es":1589526850000,
      |    "id":54,
      |    "isDdl":false,
      |    "mysqlType":{
      |        "id":"bigint(20)",
      |        "name":"varchar(255)",
      |        "ts":"datetime"
      |    },
      |    "old":[
      |        {
      |            "name":"1"
      |        },
      |        {
      |            "name":"2"
      |        }
      |    ],
      |    "pkNames":null,
      |    "sql":"",
      |    "sqlType":{
      |        "id":-5,
      |        "name":12,
      |        "ts":93
      |    },
      |    "table":"test",
      |    "ts":1589527037159,
      |    "type":"UPDATE"
      |}
    """.stripMargin

  val canal1 =
  """
    |{
    |    "data":[
    |        {
    |            "id1":"1",
    |            "name1":"0",
    |        },
    |        {
    |            "id1":"1",
    |            "name1":"2",
    |        }
    |    ],
    |    "database":"wow1",
    |    "es":1589526850000,
    |    "id":54,
    |    "isDdl":false,
    |    "mysqlType":{
    |        "id1":"bigint(20)",
    |        "name1":"varchar(255)"
    |    },
    |    "old":[
    |        {
    |            "name1":"1"
    |        },
    |        {
    |            "name1":"2"
    |        }
    |    ],
    |    "pkNames":null,
    |    "sql":"",
    |    "sqlType":{
    |        "id1":-5,
    |        "name1":12
    |    },
    |    "table":"test1",
    |    "ts":1589527037159,
    |    "type":"UPDATE"
    |}
  """.stripMargin

  def main(args: Array[String]): Unit = {
    val warehouseLocation = "spark-warehouse"

    val spark = SparkSession
      .builder()
      .appName("etl_manager")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val tableKeyMap = Map("wow\001test" -> "id" ,"wow1\001test1" -> "id1")

//    val connInfoMap = Map("wow\001test" -> ConnectionInfo("127.0.0.1" ,3306 ,"root" ,"mlsql" ,"wow" ,"test")
//      ,"wow1\001test1" -> ConnectionInfo("127.0.0.1" ,3306 ,"root" ,"mlsql" ,"wow" ,"test"))

    val canalDs = spark.createDataset[String](Array(canal ,canal1)).as("data")

    val dataSet = canalDs.rdd.flatMap { line =>
      val canalObj = JSON.parseObject(line)
      val rows = canalObj.getJSONArray("data")
      val operator = canalObj.getString("type").toLowerCase
      val db = canalObj.getString("database")
      val tb = canalObj.getString("table")
      val ts = canalObj.getLong("ts")
      val sqlTypeObj = canalObj.getJSONObject("sqlType")
      val mysqlTypeObj = canalObj.getJSONObject("mysqlType")

      val id = tableKeyMap.get(s"${db}\001${tb}").get

      rows.asInstanceOf[JSONArray].asScala.map(r => {
        val row = r.asInstanceOf[JSONObject]
        val key = s"${db}\001${tb}\001${row.getString(id)}"

        RecordInfo(key, operator, ts, db, tb, sqlTypeObj, mysqlTypeObj, row)
      })
    }.groupBy(_.key)
      .map(records => {
      val items = records._2.toSeq.sortBy(_.ts)
      items.last
    })

    //连接MySQL查询Schema，此方式简单，但是需要连接MySql
//    dataSet.collect().foreach(println(_))
//    val schemaSet = dataSet.map(record => {
//      DbInfo(record.db, record.tb)
//    }).distinct()
//      .collect()
//      .map(di =>{
//      //转换mysql schema比较昂贵，去重之后转，代价小些
//      val schema = JdbcTypeUtils.loadSchemaInfo(connInfoMap.get(s"${di.db}\001${di.tb}").get)
//      SchemaInfo(di.db, di.tb, schema)
//    })

    //Json方式处理，不建议这么做
//   val schemaSet.foreach { case (table, index) => {
//        val tmpRDD = dataSet.filter(record => {
//          record.db == table.db && record.tb == table.tb
//        }).map(_.row.toJSONString)
//
//        val columns = table.schema.fields.map(sf => {
//          sf.dataType match {
//            case StringType => {
//              F.col(sf.name)
//            }
//            case _ => F.col(sf.name).cast(sf.dataType).as(sf.name)
//          }
//        })
//
//        val stringTypeSchema = JdbcTypeUtils.changeStructTypeToStringType(table.schema)
//
//        val df = spark.createDataset[String](tmpRDD).toDF("value")
//          .select(new Column(JsonToStructs(stringTypeSchema ,Map() ,F.col("value").expr)).as("data"))
//          .select("data.*")
//          .select(columns: _*)
//
//        df.collect().foreach(data => println(s"${table.db}.${table.tb}:" + data))
//      }
//    }

    val schemaSet = dataSet.map(record => {
      MysqlSchemaInfo(record.db, record.tb ,record.sqlType ,record.mysqlType)
    }).distinct()
      .groupBy(msi => DbInfo(msi.db ,msi.tb))
      .map(record => {
        val typeInfo = record._2.foldLeft((new JSONObject(),new JSONObject()))((jot ,msi)
        => (jot._1.fluentPutAll(msi.sqlType) ,jot._2.fluentPutAll(msi.mysqlType)))

        MysqlSchemaInfo(record._1.db ,record._1.tb ,typeInfo._1 ,typeInfo._2)
      })
      .map(msi =>{
        //为了避免不必要的mysql schema转换，去重之后转换
        val schema = JdbcTypeUtils.getMysqlStructType(msi.sqlType, msi.mysqlType)
        SchemaInfo(msi.db, msi.tb, schema)
      }).collect()

    schemaSet.foreach { table => {
      val tmpRDD = dataSet.filter(record => {
        record.db == table.db && record.tb == table.tb
      }).map(record => {
        val array = table.schema.fieldNames.map(record.row.getString(_))
        Row.fromSeq(array)
      })

      val stringTypeSchema = JdbcTypeUtils.changeStructTypeToStringType(table.schema)

      val columns = table.schema.fields.map(sf => {
        sf.dataType match {
          case StringType => {
            F.col(sf.name)
          }
          case _ => F.col(sf.name).cast(sf.dataType).as(sf.name)
        }
      })

      val df = spark.createDataFrame(tmpRDD ,stringTypeSchema)
        .select(columns: _*)

      df.collect().foreach(data => println(s"${table.db}.${table.tb}:" + data))
    }
    }
  }
}

case class RecordInfo(key: String ,operator: String ,ts: Long ,db:String ,tb: String
                      ,sqlType: JSONObject ,mysqlType: JSONObject ,row: JSONObject)

case class SchemaInfo(db: String ,tb: String ,schema: StructType)

case class MysqlSchemaInfo(db: String ,tb: String ,sqlType: JSONObject ,mysqlType: JSONObject)

case class DbInfo(db: String ,tb: String)

















