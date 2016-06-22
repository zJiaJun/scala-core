package com.github.zjiajun.impatient.charpter8

/**
  * Created by zhujiajun
  * 16/6/22 14:25
  */
object Extends extends App {

  class Creature {
    val range: Int = 10
    val env: Array[Int] = new Array[Int](range)
    //此处获取range取值器,调用子类的,而子类还未初始化,所以range返回0,整型字段的初始值
  }

  class Ant extends Creature {
    override val range: Int = 2
  }

  val ant = new Ant
  println(ant.range)
  println(ant.env.length)

}
