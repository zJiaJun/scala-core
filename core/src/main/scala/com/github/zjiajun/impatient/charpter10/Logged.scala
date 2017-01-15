package com.github.zjiajun.impatient.charpter10

/**
  * @author zhujiajun
  * @since 2017/1/15
  */
trait Logged {

  def log(msg: String) {}

}

trait ConsoleLogged extends Logged {

  override def log(msg: String): Unit = {
    println(msg)
  }

}

class Saving extends Logged {

  def save(i: Int) = {
    log("msg:" + i)
  }

}

object TraitMain extends App {

  val sa = new Saving with ConsoleLogged
  sa.save(123)
}

