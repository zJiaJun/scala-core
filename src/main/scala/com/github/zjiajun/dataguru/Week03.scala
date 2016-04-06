package com.github.zjiajun.dataguru

/**
  * Created by zhujiajun
  * 16/4/3 20:13
  *
  * Bob是一个自动回答机器，对于问句，总是回答："Sure."，
  * 如果你冲他叫喊，他就回答："Whoa, chill out!"，
  * 如果你对他说的话都是空的，比如全是空格或者tab，那么他会回答："Fine. Be that way."，
  * 对于其它内容，都会回答："Whatever."
  *
  * 场景：
  *  1、"WATCH OUT!" 回答 "Whoa, chill out!"
  *  2、"Do you speak English?"  回答 "Sure."
  *  3、"DO YOU HEAR ME?" 回答 "Whoa, chill out!"
  *  4、"What DID you do?" 回答 "Sure."
  *  5、"   " 回答 "Fine. Be that way."
  */
object Week03 extends App {

  val sure = "Sure"
  val whoa = "Whoa, chill out!"
  val fine = "Fine. Be that way."
  val other = "Whatever."

  def autoBob(str: String): String = {
    val length = str.trim.length
    if (length == 0) fine
    else if (str.toUpperCase == str && str.toLowerCase == str) other
    else if (str.toUpperCase == str) whoa
    else if (str.toUpperCase != str && str(length - 1) == '?') sure
    else other
  }

  println(autoBob("WATCH OUT!"))
  println(autoBob("Do you speak English?"))
  println(autoBob("DO YOU HEAR ME?"))
  println(autoBob("What DID you do?"))
  println(autoBob("  "))

}
