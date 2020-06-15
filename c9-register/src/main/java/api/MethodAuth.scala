package api

import org.apache.spark.sql.DataFrame

/*
 * Created by respect on 15/06/2020
 * Email: wanp1989@126.com
 */

trait MethodAuth extends Serializable {
  def auth(params: Map[String, String])
}
