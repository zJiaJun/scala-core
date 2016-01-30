package com.github.zjiajun.akka

import akka.actor.{Props, ActorSystem}

/**
  * Created by zhujiajun
  * 16/1/30 17:11
  */
object LocalClient extends App {

  val system = ActorSystem("local-system")
  println(system)
  val localActorRef = system.actorOf(Props(new LocalActor),"local-actor")
  println(localActorRef)

  localActorRef ! Start
  localActorRef ! Heartbeat("100200",88)

  localActorRef ! Packet("100201",System.currentTimeMillis(),"content")
  localActorRef ! Stop
  system terminate()
}
