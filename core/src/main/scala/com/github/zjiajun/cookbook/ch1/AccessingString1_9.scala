package com.github.zjiajun.cookbook.ch1

/**
  * @author zhujiajun
  * @since 2017/9/10
  */
object AccessingString1_9 extends App {


  println("hello".charAt(0))

  //But behind the scenes, Scala converts your code into this
  //"hello".apply(0)
  println("hello"(0))

}
