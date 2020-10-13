import java.util.UUID

import org.apache.spark.sql.DataFrame

object DynamicCompile{
  def train(df: DataFrame, path: String, params: Map[String, String]): DataFrame = {

    def wrapClass(code: String) = {
      val className = s"ET_${UUID.randomUUID().toString.replaceAll("-", "")}"
      val newfun =
        s"""
           |import org.apache.spark.sql.DataFrame
           |class  ${className}{
           |  def train(__df: DataFrame, path: String, params: Map[String, String]): DataFrame = {
           |    def getView(viewName: String) = {
           |      if(__df.sparkSession.catalog.tableExists(viewName)){
           |        __df.sparkSession.table(viewName)
           |      }else{
           |        throw new RuntimeException(s"$${viewName} is not a view!")
           |      }
           |    }
           |    import __df.sparkSession.implicits._
           |    ${code}
           |  }
           |}
            """.stripMargin
      (className, newfun)
    }

    def compileScala(src: String): Class[_] = {
      import scala.reflect.runtime.universe
      import scala.tools.reflect.ToolBox
      val classLoader = scala.reflect.runtime.universe.getClass.getClassLoader
      val tb = universe.runtimeMirror(classLoader).mkToolBox()
      val tree = tb.parse(src)
      val clazz = tb.compile(tree).apply().asInstanceOf[Class[_]]
      clazz
    }

    def prepareScala(src: String, className: String): String = {
      src + "\n" + s"scala.reflect.classTag[$className].runtimeClass"
    }

    def getMethod(clazz: Class[_], method: String) = {
      val candidate = clazz.getDeclaredMethods.filter(_.getName == method).filterNot(_.isBridge)
      if (candidate.isEmpty) {
        throw new Exception(s"No method $method found in class ${clazz.getCanonicalName}")
      } else if (candidate.length > 1) {
        throw new Exception(s"Multiple method $method found in class ${clazz.getCanonicalName}")
      } else {
        candidate.head
      }
    }

    val code = params("code")

    if (code.contains(".sparkSession") || code.contains(".sqlContext") || code.contains(".sparkContext")){
      throw new RuntimeException("Code cannot constains .sparkSession or .sqlContext or .sparkContext")
    }


    val (className, src) = wrapClass(code)
    val clazz = compileScala(prepareScala(src, className))


    getMethod(clazz ,"train").invoke(clazz.newInstance() ,df ,path ,params)
      .asInstanceOf[DataFrame]
  }
}