package com.github.zjiajun.akka

import akka.actor.{Props, ActorSystem}
import com.typesafe.config.{ConfigFactory, Config}

/**
  * Created by zhujiajun
  * 16/2/2 21:50
  */
object AkkaClientApplication extends App {

  val config: Config = ConfigFactory.load().getConfig("MyRemoteClientSideActor")
  val system = ActorSystem("client-system",config)
  val log = system.log
  val clientActorRef = system.actorOf(Props[ClientActor],"client-actor")



}
