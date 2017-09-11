package com.github.zjiajun.cookbook.ch1

/**
  * @author zhujiajun
  * @since 2017/9/11
  */
object AddOwnMethodString1_10 extends App {

  implicit class StringImprovements(s: String)  {

    def increment= s.map(c => (c + 1).toChar)

  }

  val s = "HAL".increment
  println(s)
}
