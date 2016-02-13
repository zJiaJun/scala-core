package com.github.zjiajun.redis

import java.util

import com.typesafe.config.ConfigFactory
import redis.clients.jedis.{Jedis, JedisShardInfo}

import scala.collection.{mutable, JavaConversions}

/**
  * Created by zhujiajun
  * 16/2/4 21:45
  */
object RedisMain extends App {

  implicit private val config = ConfigFactory.load
  val REDIS_HOST = config.getString("redis.host")
  val REDIS_PORT = config.getInt("redis.port")

  private val jedisShardInfo: JedisShardInfo = new JedisShardInfo(REDIS_HOST,REDIS_PORT)
  println(jedisShardInfo)

  private val jedis: Jedis = new Jedis(jedisShardInfo)

  jedis.flushDB

  val stringKey = "stringKey"
  jedis.set(stringKey,"stringValue")
  private val value: String = jedis.get(stringKey)
  println("key : " + stringKey + " value : " + value)

  val listThread = createListThread

  def createListThread: Thread = {
    new Thread("List-Thread") {
      override def run(): Unit = {
        for (i <- 0 to 100) {
          jedis.lpush("listKey",i.toString)
        }
      }
    }
  }

  listThread.start()
  listThread.join()

  for (i <- 0 to 50) println(jedis.rpop("listKey"))

  jedis.hset("h:Key","h:Field","fieldValue")

  private val hgetAll: util.Map[String, String] = jedis.hgetAll("h:Key")
  println(hgetAll)
  private val scalaMap: mutable.Map[String, String] = JavaConversions.mapAsScalaMap(hgetAll)
  println(scalaMap)

}
