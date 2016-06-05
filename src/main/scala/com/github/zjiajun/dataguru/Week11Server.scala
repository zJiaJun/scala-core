package com.github.zjiajun.dataguru

import java.net.InetSocketAddress

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.{ByteString, ByteStringBuilder}

/**
  * Created by zhujiajun
  * 16/6/5 09:58
  *
  * 继续修改上周的作业，将其改为分布式的形式，前端用akka的TCP方式接受客户端请求，返回结果：
  *  1、数据查询
  *  2、数据的增删改
  *  均通过TCP连接由客户端发出请求，服务器端执行
  */
object Week11Server extends App {

  val school = Map[String,(Int, String, Int, Int)](
    "Teacher_1" -> (30, "T", 0, 0),
    "Student_1" -> (18, "S", 3, 100)
  )

  val actorSystem = ActorSystem("school-server-system")
  val endpoint = new InetSocketAddress("localhost", 2222)
  actorSystem.actorOf(Props(classOf[ServerService],endpoint),"serverService")


}

class ServerService(endpoint: InetSocketAddress) extends Actor with ActorLogging {

  IO(Tcp)(context.system) ! Tcp.Bind(self,endpoint,backlog = 120)//1.tcp bind

  override def receive: Receive = {

    case b @ Bound(localAddress) => log.info("Bound {} ", b.toString) //2.bound事件

    case CommandFailed(_: Bind) =>
      log.error("Binding Command Failed. Exiting.")
      context stop self

    case c @ Connected(remoteAddress,localAddress) => //3.连接事件
      log.info("Connected {} ", c.toString)
      val serverHandler = context.actorOf(Props[ServerHandler], "serverHandler")
      log.info("send " + sender())
      sender() ! Register(serverHandler,keepOpenOnPeerClosed = true) //4.注册,以后的套接字发送到注册的actor


  }
}


class ServerHandler extends Actor with ActorLogging {

  override def receive: Receive = {

    case Received(byteString) => //5.接收客户端信息
//      log.info(sender().toString())
//      log.info("Received byteString from client {}",byteString)
      val content = byteString.decodeString("UTF-8")
      log.info("Received from client {}", content)

      val response = content match {
        case "query" => s"query success ${Week11Server.school.toString()}"
        case "add" => s"add success ${(Week11Server.school + ("Student_2" ->(19, "S", 3, 99))).toString()}"
        case "delete" => s"delete success ${(Week11Server.school - "Teacher_1").toString()}"
        case "update" => s"update success ${(Week11Server.school + ("Student_1" ->(18, "S", 3, 80))).toString()}"
      }

      sender() ! Write(ByteString(response)) //6.发送给客户端

    case PeerClosed =>
      log.info("PeerClosed stop self")
      context stop self
  }
}

