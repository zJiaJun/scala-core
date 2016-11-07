package com.github.zjiajun.dataguru

/**
  * Created by zhujiajun
  * 16/5/8 11:17
  *
  * 继续第五周学校管理的例子,将管理人群扩展到老师,老师不会同时是学生,允许按照老师的年龄、学生
  * 的成绩对查询的结果进行排序
  */
object Week07 extends App {


  abstract class Person {

    val t_flag = "T"
    val s_flag = "S"
    /*
    姓名 -> (年龄,标示,年级,分数)
     */
    val map = collection.mutable.Map[String,(Int, String, Int, Int)](
      "Teacher_1" -> (30, t_flag, 0, 0),
      "Teacher_2" -> (35, t_flag, 0 ,0),
      "Teacher_3" -> (40, t_flag, 0 ,0),
      "Student_1" -> (18, s_flag, 3, 100),
      "Student_2" -> (19, s_flag, 3, 90),
      "Student_3" -> (20, s_flag, 3, 80),
      "Student_4" -> (21, s_flag, 3, 70)
    )

    def printMap() = {
      println("Print Map Begin")
      map.foreach(println)
      println("Print Map End")
    }
  }

  trait Student extends Person {

    def add(name: String,age: Int,grade: Int,score: Int) = map += (name -> (age, s_flag, grade ,score))

    def update(name: String,age: Int,grade: Int,score: Int) = map(name) = (age, s_flag, grade, score)

    def sortByScore(): List[(String, (Int, String, Int, Int))] = map.filter(m => m._2._2 == s_flag).toList.sortBy(l => l._2._4)

  }

  trait Teacher extends Person {

    def add(name: String,age: Int) = map += (name -> (age, t_flag, 0, 0))

    def update(name: String,age: Int) = map(name) = (age, t_flag, 0, 0)

    def sortByAge(): List[(String,(Int, String, Int, Int))] = map.filter(m => m._2._2 == t_flag).toList.sortBy(l => l._2._1)
  }

  val p = new Person with Student with Teacher
  p.add("Student_5",22,3,60)
  p.update("Student_3",22,3,50)
  p.printMap()

  p.add("Teacher_4",50)
  p.update("Teacher_3",55)
  println("SortByScore " + p.sortByScore())
  println("SortByAge " + p.sortByAge())

}
