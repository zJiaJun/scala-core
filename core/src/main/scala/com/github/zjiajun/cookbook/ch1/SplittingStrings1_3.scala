package com.github.zjiajun.cookbook.ch1

/**
  * @author zhujiajun
  * @since 2017/8/28
  */
object SplittingStrings1_3 extends App {

  val line = "eggs, milk, butter, Coco Puffs"

  val res = line.split(",")

  res.foreach(print)

  res.map(_.trim).foreach(print)


  val ss = "hello world, this is Al".split("\\s+")
  //res0: Array[java.lang.String] = Array(hello, world,, this, is, Al)


  "hello world".split(" ")
  //res0: Array[java.lang.String] = Array(hello, world)

  "hello world".split(' ') //overloaded, coming from the Scala StringLike class
  //res1: Array[String] = Array(hello, world)
}
