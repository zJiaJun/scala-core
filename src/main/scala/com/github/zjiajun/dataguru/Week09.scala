package com.github.zjiajun.dataguru

import java.io.{PrintWriter, File}

import akka.actor._

/**
  * Created by zhujiajun
  * 16/5/21 14:49
  */
object Week09 extends App {

  /*
   * 姓名 -> (年龄,标示,年级,分数)
   */
  val school = collection.mutable.Map[String,(Int, String, Int, Int)](
    "Teacher_1" -> (30, "T", 0, 0),
    "Teacher_2" -> (35, "T", 0 ,0),
    "Teacher_3" -> (40, "T", 0 ,0),
    "Student_1" -> (18, "S", 3, 100),
    "Student_2" -> (19, "S", 3, 90),
    "Student_3" -> (20, "S", 3, 80)
  )

  case class CRUDSchool(
       flag: String,
       schoolMap: collection.mutable.Map[String,(Int, String, Int, Int)],
       key: String,
       value: (Int, String, Int, Int))

  case class saveSchool(
         schoolMap: collection.mutable.Map[String,(Int, String, Int, Int)])

  val system = ActorSystem("school")
  val saveFileActor = system.actorOf(Props(classOf[SaveFileActor],System.getProperty("user.home")),"saveFileActor")
  val crudActor = system.actorOf(Props(classOf[CRUDActor],saveFileActor),"crudActor")
  crudActor ! CRUDSchool("D",school,"Teacher_1",null)
  Thread sleep 2000
  system.terminate()


  class CRUDActor(saveFileActor: ActorRef) extends Actor with ActorLogging {

    override def receive = {

      case CRUDSchool(flag,schoolMap,key,value) => flag match {
        case "C" =>
          log.info(s"Add map key : $key, value : $value")
          schoolMap += (key -> value)
        case "U" =>
          log.info(s"Update map key : $key, value : $value")
          schoolMap(key) = value
        case "D" =>
          log.info(s"Delete map key : $key, value : $value")
          schoolMap -= key
      }
        saveFileActor ! saveSchool(schoolMap)
    }
  }

  class SaveFileActor(filePath: String) extends Actor with ActorLogging {

    val file = new File(filePath + File.separator + "school.tmp")
    val printWriter = new PrintWriter(file)

    override def receive = {

      case saveSchool(m) =>
        log.info(s"Save file to : ${file.getAbsolutePath}")
        printWriter.write(m.mkString("\n"))
    }

    @throws[Exception](classOf[Exception])
    override def postStop(): Unit = printWriter.close()
  }

}
