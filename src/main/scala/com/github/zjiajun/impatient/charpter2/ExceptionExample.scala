package com.github.zjiajun.impatient.charpter2

/**
  * Created by zhujiajun
  * 16/3/5 13:21
  */
object ExceptionExample extends App {

  def throwException(): Unit = {
    try {
      print(1/0)
    } catch {
      case _: ArithmeticException => println("ArithmeticException")
      case ex: RuntimeException => ex.printStackTrace()
    }
    println(222)
  }

  throwException()

}
