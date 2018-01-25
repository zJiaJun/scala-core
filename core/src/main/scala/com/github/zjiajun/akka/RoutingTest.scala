package com.github.zjiajun.akka

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.RoundRobinPool

/**
  * @author zhujiajun
  * @since 2018/1/25
  */
object RoutingTest extends App  {


  val system = ActorSystem("routing")
  val actor = system.actorOf(RoundRobinPool(5).props(Props[Test]), "test")
  for (i <- 1 to 5) {
    actor ! i.toString
  }
  Thread sleep  5000
  system.terminate()


  class Test extends Actor {


    override def preStart(): Unit = println("preStart " + this)

    override def postStop(): Unit = println("postStop " + this)

    override def preRestart(reason: Throwable, message: Option[Any]): Unit = println("preRestart " + this)

    override def postRestart(reason: Throwable): Unit = println("postRestart " + this)

    override def receive: Actor.Receive = {
      case str: String => println(s"$this + $str")
      case _ => println("wrong")
    }
  }

}
