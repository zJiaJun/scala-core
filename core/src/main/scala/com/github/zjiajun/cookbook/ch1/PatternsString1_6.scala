package com.github.zjiajun.cookbook.ch1

/**
  * @author zhujiajun
  * @since 2017/9/3
  */
object PatternsString1_6 extends App {

  val pattern = "[0-9]+".r

  val address = "123 Main Street Suite 101"

  val match1: Option[String] = pattern.findFirstIn(address)
  val match2 = pattern.findAllIn(address)
  val match3 = pattern.findAllIn(address).toList

  println(match1)
  match2.foreach(println)
  println(match3)




}
