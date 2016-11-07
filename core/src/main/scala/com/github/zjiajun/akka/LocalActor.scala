package com.github.zjiajun.akka

import akka.actor.{ActorLogging, Actor}

/**
  * Created by zhujiajun
  * 16/1/30 17:05
  */
class LocalActor extends Actor with ActorLogging {

  override def receive: Receive = {
    case Start => log.info("start")
    case Stop => log.info("stop")
    case Heartbeat(id,magic) => log.info("Heartbeat" + (id,magic))
    case Header(id,len,encrypted) => log.info("Header" + (id,len,encrypted))
    case Packet(id,seq,content) => log.info("Packet" + (id,seq,content))
    case _ => log.error("Something wrong")
  }
}
