package com.github.zjiajun.impatient.charpter2

/**
  * Created by zhujiajun
  * 16/2/24 21:16
  */
object ScalaFor extends App {

  val s = for (i <- 1 to 10) yield i * 2
  println(s)

  val s2 = for (c <- "Hello"; i <- 0 to 1) yield (c + i).toChar
  println(s2)

  val s3 = for (i <- 0 to 1;c <- "Hello") yield (c + i).toChar
  println(s3)

}
