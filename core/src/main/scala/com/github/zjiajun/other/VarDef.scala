package com.github.zjiajun.other

/**
  * @author zhujiajun
  * @since 2016/11/27
  *
  * 花式变量丁定义
  */
class VarDef {

  val x,y = 0

  val (x1,y1) = (10,"123")

  val x2::y2 = List(1,2,3,4)

  val List(a,b,c) = List(1,2,3)

  val Array(aa,bb,_,_,cc @ _*) = Array(1,2,3,4,5,6,7)


  def show():Unit = {
    println("-------------------")
    println(s"x = $x, y = $y")
    println(s"x1 = $x1, y1 = $y1")
    println(s"x2 = $x2, y2 = $y2")
    println(s"a = $a, b = $b, c = $c")
    println(s"aa = $a, bb= $bb, cc = $cc")
    println("-------------------")
  }

}

object VarDef extends App {

  def apply() = new VarDef

  VarDef().show()

}

