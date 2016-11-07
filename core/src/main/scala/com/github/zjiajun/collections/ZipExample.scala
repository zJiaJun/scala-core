package com.github.zjiajun.collections

/**
  * Created by zhujiajun
  * 16/2/29 21:38
  */
object ZipExample extends App {

  val l1 = List[String]("a","b","c")
  val l2 = List[Int](1,2,3)
  val z12: List[(String,Int)] = l1.zip(l2)
  val z2Map: Map[String, Int] = z12.toMap

  println(z12)
  println(z2Map)

  val l3 = List[String]("aa","bb","cc")
  val l4 = List[Int](1,2)
  val z34: List[(String, Int)] = l3.zipAll(l4,"cc",3) //zipAll use default value
  println(z34)

  private val withIndex: List[(String, Int)] = l3.zipWithIndex //元素和下标组成元祖
  println(withIndex)

  private val unzip: (List[String], List[Int]) = withIndex.unzip
  println(unzip)
  println(unzip._1)
  println(unzip._2)

}
