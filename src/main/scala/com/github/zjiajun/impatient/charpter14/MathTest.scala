package com.github.zjiajun.impatient.charpter14

/**
 * Created by zhujiajun
 * 15/7/15 16:32
 */
object MathTest extends App {

  var sign = 0
  val ch :Char = '9'
  var obj:Any = 2

  ch match {
    case '+' => sign = 1
    case '-' => sign = 2
    case cc if Character.isDigit(cc) => sign = Character.digit(cc,10)
    case _ => sign = 0
  }

  obj match {
    case x :Int => x
    case _ => 0
  }

  println(sign)
  println(obj)
}
