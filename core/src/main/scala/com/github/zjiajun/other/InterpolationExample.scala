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
      args.foreach(p => if (!p.isInstanceOf[Int]) throw new RuntimeException("parameter must be Int type"))
      val date: Seq[Int] = args.map(_.asInstanceOf[Int])
      LocalDateTime.of(date(0), date(1), date(2), date(3), date(4), date(5))
    }
  }

  val (year, month, dayOfMonth, hour, minute, second)  = (2020, 5, 17, 16, 10, 20)
  val dateTime: LocalDateTime = date"${year}-$month-$dayOfMonth-$hour-$minute-$second"
  println(dateTime)

}
