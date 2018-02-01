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

case class Shutdown(waitSecs: Int)
case class Heartbeat(id: String,magic: Int) extends Message
case class Header(id: String,len: Int,encrypted: Boolean) extends Message
case class Packet(id: String,seq: Long, content: String) extends Message
