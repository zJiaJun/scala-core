package com.github.zjiajun.concurrent

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

/**
 * @author zhujiajun
 * @version 1.0
 * @since 2020/10/2 22:43
 */
object FutureUtils extends LazyLogging {

  private val durationLimit = 100.millisecond.toMillis

  implicit class FutureWrapper[T](future: Future[T]) {

    def elapsed(tag: String): Future[T] = {
      val begin = System.currentTimeMillis()
      future.map { result =>
        val duration = System.currentTimeMillis() - begin
        if (duration > durationLimit) logger.warn(s"slow invoked: [$tag] elapsed time: [${System.currentTimeMillis() - begin} ms]")
        else logger.info(s"invoked: [$tag] elapsed time: [${System.currentTimeMillis() - begin} ms]")
        result
      }
    }
  }

}
