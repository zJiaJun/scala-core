package com.github.zjiajun.impatient.charpter14

import com.typesafe.scalalogging.LazyLogging

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/10/19 22:15
  *
  * 14.14 密封类
  */
object SealedExample extends App with LazyLogging {

  sealed abstract class Amount
  case class Dollar(value: Double) extends Amount
  case class Currency(value: Double, unit: String) extends Amount

  //编译期告警,It would fail on the following input: Currency(_, _)
  def sealedMatch(amount: Amount): Unit = {
    amount match {
      case Dollar(x) => logger.info(s"sealedMatch, $x")
      case Currency(_,_) =>
    }
  }
  sealedMatch(Dollar(10))

  def sealedUncheckedMatch(amount: Amount): Unit = {
    (amount: @unchecked) match {
      case x Currency y => logger.info(s"sealedMatch, $x, $y")
    }
  }
  sealedUncheckedMatch(Currency(1.2, "EUR"))
}

