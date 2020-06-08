package aston

import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.spark.sql.execution.datasources.jdbc.{JDBCOptions, JDBCRDD}
import org.apache.spark.sql.jdbc.{JdbcDialect, JdbcDialects}
import org.apache.spark.sql.types.{DataType, MetadataBuilder, StringType, StructField, StructType}

/*
 * Created by respect on 30/05/2020
 * Email: wanp1989@126.com
 */

object JdbcTypeUtils {
  val UNSIGNED = """.*(unsigned)""".r
  def isSigned(typeName: String) ={
    typeName.trim match {
      case UNSIGNED(unsigned) => false
      case _ => true
    }
  }

  val FIXED_DECIMAL = """decimal\(\s*(\d+)\s*,\s*(\-?\d+)\s*\)""".r
  val FIXED_NUMERIC = """numeric\(\s*(\d+)\s*,\s*(\-?\d+)\s*\)""".r
  val FIXED_SCALE = """\w*\(\s*(\d+)\s*\)""".r

  private lazy val mysqlDialect = JdbcDialects.get("jdbc:mysql")

  def parsePrecisionScale(name: String) = {
    name match {
      case "decimal" | "numeric" => Array(38, 18)
      case FIXED_DECIMAL(precision, scale) => Array(precision.toInt, scale.toInt)
      case FIXED_NUMERIC(precision, scale) => Array(precision.toInt, scale.toInt)
      case FIXED_SCALE(scale) => Array(scale.toInt ,0)
      case _ => Array(0 ,0)
    }
  }

  def changeStructTypeToStringType(sf: StructType): StructType ={
    val fields = new Array[StructField](sf.fields.length)
    var i = 0
    sf.fieldNames.foreach(name => {
      fields(i) = StructField(name, StringType)
      i += 1
    })
    new StructType(fields)
  }

  def getMysqlStructType(sqlTypeObj :JSONObject ,mysqlTypeObj: JSONObject ,dialect: JdbcDialect = mysqlDialect) ={
    val fields = new Array[StructField](mysqlTypeObj.keySet().size())

    var i = 0
    mysqlTypeObj.keySet().toArray.map(_.toString).foreach(k => {
      val sqlType = sqlTypeObj.getInteger(k)
      val typeName = mysqlTypeObj.getString(k)
      val Array(precision, scale) = parsePrecisionScale(typeName)
      val singed = isSigned(typeName)

      val columnType = getCatalystType(sqlType ,typeName ,precision ,scale ,singed ,dialect)

      //此处不可获取是否字段可空，canal解析结果中不包含这个信息
      fields(i) = StructField(k, columnType)
      i += 1
    })

    new StructType(fields)
  }

  lazy val getCatalystTypePrivate = {
    import scala.reflect.runtime.{universe => ru}
    val classMirror = ru.runtimeMirror(getClass.getClassLoader)

    val JdbcUtils = classMirror.staticModule("org.apache.spark.sql.execution.datasources.jdbc.JdbcUtils")

    val methods = classMirror.reflectModule(JdbcUtils)

    val instanceMirror = classMirror.reflect(methods.instance)

    val method = methods.symbol.typeSignature.member(ru.TermName("getCatalystType")).asMethod

    instanceMirror.reflectMethod(method)
  }

  def getCatalystType(sqlType: Int,
                      typeName: String,
                      precision: Int,
                      scale: Int,
                      signed: Boolean,
                      dialect: JdbcDialect): DataType ={


    val metadata = new MetadataBuilder().putLong("scale", scale)

    val tn = if (typeName.contains("(")){
      typeName.substring(0 ,typeName.indexOf('('))
    }else {
      typeName
    }

    val columnType =
      dialect.getCatalystType(sqlType, tn, precision, metadata).getOrElse(
        getCatalystTypePrivate(sqlType, precision, scale, signed).asInstanceOf[DataType])

    columnType
  }

  Class.forName("com.mysql.jdbc.Driver")

  def loadSchemaInfo(connectionInfo: ConnectionInfo): StructType = {
    val parameters = Map(
      "url" -> s"jdbc:mysql://${connectionInfo.host}:${connectionInfo.port}",
      "user" -> connectionInfo.userName,
      "password" -> connectionInfo.password,
      "dbtable" -> s"${connectionInfo.databaseName}.${connectionInfo.tableName}"
    )
    val jdbcOptions = new JDBCOptions(parameters)
    val schema = JDBCRDD.resolveTable(jdbcOptions)
    schema
  }

  def main(args: Array[String]): Unit = {
    val json =
      """
        |{"data":[{"id":"7","name":"7","ts":"2020-05-25 18:04:12","uid":"7"}],"database":"rta","es":1590400862000,"id":2,"isDdl":false,"mysqlType":{"id":"bigint(20)","name":"varchar(255)","ts":"datetime","uid":"int(10) unsigned"},"old":null,"pkNames":null,"sql":"","sqlType":{"id":-5,"name":12,"ts":93,"uid":4},"table":"wow_test","ts":1590401580230,"type":"INSERT"}
      """.stripMargin

    val canalObj = JSON.parseObject(json)
    val sqlTypeObj = canalObj.getJSONObject("sqlType")
    val mysqlTypeObj = canalObj.getJSONObject("mysqlType")
    val schema = getMysqlStructType(sqlTypeObj ,mysqlTypeObj)

    println(schema)

    val conn = ConnectionInfo("127.0.0.1" ,3306 ,"root" ,"mlsql" ,"wow" ,"test")
    val schema1 = loadSchemaInfo(conn)

    println(schema1)

  }

}
case class ConnectionInfo(host: String, port: Int, userName: String, password: String,
                          databaseName: String ,tableName: String)