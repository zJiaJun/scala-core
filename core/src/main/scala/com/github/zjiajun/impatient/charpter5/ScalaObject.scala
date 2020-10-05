package com.github.zjiajun.impatient.charpter5

import scala.beans.BeanProperty

/**
  * Created by zhujiajun
  * 15/6/11 16:10
  */
class PrivateObj private (val name: String) { outer =>

  var age: Int = 0

  def this(age: Int) {
    this("1")
    this.age = age
  }

  override def toString = s"PrivateObj(age=$age, name=$name)"

}

class ScalaObject(@BeanProperty var pValue: Int = 2) extends PrivateObj(1) {

  val s: Int = 1

  def value = pValue

  def value_=(newValue: Int): Unit =
    if (newValue > pValue) pValue = newValue

  def increment(): Unit =
    value += 1
  def current = value

  def this(pValue: Int, s: String) {
    this()
    this.pValue = pValue
  }
}

object ScalaObject extends App {

  val scalaObj = new ScalaObject
  scalaObj.increment()
  val value = scalaObj.current
  scalaObj.value = 100
  scalaObj.value = 50
  println(value)
  println(scalaObj.value)

  scalaObj.getPValue
  scalaObj.setPValue(2)

  val aa = new PrivateObj(1)
  println(s"PrivateObj age : ${aa.age}")
  aa.age = 90
  println(s"PrivateObj age : ${aa.age}")

  val x = Practice5(100)
  println(x.a)
  println(aa)

  scalaObj match {
    case l: PrivateObj =>
      println(l.toString)
    case _ =>
  }

  println(scalaObj.getClass)
  val s = scalaObj.getClass == classOf[PrivateObj]
  println(s)
}
