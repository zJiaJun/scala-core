package com.github.zjiajun.other

/**
  * Created by zhujiajun
  * 16/3/7 08:54
  */
object EitherUseExample extends App {

  def divideBy(x: Int, y: Int): Either[String,Int] = {
    if (y == 0) Left("Ops can't divide by 0")
    else Right(x / y)
  }

  val by: Either[String, Int] = divideBy(4,2)
  if (by.isLeft) println(by.left.get) else println(by.right.get)

  divideBy(1,0) match {
    case Left(l) => println(s"Answer is : $l")
    case Right(r) => println(s"Answer is : $r")
  }

}
