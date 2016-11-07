package com.github.zjiajun.dataguru

/**
  * Created by zhujiajun
  * 16/4/25 20:52
  */
object Week05Traning extends App {

  val seq = Seq("1","2","foo","3")

  def toInt(s: String): Option[Int] = {
    try {
      Some(Integer.parseInt(s.trim))
    } catch {
      case e: Exception => None
    }
  }

  println(seq.flatMap(toInt))

  val seqmap: Seq[Option[Int]] = seq.map(toInt)
  println(seqmap)

  val flatten: Seq[Int] = seqmap.flatten
  println(flatten)

}
