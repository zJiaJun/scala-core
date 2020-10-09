package com.github.zjiajun.other

import java.time.LocalDateTime

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/5/17 15:32
  *
  * 字符串插值 s f raw.
  * 自定义date插值
  */
object InterpolationExample extends App {

  val height = 1.9d
  val name = "leon"
  println(f"$name%s is $height%2.2f")

  //换行
  println(s"a\nb")
  //不换行,输出a\nb
  println(raw"a\nb")

  //自定义插值 date
  implicit class DateInterpolation(val sc: StringContext) extends AnyVal {
    def date(args: Any*): LocalDateTime = {
      if (args.size != 6) throw new RuntimeException("parameter must be 6")
      val dateSeq: Seq[Int] = args.map {
        case d: Int => d.toInt
        case _      => throw new RuntimeException("parameter must be Int type")
      }
      LocalDateTime.of(dateSeq(0), dateSeq(1), dateSeq(2), dateSeq(3), dateSeq(4), dateSeq(5))
    }
  }

  val (year, month, dayOfMonth, hour, minute, second) = (2020, 5, 17, 16, 10, 20)
  val dateTime: LocalDateTime = date"${year}-$month-$dayOfMonth-$hour-$minute-$second"
  println(dateTime)

}
