package com.github.zjiajun.collections

/**
  * Created by zhujiajun
  * 16/2/9 21:13
  */
object BaseCollections extends App {

  val numbers = List(1,2,3,4)
  println(numbers)

  val setNum = Set(1,1,2) //set中没有重复元素
  println(setNum)

  val host_port = ("localhost",8080)
  println(host_port)
  println(host_port._1) //Tuple(元组)通过位置名称访问,非0开始
  println(host_port._2)

  host_port match {
    case ("localhost",port) => print(port)
    case (host,port) => print(host + ":" + port)
  }

  println()
  val tupleSimple = 1 -> 2 //包含2个值的元组简单的创建方式
  println(tupleSimple)

  val map1 = Map(1 -> "one",2 -> "two")
  val map2 = Map((1,"one"),(2,"two"))
  val map3 = Map(1 -> Map(("foo","bar")))
  val map4 = Map(1 -> generateMap)
  println("this is map1: " + map1)
  println("this is map2: " + map2)
  println("this is map3: " + map3)
  println("this is map4: " + map4)

  def generateMap: String = "this value from function"

  private val option: Option[String] = map1.get(1)
  println(option) //return some(one)
  private val option1: Option[String] = map1.get(3)
  println(option1)//return None
  //Some and None 都是Option的子类


  private val orElse: String = map1.getOrElse(3,"three") //three is default value
  println(orElse)

  val result = map1.get(11) match {
    case Some(v) => v + "_match"
    case None => "No match"
  }
  println(result)




}
