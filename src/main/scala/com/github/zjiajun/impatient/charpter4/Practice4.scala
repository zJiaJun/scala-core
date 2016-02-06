package com.github.zjiajun.impatient.charpter4

/**
 * Created by zhujiajun
 * 15/6/11 14:11
 */
object Practice4 extends App {

    val map = Map[String,Double]("T1"->10,"T2"->100,"T3"->487)
    val newMap = practiceOne(map)
    println(newMap)
    practiceSeven()


  def practiceOne(map : Map[String,Double]) = {
    for ((k,v)<-map) yield (k,v*0.9)
  }

  def practiceSeven() = {
    import scala.collection.JavaConversions.propertiesAsScalaMap
    val props: scala.collection.Map[String,String] = System.getProperties
    val keys = props.keySet
    println(keys)
    val keyLengths = for( key <- keys ) yield key.length
    println(keyLengths)
    val maxKeyLength = keyLengths.max
    println(maxKeyLength)
    for(key <- keys) {
      print(key)
      print(" " * (maxKeyLength - key.length))
      print(" | ")
      println(props(key))
    }

  }
}
