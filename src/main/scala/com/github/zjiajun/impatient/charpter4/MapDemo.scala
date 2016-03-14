package com.github.zjiajun.impatient.charpter4

/**
  * Created by zhujiajun
  * 16/3/14 21:07
  */
object MapDemo extends App {

  val scores = Map("scala"->10,"java"->20) //不可变map,值不能改变

  val imScores = collection.mutable.Map("python"->10,"php"->20) //可变map

  val emptyMap = new collection.mutable.HashMap[String,Int] //空map
  emptyMap("scala") = 100
  emptyMap += ("java"->200,"php"->300)
  println(emptyMap)

  emptyMap -= "java"
  //println(emptyMap("java")) //throw NoSuchElementException: key not found: java

  println(emptyMap.getOrElse("java",200))

  //scores("scala") = 100 //不能更新不可变map

  val scores2 = scores + ("scala"->100,"php"->300)
  println(scores2)

  val scores3 = scores2 - "php"
  println(scores3)

  for ((k,v) <- scores3)
    println(k,v)

  println(scores3.keySet)
  println(scores3.values)

  val r = for ((k,v) <- scores3) yield (v,k)
  println(r)


}
