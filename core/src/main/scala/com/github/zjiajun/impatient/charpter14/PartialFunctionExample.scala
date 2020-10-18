package com.github.zjiajun.impatient.charpter14

import com.typesafe.scalalogging.LazyLogging

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/10/17 23:53
  *
  * 偏函数
  * 一个并非对所有输入值都有定义的函数
  */
object PartialFunctionExample extends App with LazyLogging {

  simplePartialFunction()
  catchStatementFunction("a")

  //被包在花括号内的一组case语句是一个偏函数
  def simplePartialFunction(): Unit = {
    val fun: PartialFunction[Char, Int] = {
      case '+' => 1;
      case '-' => -1
    }
    val result = fun('+') //调用fun.apply
    logger.info(s"simplePartialFunction, $result")
    val isDefined = fun.isDefinedAt('*')
    logger.info(s"simplePartialFunction, $isDefined")
    // fun('*') //抛出MatchError异常
  }

  // try语句的catch子句是一个偏函数
  def catchStatementFunction(str: String): Unit = {

    //fun call-by-name 换名调用参数
    def tryCatch[T](fun: => T, catcher: PartialFunction[Throwable, T]) = try { fun } catch catcher

    val result = tryCatch(str.toInt, {
      case ex: NumberFormatException => -1
    })
    logger.info(s"catchStatementFunction, $result")
  }

}
