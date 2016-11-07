package com.github.zjiajun.impatient.charpter10

/**
 * Created by zhujiajun
 * 15/7/10 18:01
 */
trait TraitClazzChild extends TraitClazz {

  override def write(msg: String): Unit = {
    println(msg)
  }
}
