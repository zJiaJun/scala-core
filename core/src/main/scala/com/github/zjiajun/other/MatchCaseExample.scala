package com.github.zjiajun.other

/**
  * Created by zhujiajun
  * 16/4/2 21:19
  */
object MatchCaseExample extends App {

  def match_1(any: Any): Int =
    any match {
      case str: String  => str.length
      case num: Int     => num
      case boo: Boolean => if (boo) 0 else 1
      case _            => 0
    }

  println(match_1("123"))
  println(match_1(100))
  println(match_1(Boolean.box(false)))
  println(match_1(0.01))
  println("-------------------------")

  def match_2(any: Any): Int =
    any match {
      case m: Map[_, _] => m.size
      case _            => 0
    }

  val m = Map(1 -> "1", 2 -> "2")
  println(match_2(m))

}
