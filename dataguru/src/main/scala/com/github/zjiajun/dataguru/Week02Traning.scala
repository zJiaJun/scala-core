package com.github.zjiajun.dataguru

/**
  * Created by zhujiajun
  * 16/4/10 13:37
  *
  * 使用字典表加密,替换字符串,list递归循环
  */
class Week02Traning(str: String) {

  val values = str

  def this() {
    this("abc")
  }


  def encodeValue(v: Char) = Week02Traning.dic.getOrElse(v,"null")

  def merge(values: List[Char], result: List[String]): List[String] = {
    println(values)
    values match {
      case Nil        =>  result
      case head::tail =>  merge(tail,result :+ encodeValue(head))
    }
  }

  def encode(): List[String] = {
    merge(values.toList,List())
  }

}

object Week02Traning extends App {

  val dic = Map('a'-> "x",'b'-> "y",'c' -> "z")

  def apply(s: String) = {
    new Week02Traning(s)
  }

  println(Week02Traning("abc").encode())

}
