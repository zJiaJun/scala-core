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

  val fun: PartialFunction[Char, Int] = { case '+' => 1; case '-' => -1 }
  val result = fun('+') //调用fun.apply
  logger.info(s"$result")
  val isDefined = fun.isDefinedAt('*')
  logger.info(s"$isDefined")
  fun('*')

}
