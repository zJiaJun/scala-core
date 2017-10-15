package com.github.zjiajun.cookbook.ch2

/**
  * @author zhujiajun
  * @since 2017/10/15
  */
object OverridingDefaultNumberType_2_3 extends App {

  val a = 1
  println(s"value is $a, type is ${a.getClass}")
  val b = 1d
  println(s"value is $b, type is ${b.getClass}")
  val c = 1f
  println(s"value is $c, type is ${c.getClass}")

  val a2 = 1: Byte
  println(s"value is $a2, type is ${a2.getClass}")
  val b2 = 1: Short
  println(s"value is $b2, type is ${b2.getClass}")
  val c2 = 1: Double
  println(s"value is $c2, type is ${c2.getClass}")
  val d2 = 1: Float
  println(s"value is $d2, type is ${d2.getClass}")


  val a3: Byte = 123
  println(s"value is $a3, type is ${a3.getClass}")
  val b3 = 0x3703
  println(s"value is $b3, type is ${b3.getClass}")
  val c3 = 0x3703L
  println(s"value is $c3, type is ${c3.getClass}")

}
