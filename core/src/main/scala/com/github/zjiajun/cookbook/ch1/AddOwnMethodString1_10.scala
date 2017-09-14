package com.github.zjiajun.cookbook.ch1


/**
  * @author zhujiajun
  * @since 2017/9/11
  */
object AddOwnMethodString1_10 extends App {

  implicit class StringImprovements(s: String)  {

    def increment: String = s.map(c => (c + 1).toChar)

    def hideAll: String = s.replaceAll(".", "*")

    def plusOne: Int = s.toInt + 1

    def asBoolean: Boolean = s match {
      case "0" | "zero" | "" | " " => false
      case _ => true
    }
  }

  val s = "HAL".increment
  println(s)
  val s2 = "IBM".decrement
  println(s2)
  val s3 = "HELLO".hideAll
  println(s3)
  val s4 = "1".plusOne
  println(s4)
  val s5 = "1".asBoolean
  println(s5)
  val s6 = "0".asBoolean
  println(s6)
}
