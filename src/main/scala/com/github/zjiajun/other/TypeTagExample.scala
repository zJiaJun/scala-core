package com.github.zjiajun.other

import scala.reflect.runtime.universe._

/**
  * Created by zhujiajun
  * 16/3/22 09:51
  */
object TypeTagExample extends App {

  def meth[A: TypeTag](xs: A) = typeOf[A] match {
    case t if t =:= typeOf[String] => "string"
    case t if t =:= typeOf[Request1] => 100
    case t if t =:= typeOf[Request2] => 200
  }

  private val meth1: Any = meth("string")
  private val meth2: Any = meth(new Request1)
  private val meth3: Any = meth(new Request2)

  println(meth1)
  println(meth1.asInstanceOf[String].length)
  println(meth2)
  println(meth3)

}

class Request1
class Request2
