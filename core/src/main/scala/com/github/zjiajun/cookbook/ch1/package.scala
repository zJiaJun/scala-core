package com.github.zjiajun.cookbook

/**
  * @author zhujiajun
  * @since 2017/9/12
  */
package object ch1 {

  implicit class StringDecrement(s: String) {

    def decrement = s.map(c => (c - 1).toChar)

  }

}
