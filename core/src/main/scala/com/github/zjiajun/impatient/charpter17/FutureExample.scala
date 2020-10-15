package com.github.zjiajun.impatient.charpter17

import java.time.LocalTime
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{ArrayBlockingQueue, CountDownLatch, ThreadFactory, ThreadPoolExecutor, TimeUnit}

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.math.random
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
  //阻塞等待future结果, 失去并发意义, 不推荐.
  def waitResultFuture(): Unit = {
    val f = Future {
      Thread sleep 5000
      100
    }
    //如果分配的时间内任务没有完成, 抛出TimeoutException
    val result = Await.result(f, 5.seconds)
    logger.info(s"waitResultFuture, $result")
  }

  //Await.result方法中,如果任务抛出异常, result调用会抛出异常,使用ready方法避免.同样不推荐
  def waitReadyFuture(): Unit = {
    val f = Future {
      Thread sleep 2000
//      1 / 0
      100
    }
    //任务没有完成返回NONE
    val none = f.value
    logger.info(s"waitReadFuture, $none")
    Await.ready(f, 3.seconds)
    val value = f.value
    value.get match {
      case Success(v)  => logger.info(s"waitReadFuture, $v")
      case Failure(ex) => logger.info(s"waitReadFuture, $ex")
    }
  }

  //使用回调通知
  def callbackFuture(): Unit = {
    val f = Future {
      Thread sleep 2000
      if (random() < 0.5) throw new Exception("exception msg") else 20
    }
    f.onComplete {
      case Success(v)  => logger.info(s"callBackInFuture success, $v")
      case Failure(ex) => logger.info(s"callBackInFuture failure, ${ex.getMessage}")
    }
  }

  //嵌套回调,回调地狱,不推荐
  def callbackHellFuture(): Unit = {
    val f1 = Future { 10 }
    val f2 = Future { 20 }
    f1.onComplete {
      case Success(v1) =>
        f2.onComplete {
          case Success(v2) =>
            val v = v1 + v2
            logger.info(s"callbackHellFuture, $v")
          case Failure(v2ex) =>
        }
      case Failure(v1ex) =>
    }
  }

  //组合future任务
  def combinedFuture(): Unit = {
    val f1 = Future { Thread sleep 30; 100 }
    val f2 = Future { Thread sleep 20; 200 }
    val combined_1 = f1.flatMap(v1 => f2.map(v2 => v1 + v2))
    logger.info(s"combinedFuture_1, $combined_1")

    val combined_2 = for (v1 <- f1; v2 <- f2) yield v1 + v2
    logger.info(s"combinedFuture_2, $combined_1")
  }

  //某个任务安排在另外一个任务之后运行,使用def, 而不是val
  def lazyFuture(): Unit = {
    val f1 = Future { Thread sleep 2000; 25 }
    //f2只会在f1完成之后计算求值
    def f2 = Future { Thread sleep 1000; 25 }
    val lazyCombined_1 = for (v1 <- f1; v2 <- f2) yield v1 + v2
    logger.info(s"lazyFuture_1, $lazyCombined_1")
    lazyCombined_1.onComplete {
      case Success(v)  => logger.info(s"lazyFuture_1, $v")
      case Failure(ex) => logger.info(s"lazyFuture_1, ${ex.getMessage}")
    }

    //第二个future依赖第一个结果
    val f3 = Future { 20 }
    def f4(arg: Int) = Future { logger.info(s"second future run, first result is $arg"); arg + 5 }
    val lazyCombined_2 = for (v3 <- f3; v4 <- f4(v3)) yield v3 + v4
    logger.info(s"lazyFuture_2, $lazyCombined_2")
    lazyCombined_2.onComplete {
      case Success(v)  => logger.info(s"lazyFuture_2, $v")
      case Failure(ex) => logger.info(s"lazyFuture_2, ${ex.getMessage}")
    }
  }

  //将异常转换结果
  def recoverFuture(): Unit = {
    val f = Future { Thread sleep 2000; 1 / 0 }
    val f1 = f.recover {
      case e: ArithmeticException => logger.info(s"recoverFuture exception, ${e.getMessage}"); 0
    }
    f1.foreach(v => logger.info(s"recoverFuture, $v"))

    val f2 = f.recoverWith {
      case e: ArithmeticException => logger.info(s"recoverWithFuture exception, ${e.getMessage}"); Future { 5 }
    }
    f2.foreach(v => logger.info(s"recoverWithFuture, $v"))
  }

  //拉链操作
  def zipFuture(): Unit = {
    val f1 = Future { 2 }
    val f2 = Future { 4 }
    val zipFuture: Future[(Int, Int)] = f1.zip(f2)
    zipFuture.foreach(z => logger.info(s"zipFuture, $z"))

    val future = f1.zipWith(f2) { (v1, v2) =>
      v1 + v2
    } // 等于 f1.zipWith(f2)(_ + _)
    future.foreach(v => logger.info(s"zipFutureWith, $v"))
  }

  def sequenceFuture(): Unit = {
    val futures = List(1, 2, 3).map(x => Future { x * 2 })
    val eventualList = Future.sequence(futures)

    eventualList.foreach(v => logger.info(s"sequence, $v"))
    val result = Future.traverse(List(1, 2, 3)) { p =>
      Future { p * 3 }
    }
    result.foreach(v => logger.info(s"traverse, $v"))

  }

  //自定义执行上下文 线程池，不使用默认的ExecutionContext.Implicits.global ForkJoinPool
  def executionContextFuture(): Unit = {
    val poolSize = Runtime.getRuntime.availableProcessors
    val executor =
      new ThreadPoolExecutor(
        poolSize,
        poolSize,
        10L,
        TimeUnit.SECONDS,
        new ArrayBlockingQueue[Runnable](50),
        new ThreadFactory {

          val threadNumber = new AtomicInteger(1)

          override def newThread(r: Runnable): Thread = {
            val thread = new Thread(r)
            thread.setDaemon(false)
            thread.setPriority(Thread.NORM_PRIORITY)
            thread.setName(s"simple-pool-thread-${threadNumber.getAndIncrement()}")
            thread
          }
        }
      )
    implicit val contextExecutor: ExecutionContextExecutor = ExecutionContext.fromExecutor(executor)
    val f = Future { Thread sleep 1000; "executor" }
    f.foreach(v => logger.info(s"executionContextFuture, $v"))
  }

  /*
  runInFuture()
  multiFuture()
  waitResultFuture()
  waitReadyFuture()
  callbackFuture()
  callbackHellFuture()
  combinedFuture()
  lazyFuture()
  recoverFuture()
  zipFuture()
  executionContextFuture()
   */
  sequenceFuture()

  new CountDownLatch(1).await()
}
