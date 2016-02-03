package com.github.zjiajun.akka

import akka.actor.{ActorLogging, Actor}

/**
  * Created by zhujiajun
  * 16/2/2 21:29
  */
class ClientActor extends Actor with ActorLogging {

  //akka.<protocol>://<actor system>@<hostname>:<port>/<actor path>
  val path = "akka.tcp://remote-system@127.0.0.1:2552/user/remoteActor"
  val remoteActorRef = context.actorSelection(path)

  @volatile var connected = false
  @volatile var stop = false

  override def receive: Receive = {
    case Start => {
      send(Start)
      if (!connected) {
        connected = true
        log.info("Actor connected: " + this)
      }
    }
    case Stop => {
      send(Stop)
      stop = true
      connected = false
    }
    case h: Header => send(h)
    case hb: Heartbeat => sendWithCheck(hb)
    case pk: Packet => sendWithCheck(pk)
    case sh: Shutdown => send(sh)
    case (seq,result) => log.info("RESULT: seq=" + seq + ", result=" + result) // 用于接收远程Actor处理一个Packet消息的结果
    case m => log.info("Unknown message: " + m)

  }

  private def sendWithCheck(cmd: Serializable): Unit = {
    while (!connected) {
      Thread.sleep(1000)
      log.info("Wait to be connected...")
    }
    if (!stop) {
      send(cmd)
    } else {
      log.warning("Actor has stopped!")
    }
  }

  private def send(cmd: Serializable): Unit = {
    log.info("Send command to server: " + cmd)
    try {
      remoteActorRef ! cmd //发送一个消息到远程Actor，消息必须是可序列化的，因为消息对象要经过网络传输
    } catch {
      case e: Exception => {
        connected = false
        log.info("Try to connect by sending Start command...")
        send(Start)
      }
    }

  }
}
