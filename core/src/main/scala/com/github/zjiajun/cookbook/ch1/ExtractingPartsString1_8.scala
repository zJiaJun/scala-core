package com.github.zjiajun.cookbook.ch1

/**
  * @author zhujiajun
  * @since 2017/9/7
  */
object ExtractingPartsString1_8 extends App {

  val pattern = "([0-9]+) ([A-Za-z]+)".r

  val pattern(count, fruit) = "100 Bananas"

  println(count)
  println(fruit)

}
