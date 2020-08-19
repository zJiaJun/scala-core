package com.github.zjiajun.impatient.charpter14

import org.apache.logging.log4j.scala.Logging

/**
 * @author zhujiajun
 * @version 1.0
 * @since 2020/8/19 16:11
 */
object ListMath extends App with Logging {

  val list = List(1000,2000,3000)

  def listMatch(list: List[Int]): Unit = {
    list match {
      case 0 :: Nil => logger.info("0")
      case x :: y :: Nil => logger.info(s"$x, $y")
      case 0 :: tail => logger.info(tail.mkString(","))
      case _ => logger.info("anything")
    }
  }

  listMatch(list)

}
