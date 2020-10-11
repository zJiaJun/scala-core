package com.github.zjiajun.impatient.charpter17

import java.time.LocalTime
import java.util.concurrent.CountDownLatch

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

/**
  *@author zhujiajun
  *@version 1.0
  *@since 2020/10/11 21:57
  */
object FutureExample extends App with LazyLogging {

  //默认全局线程池, 使用ForkJoinPool, 不推荐生产环境使用
  import scala.concurrent.ExecutionContext.Implicits.global
  def runInFuture(): Unit =
    Future {
      Thread sleep 3000
      logger.info(s"runInFuture, this is the future at ${LocalTime.now()}")
    }

  //多个future并发执行
  def multiFuture(): Unit = {
    Future { for (i <- 1 to 5) logger.info("A"); Thread sleep 10 }
    Future { for (i <- 1 to 5) logger.info("B"); Thread sleep 10 }
  }

  import scala.concurrent.duration._
  //等待future结果, 失去并发意义, 不推荐
  def waitResultFuture(): Unit = {
    val f = Future {
      Thread sleep 5000
      100
    }
    //如果分配的时间内任务没有完成, 抛出TimeoutException
    val result = Await.result(f, 5.seconds)
    logger.info(s"waitResultFuture, $result")
  }
  def waitReadFuture(): Unit = {
    val f = Future {
      Thread sleep 3000
//      1 / 0
      100
    }
    //任务没有完成返回NONE
    val none = f.value
    logger.info(s"waitReadFuture, $none")
    Await.ready(f, 3.seconds)
    val value = f.value
    value.get match {
      case Success(v) => logger.info(s"waitReadFuture, $v")
      case Failure(ex) => logger.info(s"waitReadFuture, $ex")
    }
  }

//  runInFuture()
//  multiFuture()
//  waitResultFuture()
  waitReadFuture()

  new CountDownLatch(1).await()
}
