package com.github.zjiajun.impatient.charpter2

import scala.io.Source

/**
  * Created by zhujiajun
  * 16/3/5 12:55
  */
object LazyParam extends App {

  lazy val words = Source
    .fromFile(
      "/Users/zhujiajun/Work/ideaProjects/scala-sbt/src/main/scala/com/github/zjiajun/impatient/charpter2/LazyParam.scala"
    )
    .mkString

  println(words)

}
