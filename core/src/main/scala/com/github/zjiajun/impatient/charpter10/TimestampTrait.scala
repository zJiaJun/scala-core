package com.github.zjiajun.impatient.charpter10

/**
  * @author zhujiajun
  * @since 2017/1/15
  */
trait TimestampTrait extends Logged {

  override def log(msg: String): Unit = super.log(new java.util.Date() + " " + msg)

}
trait ShortTrait extends Logged {
  val maxLength = 10

  override def log(msg: String): Unit =
    super.log(if (msg.length <= maxLength) msg else msg.substring(0, maxLength - 3) + "...")

}

object TestMain extends App {

  //Short 先执行，然后super.log调用Timestamp
  val s1 = new Saving with ConsoleLogged with TimestampTrait with ShortTrait
  //Timestamp 先执行，然后super.log调用short
  val s2 = new Saving with ConsoleLogged with ShortTrait with TimestampTrait

  s1.save(1)
  s2.save(2)

}