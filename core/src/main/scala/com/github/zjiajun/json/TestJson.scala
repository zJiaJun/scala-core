package com.github.zjiajun.json

import org.json4s.{DefaultFormats, Formats}
import org.json4s.jackson.JsonMethods

/**
  * Created by zhujiajun
  * 16/3/11 14:51
  */
object TestJson extends App {

  implicit def json4sFormats: Formats = DefaultFormats

//  val json = "{\"id\": \"123\",\"pmp\": {\"private_auction\":100,\"deals\": [{\"id\": 123456,\"bidfloor\": 6123}]}}"
  val json = "{\"id\": \"123\",\"pmp\": {\"deals\": [{\"id\": \"123456\",\"bidfloor\": 6123}]}}"

  val jsonObj = JsonMethods.parse(json)
  println(jsonObj.toString)
  val value = jsonObj.extract[Model]
  println(value.toString)
  println(value.pmp.get.deals.head.head.id.toString)

}
