package com.github.zjiajun.akka

import akka.actor.{Actor, Props, ActorRef, ActorSystem}

/**
  * Created by zhujiajun
  * 16/1/30 10:46
  */
object HelloAkka3 extends App {

  import Greeter._
  val system: ActorSystem = ActorSystem("actor-demo")
  val bob: ActorRef = system.actorOf(props("Bob","Howya doing"))
  val alice: ActorRef = system.actorOf(props("Alice","Happy to meet you"))
  bob ! Greet(alice)
  alice ! Greet(bob)
  Thread sleep 1000
  system terminate()


  object Greeter {
    case class Greet(peer: ActorRef)
    case object AskName
    case class TellName(name: String)
    def props(name: String,greeting: String) = Props(new Greeter(name,greeting))
  }

  class Greeter(myName: String,greeting: String) extends Actor {
    import Greeter._
    override def receive: Actor.Receive = {
      case Greet(ref) => ref ! AskName
      case AskName => sender ! TellName(myName)
      case TellName(name) => println(s"$greeting,$name")
    }
  }

}
