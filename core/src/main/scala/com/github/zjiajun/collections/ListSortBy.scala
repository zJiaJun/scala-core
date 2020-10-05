package com.github.zjiajun.collections

import scala.collection.mutable.ListBuffer

/**
  * Created by zhujiajun
  * 16/2/16 20:50
  */
object ListSortBy extends App {

  case class MockObj(id: Int, name: String)

  val list = ListBuffer[(Int, MockObj)]()
  for (i <- 0 to 5) list += (i -> MockObj(i, i + "_name"))
  println("原始数据: " + list)

  val sortBy: ListBuffer[(Int, MockObj)] = list.sortBy(-_._1) // - 倒序 + 正序
  println("排序后数据: " + sortBy)

  sortBy.foreach(println)

}
