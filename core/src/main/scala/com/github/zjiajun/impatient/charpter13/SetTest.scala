package com.github.zjiajun.impatient.charpter13

/**
  * Created by zhujiajun
  * 15/7/13 11:19
  */
object SetTest extends App {

  val s1 = Set(1, 2, 3, 4, 5)
  val s2 = Set(1, 3, 5, 7, 8)

  val ns1 = s1.union(s2) //or use s1 | s2,  s1 ++ s2
  val ns2 = s1.intersect(s2) //or use s1 & s2
  val ns3 = s1.diff(s2) //or use s1 &~ s2, s1 -- s2

  println(ns1)
  println(ns2)
  println(ns3)

}
