package com.github.zjiajun.cookbook.ch2

/**
  * @author zhujiajun
  * @since 2017/10/13
  */
object NumberTypeConvert extends App {

  val a = "100"

  println(a.toInt)
  println(a.toFloat)
  println(a.toDouble)
  println(a.toLong)

  val b = 1000L

  b.isValidByte match {
    case true => println(b.toByte)
    case false => println("number cannot convert byte")
}

  b.isValidShort match {
    case true => println(b.toShort)
    case false => println("number cannot convert short")
  }

  b.isValidChar match {
    case true => println(b.toChar)
    case false => println("number cannot convert char")
  }

}
