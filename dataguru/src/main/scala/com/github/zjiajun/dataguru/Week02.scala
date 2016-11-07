package com.github.zjiajun.dataguru

/**
  * Created by zhujiajun
  * 16/3/31 16:30
  *
  * 杨辉三角：杨辉三角是对二项式系数的计算，第一行第一个元素为1，第二行第一个元素为1，
  * 第二个元素为1，第三行第一个为1，第二个为2，第三个为1，以下每行的元素，最前的和最后的均为1，中间的元素为上一行斜向的两个元素的和，
  * 写一个函数，def pascal(row: Int, col: Int): Int，接受行号和列号为参数，返回row行，col列对应的元素的数值
  *
  * 括号对应，写一个函数：def balance(chars: List[Char]): Boolean，
  * 接受一个字符列表作为输入参数，如果其中存在(和或)，则计算是否对应，如果一一对应，返回true，否则返回false，
  * 如果不存在，则返回true。一一对应的定义为，必须以(为起始，以)为终结。
  * 如“(if (zero? x) max (/ 1 x))”或“I told him (that it’s not (yet) done). (But he wasn’t listening)”应返回true，“--)”或“(()))”应返回false
  */
object Week02 extends App {

  def pascal(row: Int ,col: Int): Int = {
    if (col == 0 || col == row) 1
    else pascal(row - 1,col - 1) + pascal(row - 1,col)
  }

  println(pascal(4,2)) //从0开始 第5行 第3列

  def balance(chars: List[Char]): Boolean = {
    val len= chars.length
    if(len == 0) true
    else if(len % 2 != 0) false
    else if(chars(0) == '(' && chars(1) == ')'){
      balance(chars.slice(2, len))
    }
    else if(chars(len-2) == '(' && chars(len-1) == ')'){
      balance(chars.slice(0, len-2))
    }
    else if(chars.head == '(' && chars.last == ')'){
      balance(chars.slice(1, len -1))
    }
    else false
  }

  val chars = List('(',')')
  println(balance(chars))


  val l = 1 :: 2 :: Nil
  val list = l ::: List(3,4)
  def plist(list: List[Int]): Unit = {
    list match {
      case Nil => println("Nil")
      case head::tail =>
        println("value " + head)
        plist(tail)
    }
  }

  plist(list)

  val m = Map(1 -> "a", 2 -> "b")
  for ((k,v) <- m) {
    println(k)
    println(v)
  }

  for (tuple <- m) {
    println(tuple._1)
    println(tuple._2)
  }












}
