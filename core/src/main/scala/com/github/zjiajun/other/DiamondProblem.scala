package com.github.zjiajun.other

/**
 * @author zhujiajun
 * @version 1.0
 * @since 2020/8/29 15:41
 *
 * 多继承菱形问题
 *       A
 *      B C
 *       D
 *
 *  B和C继承A, D继承B和C, A有个方法被B和C重载, D不重载此方法, 那么D实现谁的方法, B还是C？
 */
object DiamondProblem extends App {

  val obj =  new D("msg") with B with C
  obj.method("something")

}

trait A {
  println("A...")
  def method(msg: String) = println(msg)

}
trait B extends A {
  println("B...")
  override def method(msg: String): Unit = println(s"B-$msg")
}
trait C extends A {
  println("C...")
  override def method(msg: String): Unit = println(s"C-$msg")
}

class D(msg: String) extends A {
  println("D...")
}




