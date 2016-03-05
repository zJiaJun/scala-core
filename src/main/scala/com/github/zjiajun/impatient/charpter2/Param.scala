package com.github.zjiajun.impatient.charpter2

import java.text.MessageFormat

/**
  * Created by zhujiajun
  * 16/3/5 11:00
  */
object Param extends App {

    def decorate(str: String,left: String = "[",right: String = "]") = println(left + str + right)

    decorate("Hello")
    decorate("Hello","<<<",">>>")
    decorate("Hello","<<<[")
    decorate(left = "<<",str = "Hello",right = ">>")
    decorate("Hello",right = "]>>>")

    def sum(args: Int*) = {
      var result = 0
      for (arg <- args) result += arg
      result
    }

    println(sum(1))
    println(sum(1,2,3))
    println(sum(1 to 3:_*))

    val str = MessageFormat.format("The answer to {0} is {1}","everything",42.asInstanceOf[AnyRef])
    println(str)


}
