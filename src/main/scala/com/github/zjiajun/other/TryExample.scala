package com.github.zjiajun.other

import scala.util.{Failure, Success, Try}

/**
  * Created by zhujiajun
  * 16/2/8 16:00
  */
object TryExample extends App {

  def getDealId(v : Any): Int = {
    var dealId: Int = 0
    Try {
      dealId = if (null == v) 0 else Integer.parseInt(v.toString)
    } match {
      case Success(result) =>
      case Failure(e) =>  dealId = 0
    }
    dealId
  }

  println(getDealId("12_31"))

}
