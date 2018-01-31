package com.github.zjiajun.akka

import akka.actor.{Actor, ActorLogging, ActorPath, ActorRef, ActorSystem, Props}


/**
  * Created by zhujiajun
  * 16/1/29 21:45
  */
object HelloAkka extends App {

  val system: ActorSystem = ActorSystem("actor-demo")
  val hello: ActorRef = system.actorOf(Hello.props,"hello")
  println(hello.path)
  val other = system.actorOf(Props[Other], "other")
  hello ! "zjiajun"
  Thread sleep 1000
  system terminate()


  class Hello extends Actor with ActorLogging {

    override def receive: Receive = {
      case name: String => {
        log.info(s"msg: $name, sender: $sender")
        other ! 123

      }
    }
  }

  object Hello {

    def props: Props = Props(classOf[Hello])
  }

  class Other extends Actor with ActorLogging {


    override def receive: Receive = {
      case num:Int => log.info(s"msg: $num, sender: $sender")
    }
  }
}
