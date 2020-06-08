import com.alibaba.druid.sql.SQLUtils
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement
import com.alibaba.druid.sql.repository.SchemaRepository
import com.alibaba.druid.util.JdbcConstants

import scala.collection.JavaConverters._
import scala.collection.mutable

/*
 * Created by respect on 07/05/2020
 * Email: wanp1989@126.com
 */

/**
  * create table c5_user(id varchar(255) ,name varchar(255))
  * create table c5_country(id varchar(255) ,country varchar(255))
  */

object DruidUtils {

  //streaming.core.datasource.impl.MLSQLDirectJDBC
  def extractTablesFromSQL(sql: String, dbType: String = JdbcConstants.MYSQL) = {
    val stmt = SQLUtils.parseSingleStatement(sql, dbType)
    val visitor = SQLUtils.createSchemaStatVisitor(dbType)
    stmt.accept(visitor)
    visitor.getTables().asScala.map { f =>
      val dbAndTable = f._1.getName
      if (dbAndTable.contains(".")) {
        val Array(db, table) = dbAndTable.split("\\.", 2)
        (db ,table)
      } else (dbAndTable, None)
    }.toList

  }

  //streaming.core.datasource.JDBCUtils
  def queryTableWithColumnsInDriver(options: Map[String, String] ,tableList: List[String]) = {
    val tableAndCols = mutable.HashMap.empty[String, mutable.HashMap[String ,String]]
    val driver = options("driver")
    val url = options("url")
    Class.forName(driver)
    val connection = java.sql.DriverManager.getConnection(url, options("user"), options("password"))
    try {
      val dbMetaData = connection.getMetaData()
      tableList.foreach(table => {
        val rs = dbMetaData.getColumns(null, null, table, "%")
        val value = tableAndCols.getOrElse(table, mutable.HashMap.empty[String ,String])

        while(rs.next()){
          value += (rs.getString("COLUMN_NAME") -> rs.getString("TYPE_NAME"))
        }

        tableAndCols.update(table, value)
        rs.close()
      })

    } finally {
      if (connection != null)
        connection.close()
    }
    tableAndCols
  }

  //streaming.core.datasource.JDBCUtils
  def tableColumnsToCreateSql(tableClos: mutable.HashMap[String, mutable.HashMap[String, String]]) = {
    val createSqlList = mutable.ArrayBuffer.empty[String]
    tableClos.foreach(table => {
      var createSql = "create table " + table._1 + " (" +
        table._2.map(m => m._1 + " " + m._2)
          .mkString(",") +
        " )"
      createSqlList += createSql
    })
    createSqlList.toList
  }

  //tech.mlsql.sql.MLSQLSQLParser
  def extractTableWithColumns(dbType :String ,sql :String ,createSchemaList :List[String]) = {
    val tableAndCols = mutable.HashMap.empty[String, mutable.HashSet[String]]

    val repository = new SchemaRepository(dbType)

    createSchemaList.foreach(repository.console(_))

    val stmtList = SQLUtils.parseStatements(sql, dbType)
    val stmt = stmtList.get(0).asInstanceOf[SQLSelectStatement]
    repository.resolve(stmt)

    val statVisitor = SQLUtils.createSchemaStatVisitor(dbType)
    stmt.accept(statVisitor)

    val iter = statVisitor.getColumns().iterator()

    while(iter.hasNext()){
      val c = iter.next()
      if(c.isSelect()){
        val value = tableAndCols.getOrElse(c.getTable, mutable.HashSet.empty[String])
        value.add(c.getName)
        tableAndCols.update(c.getTable, value)
      }
    }

    tableAndCols
  }


  def main(args: Array[String]): Unit = {
    val sql =
      """
        |select name ,country
        |  from c5_user a
        |  left join c5_country b
        |  on a.id = b.id
      """.stripMargin

    val params = Map("driver"->"com.mysql.jdbc.Driver" ,
      "url"->"jdbc:mysql://127.0.0.1:3306/test" ,
      "user"->"root" ,
      "password"->"mlsql")

    val rest = extractTablesFromSQL(sql)

    val tableList = extractTablesFromSQL(sql).map(_._1)

    val tableColsMap = queryTableWithColumnsInDriver(params, tableList)

    val createSqlList = tableColumnsToCreateSql(tableColsMap)
    println("创建SQL：")
    createSqlList.foreach(println(_))

    val tableAndCols = extractTableWithColumns(JdbcConstants.MYSQL, sql, createSqlList)

    println("表列信息：")
    println(tableAndCols)
    //Map(c5_country -> Set(country), c5_user -> Set(name))
  }
}