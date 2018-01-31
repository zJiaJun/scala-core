package com.github.zjiajun.akka

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import akka.util.Timeout
import org.scalatest.{FunSpecLike, Matchers}

/**
  * @author zhujiajun
  * @since 2018/1/31
  */
class HelloActorTest extends FunSpecLike with Matchers {

  implicit val system = ActorSystem()
  val timeout = Timeout(5, TimeUnit.SECONDS)

  describe("helloActor") {
      it("should") {
        val actorRef = TestActorRef(new ClientActor)
        actorRef ! Stop
        val clientActor = actorRef.underlyingActor
        println(clientActor)
      }
  }

}
