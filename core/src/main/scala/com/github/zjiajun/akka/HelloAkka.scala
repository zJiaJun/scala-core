package com.github.zjiajun.akka

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.pattern.ask

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by zhujiajun
  * 16/1/29 21:45
  */
object HelloAkka extends App {

  val system: ActorSystem = ActorSystem("actor-demo")
  val hello: ActorRef = system.actorOf(Hello.props,"hello")
  hello ! "zjiajun"


  val other = system.actorOf(Props[Other], "other")
  implicit val timeout = akka.util.Timeout(1 seconds)
  val future =  other ? 123

  import concurrent.ExecutionContext.Implicits.global
  future.onComplete({
    case Success(s) => println(s)
    case Failure(f) => println(f)
  })

//  val result = Await.result(future.mapTo[String], 2 seconds)
//  println(result)


  Thread sleep 5000
  system terminate()


  class Hello extends Actor with ActorLogging {

    override def receive: Receive = {
      case name: String => {
        log.info(s"msg: $name, sender: $sender")
      }
    }
  }

  object Hello {

    def props: Props = Props(classOf[Hello])
  }

  class Other extends Actor with ActorLogging {


    override def receive: Receive = {
      case num:Int => {
        log.info(s"msg: $num, sender: $sender")
        sender !  "success"
      }

    }
  }
}
