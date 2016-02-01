package com.github.zjiajun.akka

import akka.actor.{ActorLogging, Actor}

/**
  * Created by zhujiajun
  * 16/2/1 21:32
  */
class RemoteActor extends Actor with ActorLogging {

  val SUCCESS = "SUCCESS"
  val FAILURE = "FAILURE"

  override def receive: Receive = {
    case Start => log.info("RECV event: " + Start)
    case Stop  => log.info("RECV event: " + Stop)
    case Shutdown(waitSecs) => {
      log.info("Wait to shutdown: waitSecs=" + waitSecs)
      Thread.sleep(waitSecs)
      log.info("Shutdown this system")
      context.system.terminate()
    }
    case Heartbeat(id,magic) => log.info("RECV heartbeat: " + (id,magic))
    case Header(id,len,encrypted) => log.info("RECV header: " + (id,len,encrypted))
    case Packet(id,seq,content) => {
      val originalSender = sender //获取到当前发送方的Actor引用
      log.info("RECV packet: " +(id, seq, content))
      originalSender !(seq, content) // 响应给发送方消息处理结果，类似发送一个ACK
    }
    case - =>
  }

}
