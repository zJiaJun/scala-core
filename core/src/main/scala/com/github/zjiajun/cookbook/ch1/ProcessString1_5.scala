package com.github.zjiajun.cookbook.ch1

/**
  * @author zhujiajun
  * @since 2017/9/1
  */
object ProcessString1_5 extends App {

  val upper1 = "hello,world".map(c => c.toUpper)
  println(upper1)

  val upper2 = "hello,world".map(_.toUpper)
  println(upper2)

  val upper3 = "hello,world".filter(_ != 'l').map(_.toUpper)
  println(upper3)

  val upper4 = for (c <- "hello,world") yield c.toUpper
  println(upper4)

  val upper5 = for {
    c <- "hello,world"
    if c != 'l'
  } yield c.toUpper
  println(upper5)

  val lower1 = "HELLO".map(c => (c.toByte + 32).toChar)
  println(lower1)

  def toLower(c: Char): Char = (c.toByte + 32).toChar

  val lower2 = "HELLO".map(toLower)
  println(lower2)

  val lower3 = for (c <- "HELLO") yield toLower(c)
  println(lower3)

  val toLowerFun = (c: Char) => (c.toByte + 32).toChar

  val lower4 = "HELLO".map(toLowerFun)
  println(lower4)

}
