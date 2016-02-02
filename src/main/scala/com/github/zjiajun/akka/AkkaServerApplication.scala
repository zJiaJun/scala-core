package com.github.zjiajun.akka

import akka.actor.{Props, ActorSystem}
import com.typesafe.config.{ConfigFactory, Config}

/**
  * Created by zhujiajun
  * 16/2/2 21:22
  */
object AkkaServerApplication extends App {

  val config: Config = ConfigFactory.load().getConfig("MyRemoteServerSideActor")
  val system = ActorSystem("remote-system",config)
  val log = system.log
  log.info("Remote server actor started: " + system)
  system.actorOf(Props[RemoteActor],"remoteActor")
}
