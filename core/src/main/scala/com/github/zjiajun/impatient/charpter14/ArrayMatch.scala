package com.github.zjiajun.impatient.charpter14

import org.apache.logging.log4j.scala.Logging

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/8/17 22:54
  */
object ArrayMatch extends App with Logging {

  val array = Array(0, 10, 11, 12)

  def arrayMatch(array: Array[Int]): Unit =
    array match {
      case Array(0)    => logger.info("0")
      case Array(x, y) => logger.info(s"$x, $y")
      case Array(0, rest @ _*) => {
        logger.info("0 ...")
        logger.info(rest.seq)
      }
    }

  arrayMatch(array)

}
