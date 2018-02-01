package com.github.zjiajun.other

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * @author zhujiajun
  * @since 2017/7/16
  */
object FutureExample extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

   val future: Future[String] = Future {
    println("begin future")
    Thread.sleep(1000)
    println("end future")
    "return future value"
  }

  println(future.isCompleted)

  future.onComplete {
    case Success(v) => println(v)
    case Failure(e) => println(e)
  }

  val f1: Future[Int] = Future { println("f1"); Thread sleep 2000; 1}
  val f2: Future[Int] = Future { println("f2"); Thread sleep 2000; 2}

  val s: Future[String] = for { v1 <- f1
        v2 <- f2 } yield s"result $v1 + $v2"

  println(s)
  Thread sleep 3000
  println(s.value)


  val listFuture: List[Future[Int]] = List(1,2,3).map(x => Future { x })
  println(listFuture)

  val futureList: Future[List[Int]] = Future.sequence(listFuture)
  println(futureList)
  futureList.onComplete({
    case Success(s) => println(s)
  })


}
