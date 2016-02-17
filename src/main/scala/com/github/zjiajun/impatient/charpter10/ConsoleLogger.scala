package com.github.zjiajun.impatient.charpter10

/**
 * Created by zhujiajun
 * 15/7/10 16:42
 */
class ConsoleLogger extends Logger with TraitClazz {

  def fuck(msg: String) = {
    log(msg)
    println(msg)
  }

  def fuckAgain(msg :String): Unit = {
    write(msg)
  }

}

object ConsoleLogger extends App {

  val console = new ConsoleLogger with TraitClazzChild
  console.fuck("1")
  console.fuckAgain("2")

}