package com.github.zjiajun

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.mutable.ListBuffer

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


  val url = ListBuffer[String]()
  url += "www.baidu.com"
  url.+=: ("www.yahoo.com")
  url += "www.google.com"
  println(url)

  val config: Config = ConfigFactory.load
  val redisHost = if (config.hasPath("redis.host")) config.getString("redis.host") else "127.0.0.100"
  println(redisHost)

  val zkConfig: Config = ConfigFactory.load("example")
  println(zkConfig.getString("zookeeper.host"))
  println(zkConfig.getInt("zookeeper.port"))

  def fuzhi() {
    var s = 100
    s = 10 //赋值动作本身是没有值的,Unit类型()
  }
  println(fuzhi())

}
