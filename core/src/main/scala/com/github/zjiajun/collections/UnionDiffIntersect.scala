package com.github.zjiajun.collections

/**
  * Created by zhujiajun
  * 16/2/12 22:02
  *
  * 集合的交集,差集,并集操作
  */
object UnionDiffIntersect extends App {

  val set_1 = Set(1,2,3)
  val set_2 = Set(2,3,4)

  //交集
  println(set_1 & set_2) // & 同等于intersect
  println(set_1 intersect set_2)

  //差集
  println(set_1 -- set_2)
  println(set_1 &~ set_2)
  println(set_1 diff set_2)

  //并集
  println(set_1 ++ set_2)
  println(set_1 | set_2)
  println(set_1 union set_2)

  //元素加和减,用tuple封装
  println(set_1 + (4,5))
  println(set_2 - (3,4))

  //对于非Set集合，在做交集、并集、差集时必须转换为Set，否则元素不去重没有意义。
  val list_1 = List(1,2,3)
  val list_2 = List(2,3,4)
  println(list_1.toSet & list_2.toSet)

  val list_3 = List(1,1,2,2,3,3)
  println(list_3.distinct)

}
