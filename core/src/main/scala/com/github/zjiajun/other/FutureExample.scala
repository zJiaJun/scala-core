package com.github.zjiajun.other

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * @author zhujiajun
  * @since 2017/7/16
  */
object FutureExample extends App {

//  import scala.concurrent.ExecutionContext.Implicits.global

   val future: Future[String] = Future {
    println("begin future")
    Thread.sleep(2)
    println("end future")
    "return future value"
  }
  println(future.isCompleted)

  future.onComplete{
    case Success(v) => println(v)
    case Failure(e) => println(e)
  }


}
