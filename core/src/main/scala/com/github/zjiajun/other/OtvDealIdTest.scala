package com.github.zjiajun.other

/**
  * Created by zhujiajun
  * 16/3/8 21:00
  *
  * 根据es查询出的值按规则取cKey生成list
  * 生成dealId -> cKey List数据
  * 取这两部分的交集
  */
object OtvDealIdTest extends App {

  val esValue = List.newBuilder[String]
  esValue += "2EE9EE8998FE266F6B225EC84E88311C"
  esValue += ("AC4E02CC855F38886B225EC84E88311C","E374B8265713C4EC6B225EC84E88311C")

  val campaignKeyList: List[String] = esValue.result().map(_.substring(0,16))
  println(campaignKeyList)

  val dealIdMap = Map.newBuilder[String,List[String]]
  val c100 = List[String]("2EE9EE8998FE266F","AC4E02CC855F3888")
  val c200 = List[String]("2EE9EE8998FE266F","E374B8265713C4EC")
  dealIdMap += ("100" -> c100,"200" -> c200)

  val cMap = dealIdMap.result()

  val union100 = cMap("100").toSet & campaignKeyList.toSet
  val union200 = cMap("200").toSet & campaignKeyList.toSet
  println(union100)
  println(union200)

  val aId = "6B225EC84E88311C"

  val end1 = union100.toList.map(_ + aId)
  val end2 = union200.toList.map(_ + aId)
  println(end1)
  println(end2)


  val set = new java.util.HashSet[String]()
  set.add("201603171839")

  val dealId = "201603171839"
  println(set.contains(dealId))

}
