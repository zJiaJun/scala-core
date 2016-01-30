package com.github.zjiajun.akka

import akka.actor.{Props, ActorRef, ActorSystem, Actor}

/**
  * Created by zhujiajun
  * 16/1/30 08:55
  */
object HelloAkka2 extends App {

  case class Greeting(greet: String)
  case class Greet(name:String)

  val system: ActorSystem = ActorSystem("actor-demo")
  val hello2: ActorRef = system.actorOf(Props[Hello2],"root")
  hello2 ! Greeting("Leon")
  hello2 ! Greet("zjiajun")
  hello2 ! Greeting("Tom")
  hello2 ! Greeting("Bob")
  hello2 ! Greet("Alice")
  hello2 ! Greet("Marry")
  Thread sleep 1000
  system terminate

  class Hello2 extends Actor {
    var greeting = ""
    override def receive: Actor.Receive = {
      case Greeting(greet) => greeting = greet
      case Greet(name) => println(s"$greeting $name")
    }
  }

}
