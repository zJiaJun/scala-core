package com.github.zjiajun.dataguru

import java.util.concurrent.{Executors, TimeUnit}

import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * Created by zhujiajun
  * 16/5/15 10:26
  *
  * Scala中，可以通过一个Callable[E]来实现线程返回值。通常的做法，是
  * 创建一个带返回类型的函数，通过该函数创建一个Future
  *
  * 将上周的学校管理系统改写成一个服务端程序（不需要做RPC或者Restful接口，假设前面有这类封装），
  * 将对内存中保存的学生老师信息进行多线程处理（每个管理接口接受请求后，生成一个Future，进行多线程运行，
  * 可以用lock.synchronized对一个body: => Unit代码段进行多线程的保护
  */
class Week08(key: String, age: Int) extends Runnable {

  override def run(): Unit = updateTeacherAge(key,age)

  def updateTeacherAge(key: String,age: Int) = {
    val future = Future[collection.mutable.Map[String, (Int, String, Int, Int)]] {
      Week08.school
    }
    future onComplete {
      case Success(s) => {
        println(s"Begin update $key age : $age" )
        Week08.school.synchronized {
          Week08.school(key) = (age, Week08.t_flag, 0, 0)
        }
        println(s"Finish update $key age")
      }
      case Failure(t) => println("Some is wrong: " + t.getMessage)
    }
  }

}

object Week08 extends App {

  val t_flag = "T"
  val s_flag = "S"

  /*
    * 姓名 -> (年龄,标示,年级,分数)
    */
  val school = collection.mutable.Map[String,(Int, String, Int, Int)](
    "Teacher_1" -> (30, Week08.t_flag, 0, 0),
    "Teacher_2" -> (35, Week08.t_flag, 0 ,0),
    "Teacher_3" -> (40, Week08.t_flag, 0 ,0),
    "Student_1" -> (18, Week08.s_flag, 3, 100),
    "Student_2" -> (19, Week08.s_flag, 3, 90),
    "Student_3" -> (20, Week08.s_flag, 3, 80),
    "Student_4" -> (21, Week08.s_flag, 3, 70)
  )

  def apply(key: String, age: Int) = new Week08(key, age)

  val threadPool = Executors.newFixedThreadPool(3)
  for (i <- 1 to 3) {
    threadPool.execute(Week08("Teacher_" + i, i * 20))
  }
  threadPool.shutdown()
  threadPool.awaitTermination(1, TimeUnit.MINUTES)
  TimeUnit.SECONDS.sleep(1)
  println(school.filter(_._2._2 == t_flag))

}
