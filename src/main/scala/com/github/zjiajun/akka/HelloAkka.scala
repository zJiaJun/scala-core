package com.github.zjiajun.akka

import akka.actor.{ActorRef, Props, ActorSystem, Actor}
/**
  * Created by zhujiajun
  * 16/1/29 21:45
  */
object HelloAkka extends App {

  val system: ActorSystem = ActorSystem("actor-demo")
  val hello: ActorRef = system.actorOf(Props[Hello],"root")
  hello ! "zjiajun" //相当于 hello.tell("zjiajun",ActorRef.noSender)
  Thread sleep 1000
  system terminate


  class Hello extends Actor {
    override def receive: Actor.Receive = {
      case name : String => println(s"Hello $name")
    }
  }
}
