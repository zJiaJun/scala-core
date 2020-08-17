package com.github.zjiajun.impatient.charpter14

/**
 * @author zhujiajun
 * @version 1.0
 * @since 2020/8/17 22:54
 */
object ArrayMatch extends App {

  val array = Array(0,10,11,12)

  def arrayMatch(array: Array[Int]): Unit = {
    array match {
      case Array(0) => println("0")
      case Array(x,y) => println(s"$x, $y")
      case Array(0, rest @ _*) => {
        println("0 ...")
        println(rest.seq)
      }
    }
  }

  arrayMatch(array)

}
