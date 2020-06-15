package api

import org.apache.spark.sql.DataFrame

/*
 * Created by respect on 15/06/2020
 * Email: wanp1989@126.com
 */

trait MethodExt extends Serializable {
  def run(df :DataFrame ,params: Map[String, String]) :DataFrame
}
