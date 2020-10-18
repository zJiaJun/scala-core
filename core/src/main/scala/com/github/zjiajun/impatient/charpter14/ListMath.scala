package com.github.zjiajun.impatient.charpter14

import com.typesafe.scalalogging.LazyLogging

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/8/19 16:11
  */
object ListMath extends App with LazyLogging {

  val list = List(10,20,30)

  //中置表示法
  def listMatch(): Unit = {
    list match {
      case 0 :: Nil      => logger.info("0")
      case x :: y :: Nil => logger.info(s"$x, $y")
      case x :: tail     => logger.info(s"$x, ${tail.mkString(",")}")
      case _             => logger.info("anything")
    }
  }

  listMatch()

  //普通case匹配, :: 其实就是case class
  def middleMatch(): Unit = {
    list match {
      case ::(0, Nil) => logger.info(s"middle, 0")
      case ::(x, ::(y, Nil)) => logger.info(s"middle, $x, $y")
      case ::(x, tail) => logger.info(s"middle, $x, ${tail.mkString(",")}")
      case _ => logger.info(s"middle, anything")
    }
  }
  middleMatch()

}
