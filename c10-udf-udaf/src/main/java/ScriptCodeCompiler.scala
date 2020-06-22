
/*
 * Created by respect on 15/06/2020
 * Email: wanp1989@126.com
 */

object ScriptCodeCompiler {
  def newInstance(clazz: Class[_]): Any = {
    val constructor = clazz.getDeclaredConstructors.head
    constructor.setAccessible(true)
    constructor.newInstance()
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
}
