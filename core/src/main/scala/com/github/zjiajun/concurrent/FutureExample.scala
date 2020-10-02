package com.github.zjiajun.concurrent

import org.apache.logging.log4j.scala.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}
import com.github.zjiajun.concurrent.FutureUtils._

/**
 * @author zhujiajun
 * @version 1.0
 * @since 2020/10/2 22:48
 */
object FutureExample extends App with Logging {


  Future {
    logger.info("future begin, do work")
    Thread sleep 1000
    logger.info("future end, finish")
    "return value"
  }.elapsed("example").onComplete {
    case Success(value) => logger.info(value)
    case Failure(exception) => logger.error("exception", exception)
  }

  Thread sleep 100000

}
