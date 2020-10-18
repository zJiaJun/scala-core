package com.github.zjiajun.impatient.charpter12

import com.typesafe.scalalogging.LazyLogging

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/10/18 21:22
  *
  * 12.9节 控制抽象
  */
object ControlAbstractionExample extends App with LazyLogging {

  runInThread { () =>
    logger.info(s"runInThread, ${Thread.currentThread().getName}")
  }
  def runInThread(block: () => Unit): Unit = {
    new Thread {
      override def run(): Unit =
        block()
    }.start()
  }

  runInThreadCallByName {
    logger.info(s"runInThreadCallByName, ${Thread.currentThread().getName}")
  }
  //省略(), 换名(call-by-name)调用参数
  def runInThreadCallByName(block: => Unit): Unit = {
    new Thread {
      override def run(): Unit = {
        block
      }
    }.start()
  }


  /*
    call-by-value 换值调用
    call-by-name  换名调用
   */
  def betweenCallByValueAndCallByNameDiff(): Unit = {
    def something() = {
      logger.info("calling something")
      1
    }
    //something 调用一次
    //call-by-value函数在调用函数之前计算传入的表达式的值,每次访问都是相同的值
    def callByValue(x: Int): Unit = {
      logger.info(s"x1 = $x")
      logger.info(s"x2 = $x")
    }
    // something 调用二次
    //call-by-name函数每次都会重新计算传入的表达式值
    def callByName(x: => Int): Unit = {
      logger.info(s"x1 = $x")
      logger.info(s"x2 = $x")
    }
    logger.info("call-by-value")
    callByValue(something())
    logger.info("call-by-name")
    callByName(something())
  }
  betweenCallByValueAndCallByNameDiff()

}
