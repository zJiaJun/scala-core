package com.github.zjiajun.dataguru

import java.io.{File, PrintWriter}

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, AllForOneStrategy, OneForOneStrategy, Props, SupervisorStrategy}

/**
  * Created by zhujiajun
  * 16/5/28 21:31
  *
  * 继续改造上周的学生、教师管理系统，加入对各个actor异常的监控，并制定错误恢复策略。作业中必须说明选择这些策略的原因。
  */
object Week10 extends App {

  /*
  * 姓名 -> (年龄,标示,年级,分数)
  */
  val school = Map[String,(Int, String, Int, Int)](
    "Teacher_1" -> (30, "T", 0, 0),
    "Student_1" -> (18, "S", 3, 100)
  )

  case class CRUDSchool(
                         flag: String,
                         schoolMap: Map[String,(Int, String, Int, Int)],
                         key: String,
                         value: (Int, String, Int, Int))

  case class saveSchool(
                         schoolMap: Map[String,(Int, String, Int, Int)])


  val system = ActorSystem("watchSchoolSystem")
  val supervisorActor = system.actorOf(Props[SupervisorActor],"supervisorActor")
  supervisorActor ! CRUDSchool("C",school,"Student_2",(19,"S",3,1230))
  Thread sleep 3000
  println("Stop Actor System")
  system.terminate()

  class SupervisorActor extends Actor with ActorLogging {
    val saveFileActor = context.actorOf(Props(classOf[SaveFileActor],System.getProperty("user.home")),"saveFileActor")
    val crudActor = context.actorOf(Props(classOf[CRUDActor],saveFileActor),"crudActor")
    context.watch(crudActor)

    override def receive = {
      case msg : CRUDSchool => crudActor ! msg
    }

    override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
      case e: Exception => Restart
      case _ => Stop
    }
  }

  class CRUDActor(saveFileActor: ActorRef) extends Actor with ActorLogging {

    println("CRUDActor Constructor")
    var className: String = null

    @scala.throws[Exception](classOf[Exception])
    override def preStart(): Unit = {
      className = getClass.getSimpleName + File.separator
      println(className + "PreStart")
    }

    @scala.throws[Exception](classOf[Exception])
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      println(className + "PreRestart")
      println(getClass.getSimpleName + "Exception Msg : " + reason.getMessage)
      println(getClass.getSimpleName + "Msg : " + message)
      sender() ! message
      super.preRestart(reason, message)
    }

    @throws[Exception](classOf[Exception])
    override def postStop(): Unit = {
      println(className + "PostStop")
    }


    @scala.throws[Exception](classOf[Exception])
    override def postRestart(reason: Throwable): Unit = {
      super.postRestart(reason) //先调用preStart,初始化className
      println(className + "PostRestart")
    }

    override def receive = {
      case CRUDSchool(flag,schoolMap,key,value) =>
        saveFileActor ! saveSchool(
          flag match {
            case "C" =>
              log.info(s"Add map key : $key, value : $value")
              schoolMap + (key -> value)
            case "U" =>
              log.info(s"Update map key : $key, value : $value")
              schoolMap + (key -> value)
            case "D" =>
              log.info(s"Delete map key : $key, value : $value")
              schoolMap - key
          }
        )
    }
  }

  class SaveFileActor(filePath: String) extends Actor with ActorLogging {

    println("SaveFileActor Constructor")
    val file = new File(filePath + File.separator + "school.tmp")

    var className: String = null


    @scala.throws[Exception](classOf[Exception])
    override def preStart(): Unit = {
      className = getClass.getSimpleName + File.separator
      println(className + "PreStart")
    }

    @scala.throws[Exception](classOf[Exception])
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      println(className + "PreRestart")
      println(className + "Exception Msg : " + reason.getMessage)
      println(className + "Msg : " + message)
      println("重试")
      Thread sleep 5000
      self ! message //TODO has pro
      println("重试完成")
      super.preRestart(reason, message)
    }


    @throws[Exception](classOf[Exception])
    override def postStop(): Unit = {
      println(className + "PostStop")
    }


    @scala.throws[Exception](classOf[Exception])
    override def postRestart(reason: Throwable): Unit = {
      super.postRestart(reason)
      println(className + "PostRestart")
    }

    override def receive = {
      case saveSchool(m) =>
        if (!file.exists()) { //文件不存在,抛异常,休眠5S,创建文件
          throw new IllegalArgumentException("File path is wrong")
        }
        log.info(s"Save file to : ${file.getAbsolutePath}")
        val printWriter = new PrintWriter(file)
        printWriter.write(m.mkString("\n"))
        printWriter.close()
    }
  }
}
