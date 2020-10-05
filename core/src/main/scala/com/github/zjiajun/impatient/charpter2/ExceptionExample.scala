package com.github.zjiajun.impatient.charpter2

import scala.util.{Failure, Success, Try}

/**
  * Created by zhujiajun
  * 16/3/5 13:21
  */
object ExceptionExample extends App {

  def throwException(): Unit =
    try {
      print(1 / 0)
    } catch {
      case _: ArithmeticException => println("ArithmeticException")
      case ex: RuntimeException   => ex.printStackTrace()
    }

  def useScalaTry(x: Int, y: Int): Try[Int] = Try(x / y)

  throwException()

  println(useScalaTry(1, 1).getOrElse(0))
  println(useScalaTry(1, 0).getOrElse(0))

  useScalaTry(1, 1).foreach(println)
  useScalaTry(1, 0).foreach(println)
  useScalaTry(4, 2) match {
    case Success(i) => println(s"Success values is: $i")
    case Failure(e) => println(s"Failed message is: $e")
  }

}
