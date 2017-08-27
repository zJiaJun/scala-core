package com.github.zjiajun.cookbook.ch1

/**
  * @author zhujiajun
  * @since 2017/8/27
  */
object StringEquality extends App {

  val s1 = "Hello"
  val s2 = "Hello"
  val s3 = "H" + "ello"

  println(s1 == s2)
  println(s1 == s3)

  //it doesn’t throw a NullPointerException
  val s4 = null
  println(s3 == s4)
  println(s4 == s3)
}
