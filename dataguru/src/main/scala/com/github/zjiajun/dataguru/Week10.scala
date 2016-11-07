package com.github.zjiajun.dataguru

import java.io.{File, PrintWriter}
import java.util.concurrent.CountDownLatch

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

  case class SaveSchool(
                         schoolMap: Map[String,(Int, String, Int, Int)])


  val system = ActorSystem("watchSchoolSystem")
  val supervisorActor = system.actorOf(Props[SupervisorActor],"supervisorActor")
  supervisorActor ! CRUDSchool("C",school,"Student_2",(19,"S",3,88))
  val latch = new CountDownLatch(1)
  latch.await() //等待保存磁盘成功,否则一只等待
  println("Stop Actor System")
  system.terminate()

  class SupervisorActor extends Actor with ActorLogging {
    val saveFileActor = context.actorOf(Props(classOf[SaveFileActor],System.getProperty("user.home")),"saveFileActor")
    val crudActor = context.actorOf(Props(classOf[CRUDActor],saveFileActor),"crudActor")
    context.watch(crudActor)

    override def receive = {
      case msg : CRUDSchool => crudActor ! msg
    }

    /**
      * 在这个例子中使用Restart重建actor策略
      * 原因: 当修改完老师或学生信息后,可能文件在磁盘中不存在或者磁盘满了
      *       等手动工创建文件或者清理磁盘后,重试操作便可以成功
      */
    override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
      case e: Exception => Restart
      case _ => Stop
    }
  }

  class CRUDActor(saveFileActor: ActorRef) extends Actor with ActorLogging {

    log.info("CRUDActor Constructor")
    var className: String = null

    @scala.throws[Exception](classOf[Exception])
    override def preStart(): Unit = {
      className = getClass.getSimpleName + File.separator
      log.info(className + "PreStart")
    }

    @scala.throws[Exception](classOf[Exception])
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      log.info(className + "PreRestart")
      log.info(getClass.getSimpleName + "Exception Msg : " + reason.getMessage)
      log.info(getClass.getSimpleName + "Msg : " + message)
      super.preRestart(reason, message)
    }

    @throws[Exception](classOf[Exception])
    override def postStop(): Unit = {
      log.info(className + "PostStop")
    }


    @scala.throws[Exception](classOf[Exception])
    override def postRestart(reason: Throwable): Unit = {
      super.postRestart(reason) //先调用preStart,初始化className
      log.info(className + "PostRestart")
    }

    override def receive = {
      case CRUDSchool(flag,schoolMap,key,value) =>
        saveFileActor ! SaveSchool(
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

    log.info("SaveFileActor Constructor")
    val file = new File(filePath + File.separator + "school.tmp")
    var className: String = null


    @scala.throws[Exception](classOf[Exception])
    override def preStart(): Unit = {
      className = getClass.getSimpleName + File.separator
      log.info(className + "PreStart")
    }

    /**
      * 如果调用super.preRestart(),会停止监控并停止子actor,然后调用postStop
      * 接着执行构造器,然后执行postRestart
      *
      * @param reason Exception
      * @param message actor message
      */
    @scala.throws[Exception](classOf[Exception])
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      log.info(className + "PreRestart")
      log.info(className + "Exception Msg : " + reason.getMessage)
      log.info(className + "Msg : " + message)
      message match {
        case Some(msg) => self ! msg.asInstanceOf[SaveSchool] //注意这里需要转类型,被坑了好久
        case None      => log.info(className + "PreRestart message is None")
      }
      Thread sleep 5000
      super.preRestart(reason, message)
    }


    @throws[Exception](classOf[Exception])
    override def postStop(): Unit = {
      log.info(className + "PostStop")
    }

    /**
      * 如果调用super.postRestart(),会先执行preStart
      * 如果不需要恢复actor状态,可以不调用
 *
      * @param reason Exception
      */
    @scala.throws[Exception](classOf[Exception])
    override def postRestart(reason: Throwable): Unit = {
      super.postRestart(reason)
      log.info(className + "PostRestart")
    }

    override def receive = {
      case SaveSchool(m) =>
        log.info(this.toString)
        if (!file.exists()) { //文件不存在,抛异常,休眠5S,手动创建文件后,下次执行成功
          throw new IllegalArgumentException("File path is wrong")
        }
        log.info(s"Save file to : ${file.getAbsolutePath}")
        val printWriter = new PrintWriter(file)
        printWriter.write(m.mkString("\n"))
        printWriter.close()
        latch.countDown()
    }
  }
}
