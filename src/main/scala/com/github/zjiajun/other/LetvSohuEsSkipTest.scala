package com.github.zjiajun.other

import scala.collection.mutable.ListBuffer

/**
  * Created by zhujiajun
  * 16/2/29 13:17
  */
object LetvSohuEsSkipTest extends App {

  val map = Map("ApiType" -> "3","1"-> 90001)
  println(map)
  println(map.get("ApiType"))
  println(map("ApiType"))

  map("1") match  {
    case "2" | "3" => println(222)
    case 90001 => println(8888)
    case _ => println(111)
  }

  val lbuffer = new ListBuffer[String]()

  lbuffer += "2EE9EE8998FE266F6B225EC84E88311C"
  lbuffer += "AC4E02CC855F38886B225EC84E88311C"
  lbuffer += "E374B8265713C4EC6B225EC84E88311C"

  println(lbuffer.toString())
  println(lbuffer.toList)
  println(lbuffer.toList.toString())


}
