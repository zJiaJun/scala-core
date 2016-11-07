package com.github.zjiajun.dataguru

import scala.collection.mutable

/**
  * Created by zhujiajun
  * 16/4/20 22:03
  *
  * 写一个存储程序，把学生的名字和所在年级保存起来（不需要落盘）
  * 你可以对学生添加、删除，修改年级。例如school.add("Jim", 2)等等，并对学生进行查询.
  * 例如输入名字，输出学生名字和所在年级，输入年级，输出该年级所有学生
  */
object Week05 extends App {


  val school = new mutable.HashMap[String,Int]

  school += ("Tom" -> 1, "Jim" -> 2, "Leon" -> 3, "Marry" -> 5)

  println(school)

  school -= "Tom"

  println(school)

  school("Leon") = 5

  println(school)

  println(school.getOrElse("Leon",3))

  def getStudByGrade(grade: Int) = {
    school.keys.foreach { k =>
      if (school(k) == grade)
        println(k)
    }
  }

  getStudByGrade(5)


}
