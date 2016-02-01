package com.github.zjiajun.akka

/**
  * Created by zhujiajun
  * 16/1/30 17:01
  */
trait Message {
  val id: String
}

object Start extends Serializable
object Stop extends Serializable

case class Shutdown(waitSecs: Int) extends Serializable
case class Heartbeat(id: String,magic: Int) extends Message with Serializable
case class Header(id: String,len: Int,encrypted: Boolean) extends Message with Serializable
case class Packet(id: String,seq: Long, content: String) extends Message with Serializable
