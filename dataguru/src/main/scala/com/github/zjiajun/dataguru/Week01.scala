package com.github.zjiajun.dataguru

/**
  * Created by zhujiajun
  * 16/3/27 14:03
  *
  * 牛顿法求任意数的平方根
  * 递归求解
  * x = 任意数
  * y = 猜测值
  * y = (y + x / y) / 2
  */
object Week01 extends App {

  def sqrt(y: Double, x: Double): Double = {
    if (x <= 0 || y <= 0) throw new IllegalArgumentException()
    val value = (y + (x / y)) / 2
    println(value)
    if (math.abs(value - y) > 0.001) sqrt(value,x) else value
  }

  println(sqrt(1,2))
}

/**
  *
  * (1 + (2/1)) / 2 = 1.5
  * abs(1.5 - 1) > 0.0000001
  * sqrt(1.5,2)
  *
  * (1.5 + (2/1.5)) / 2 = 1.416666667
  * abs(1.416666667 - 1.5) > 0.0000001
  * sqrt(1.416666667,2)
  *
  *(1.416666667 + (2/1.416666667)) / 2 = 1.414215686
  * abs(1.414215686 - 1.416666667) > 0.0000001
  *
  * ...
  *
  */
