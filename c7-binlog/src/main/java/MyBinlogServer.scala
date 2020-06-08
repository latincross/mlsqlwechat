import java.io.Serializable
import java.util
import java.util.Map.Entry
import com.github.shyiko.mysql.binlog.BinaryLogClient
import com.github.shyiko.mysql.binlog.event.EventType._
import com.github.shyiko.mysql.binlog.event._

/*
 * Created by respect on 24/05/2020
 * Email: wanp1989@126.com
 */

case class MySQLConnectionInfo(host: String, port: Int, userName: String, password: String
                               , binlogFileName: Option[String], recordPos: Option[Long])

object EventInfo {
  val INSERT_EVENT = "insert"
  val DELETE_EVENT = "delete"
  val UPDATE_EVENT = "update"
}

class MyBinlogServer {
  def connectMySQL(connect: MySQLConnectionInfo) = {
    val client = new BinaryLogClient(connect.host, connect.port, connect.userName, connect.password)

    client.setBinlogFilename(connect.binlogFileName.get)
    client.setBinlogPosition(connect.recordPos.get)


    client.registerEventListener(new BinaryLogClient.EventListener() {
      def onEvent(event: Event): Unit = {
        val header = event.getHeader[EventHeaderV4]()
        val eventType = header.getEventType

        eventType match {
          case TABLE_MAP => println(event.getData[TableMapEventData]())
          case EXT_WRITE_ROWS | PRE_GA_WRITE_ROWS | WRITE_ROWS =>
            printRecord(event, client.getBinlogFilename, EventInfo.INSERT_EVENT ,header)
          case EXT_UPDATE_ROWS | PRE_GA_UPDATE_ROWS | UPDATE_ROWS=>
            printRecord(event, client.getBinlogFilename, EventInfo.UPDATE_EVENT ,header)
          case EXT_DELETE_ROWS | PRE_GA_DELETE_ROWS | DELETE_ROWS =>
            printRecord(event, client.getBinlogFilename, EventInfo.DELETE_EVENT ,header)

//          case ROTATE =>
//            val rotateEventData = event.getData[RotateEventData]()
//            val currentBinlogFile = rotateEventData.getBinlogFilename
//            val currentBinlogPosition = rotateEventData.getBinlogPosition
//          case QUERY =>
//            println("qe: " + event.getData())
          case _ =>
        }
      }
    })

    client.connect()
  }

  def printRecord(event: Event, binLogFilename: String, eventType: String ,header: EventHeaderV4) = {
    eventType match {
//      case EventInfo.INSERT_EVENT => deserializer(event.getData[WriteRowsEventData].getRows.iterator() ,header)
//      case EventInfo.UPDATE_EVENT => deserializer2(event.getData[UpdateRowsEventData].getRows.iterator() ,header)
//      case EventInfo.DELETE_EVENT => deserializer(event.getData[DeleteRowsEventData].getRows.iterator() ,header)
      case _ => println("other: " + event.toString)
    }
  }

  def deserializer(itr : java.util.Iterator[Array[java.io.Serializable]] ,header: EventHeaderV4) = {
    while(itr.hasNext){
      val rest = itr.next().map(item => getWritableObject(null ,item))
      println(header.getEventType + s" ,newValue:" + rest.mkString(",") + ", pos:" + header.getPosition + ", next pos:" + header.getNextPosition)
    }
  }


  def deserializer2(itr: util.Iterator[Entry[Array[Serializable], Array[Serializable]]] ,header: EventHeaderV4) = {
    while(itr.hasNext){
      val next = itr.next()
      val key = next.getKey.map(item => getWritableObject(null ,item))
      val value = next.getValue.map(item => getWritableObject(null ,item))
      println(header.getEventType + s" ,oldValue: ${key.mkString(",")} ,newValue: ${value.mkString(",")} ,pos: ${header.getPosition} ,next pos: ${header.getNextPosition}")
    }
  }

  def getWritableObject(t: Integer, value: Serializable): Any = {
    if (value == null) return null
    if (t == null) if (value.isInstanceOf[Array[Byte]]) return new String(value.asInstanceOf[Array[Byte]])
    else if (value.isInstanceOf[Number]) return value
    else if (value.isInstanceOf[Number]) return value
    else if (value.isInstanceOf[Array[Byte]]) return new String(value.asInstanceOf[Array[Byte]])
    else return value.toString
    null
  }
}

object MyBinlogServer{
  def main(args: Array[String]) {
    val bls = new MyBinlogServer
    bls.connectMySQL(MySQLConnectionInfo("127.0.0.1" ,3306 ,"root" ,"mlsql" ,Option("master-bin.000069") ,Option(4)))
  }
}

