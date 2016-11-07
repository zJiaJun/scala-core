package com.github.zjiajun.akka

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.atomic.AtomicLong

import akka.actor.{Props, ActorSystem}
import com.typesafe.config.{ConfigFactory, Config}

import scala.util.Random

/**
  * Created by zhujiajun
  * 16/2/2 21:50
  */
object AkkaClientApplication extends App {

  val config: Config = ConfigFactory.load().getConfig("MyRemoteClientSideActor")
  val system = ActorSystem("client-system",config)
  val log = system.log
  val clientActorRef = system.actorOf(Props[ClientActor],"client-actor")

  @volatile var running = true
  val hbInterval = 1000

  lazy val hbWorker = createHBWorker

  def createHBWorker: Thread = { // 心跳发送线程
    new Thread("HB-WORKER") {
      override def run(): Unit = {
        var i = 0
        while (running) {
          clientActorRef ! Heartbeat("HB",i)
          Thread.sleep(hbInterval)
          i+=1
        }
      }
    }
  }

  clientActorRef ! Start // 发送一个Start消息，第一次与远程Actor握手（通过本地ClientActor进行转发）
  Thread.sleep(2000)

  clientActorRef ! Header("HEADER",20,encrypted = false) // 发送一个Header消息到远程Actor（通过本地ClientActor进行转发）
  Thread.sleep(2000)

  hbWorker.start()

  // send some packets
  val DT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"
  val r = Random
  val ID = new AtomicLong(90760000)
  val packetCount = 100
  val serviceProviders = Seq("CMCC", "AKBBC", "OLE")
  val payServiceProviders = Seq("PayPal", "CMB", "ICBC", "ZMB", "XXB")

  def nextTxId: Long = ID.incrementAndGet()

  def nextProvider(seq: Seq[String]): String = seq(r.nextInt(serviceProviders.size))

  def format(timeStamp: Long,format: String): String = {
    val df = new SimpleDateFormat(format)
    df.format(new Date(timeStamp))
  }

  def createPacket(packet: Map[String, _]): String = packet.toString()

  val startWhen = System.currentTimeMillis()
  for (i <- 0 until packetCount) {
    val packet = createPacket(Map[String,Any](
      "txid" -> nextTxId,
      "pvid" -> nextProvider(serviceProviders),
      "txtm" -> format(System.currentTimeMillis(),DT_FORMAT),
      "payp" -> nextProvider(payServiceProviders),
      "amout" -> 1000 * r.nextDouble()
    ))
    clientActorRef ! Packet("Packet",System.currentTimeMillis(),packet)
  }
  val finishWhen = System.currentTimeMillis()
  log.info("FINISH: timeTaken=" + (finishWhen - startWhen) + ", avg=" + packetCount/(finishWhen - startWhen))
  Thread.sleep(2000)

  val waitSecs = hbInterval
  clientActorRef ! Shutdown(waitSecs) // 发送Packet消息完成，通知远程Actor终止服务

  running = false
  while (hbWorker.isAlive) {
    log.info("Wait heartbeat worker to exit...")
    Thread.sleep(300)
  }

  system.terminate() // 终止本地ActorSystem系统

}
