package methods

import api.{MethodAuth, MethodExt}
import org.apache.spark.sql.DataFrame

/*
 * Created by respect on 13/06/2020
 * Email: wanp1989@126.com
 */

class ShowExt extends MethodExt with MethodAuth{
  override def run(df: DataFrame, params: Map[String, String]): DataFrame = {
    df.sparkSession.sql(s"show tables from ${params.getOrElse("db" ,"default")}")
  }

  override def auth(params: Map[String, String]): Unit = {
    if (params.getOrElse("db" ,"default").equals("bdm")){
      throw new RuntimeException("你没有bdm库的show权限")
    }
  }
}
