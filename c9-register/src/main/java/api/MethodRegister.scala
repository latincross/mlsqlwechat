package api

import scala.collection.JavaConverters._
/*
 * Created by respect on 13/06/2020
 * Email: wanp1989@126.com
 */

//tech.mlsql.ets.register.ETRegister

object MethodRegister{
  private val mapping = new java.util.concurrent.ConcurrentHashMap[String, String]()

  def wow(name: String) = mapping.put(name, ("methods." + name))

  def register(name: String, value: String) = mapping.put(name, value)

  def unRegister(name: String) = mapping.remove(name)

  def getMapping = {
    mapping.asScala
  }

  wow("ShowExt")
}
