package com.github.zjiajun

/**
  * Created by zhujiajun
  * 16/2/17 15:21
  */
object Test extends App {

  var crid = "1"

  val crid_2 = if(crid == null || crid.length == 0) {
    "www"
  } else "ooo"

  println(crid_2)

}
