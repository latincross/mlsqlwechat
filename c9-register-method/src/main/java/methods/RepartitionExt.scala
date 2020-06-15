package methods

import api.MethodExt
import org.apache.spark.sql.DataFrame

/*
 * Created by respect on 13/06/2020
 * Email: wanp1989@126.com
 */

class RepartitionExt extends MethodExt {
  override def run(df: DataFrame, params: Map[String, String]): DataFrame = {
    df.repartition(params.getOrElse("partitionNum" ,"2").toInt)
  }
}