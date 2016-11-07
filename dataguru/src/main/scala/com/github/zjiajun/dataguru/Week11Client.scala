package com.github.zjiajun.dataguru

import java.net.InetSocketAddress

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.ByteString

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * Created by zhujiajun
  * 16/6/5 09:58
  *
  * 继续修改上周的作业，将其改为分布式的形式，前端用akka的TCP方式接受客户端请求，返回结果：
  *  1、数据查询
  *  2、数据的增删改
  *  均通过TCP连接由客户端发出请求，服务器端执行
  *
  *  修改你上周的作业，用router对查询的工作进行负载均衡
  *
  */
object Week11Client extends App {

  val actorSystem = ActorSystem("school-client-system")
  val endpoint = new InetSocketAddress("localhost", 2222)
  val clientService = actorSystem.actorOf(Props(classOf[ClientService],endpoint),"clientService")

  import ExecutionContext.Implicits.global
  actorSystem.scheduler.scheduleOnce(3 seconds,clientService,"add")
  actorSystem.scheduler.scheduleOnce(5 seconds,clientService,"update")
  actorSystem.scheduler.scheduleOnce(7 seconds,clientService,"delete")
  actorSystem.scheduler.schedule(10 seconds,3 seconds,clientService,"query")

}


class ClientService(remote: InetSocketAddress) extends Actor with ActorLogging {

  IO(Tcp)(context.system) ! Connect(remote)

  override def receive: Receive = {

    case CommandFailed(_: Connect) =>
      log.error("Connect Command Failed. Exiting.")
      context stop self //连接失败

    case c @ Connected(remoteAddress,localAddress) =>

      log.info("Connected {} ", c.toString)
      sender() ! Register(self) //这里注册其他的actor有问题,接受不了消息,只能用self,然后become???
      context.become(handler(sender()))
  }

  def handler(sender : ActorRef): Receive = {

    case op: String => sender ! Write(ByteString(op))

    case CommandFailed(_: Write) => log.error("Write Command Failed")

    case Received(byteString) =>
//      log.info("Received byteString from server {}", byteString)
      val content = byteString.decodeString("UTF-8")
      log.info("Received from server {}",content)
  }

}


