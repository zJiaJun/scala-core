package com.github.zjiajun.akka

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.routing.RoundRobinPool

/**
  * @author zhujiajun
  * @since 2018/1/25
  */
object RoutingTest extends App  {


  val system = ActorSystem("routing")
  system.settings.SerializeAllMessages
  val actor = system.actorOf(RoundRobinPool(5).props(Props[Test]), "test")
  println(actor)
  for (i <- 1 to 5) {
    actor ! i.toString
  }
  Thread sleep  5000
  system.terminate()


  class Test extends Actor with ActorLogging {

    val tId: Long = Thread.currentThread().getId

    override def preStart(): Unit = log.info(s"$tId + preStart + $this")

    override def postStop(): Unit = log.info(s"$tId postStop + $this")

    override def preRestart(reason: Throwable, message: Option[Any]): Unit = log.info(s"$tId preRestart + $this")

    override def postRestart(reason: Throwable): Unit = log.info(s"$tId postRestart + $this")

    override def receive: Actor.Receive = {
      case str: String => log.info(s"$tId $this + $str, $sender()")
      case _ => log.error("wrong")
    }
  }

}
