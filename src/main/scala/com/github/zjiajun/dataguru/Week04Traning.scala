package com.github.zjiajun.dataguru

/**
  * Created by zhujiajun
  * 16/4/17 09:38
  */
object Week04Traning extends App {

  var inc = (x: Int) => x * 2

  println(inc(2))

  var i = 0
  val list = 1::2::3::Nil
  list.foreach(println)
  list.foreach(i += _)//闭包
  println(i)


}
