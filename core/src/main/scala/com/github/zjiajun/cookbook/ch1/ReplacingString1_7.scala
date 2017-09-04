package com.github.zjiajun.cookbook.ch1

/**
  * @author zhujiajun
  * @since 2017/9/4
  */
object ReplacingString1_7 extends App {

  val address = "123 Main Street".replaceAll("[0-9]", "x")
  println(address)

  val regex = "[0-9]".r

  val newAddress = regex.replaceAllIn("123 Main Street", "x")
  println(newAddress)

  val regex2 = "H".r

  val result = regex2.replaceAllIn("Hello World", "J")
  println(result)

}
