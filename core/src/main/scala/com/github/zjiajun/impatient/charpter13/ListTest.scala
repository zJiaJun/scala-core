package com.github.zjiajun.impatient.charpter13


/**
 * Created by zhujiajun
 * 15/7/13 10:15
 */
object ListTest extends App {

  val digits = List[Int](1,2,3,4,5)

  private def printlnList(list : List[Any]): Unit = {
    println(list.head)
    println(list.tail)
    println(list.tail.head)
    println(list.tail.tail)
  }

  private def sumList(list: List[Int]) :Int = {
    if (list == Nil) 0 else list.head + sumList(list.tail)
  }

  private def sumList2(list: List[Int]) :Int = {
    list match {
      case Nil => 0

      case h :: t => h +sumList2(t)
    }
  }

  printlnList(digits)

  val digits2 = 0 :: digits

  println(digits2)

  printlnList(digits2)

  println(sumList(digits))

  println(sumList2(digits))

  println(digits.sum)

  val appendHead = 0 +: digits
  val appendTail = digits :+ 6
  val d = digits :+ 2
  val f = 2 +: (3 :: digits) :+ 6
  val g = digits ::: digits2

  println(appendHead)
  println(appendTail)
  println(d)
  println(f)
  println(g)

  println(digits.headOption)

}
