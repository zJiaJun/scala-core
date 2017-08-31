package com.github.zjiajun.cookbook.ch1

import com.alibaba.fastjson.{JSON, JSONObject}


/**
  * @author zhujiajun
  * @since 2017/8/29
  */
object VariablesIntoStrings1_4 extends App {


  val name = "Fred"

  val age = 33

  val weight = 200.00

  println(s"$name is $age years old, and weights $weight pounds.")

  println(s"Age next year: ${age + 1}")

  println(s"You are 33 years old: ${age == 33}")

  case class Student(name: String, score: Int)

  val hannah = Student("Hannah", 95)

  println(s"${hannah.name} has a score of ${hannah.score}")

  //error
  println(s"$hannah.name has a score of $hannah.score")

  //s is a method

  println(f"$name is $age years old, and weights $weight%.2f pounds.")

  println(f"$name is $age years old, and weights $weight%.0f pounds.")


  println(s"foo\nbar\ttest")

  println(raw"foo\nbar\ttest")

  implicit class JsonHelper(val sc: StringContext) extends AnyVal {
    def json(args: Any*): JSONObject = {
      val strings = sc.parts.iterator
      val expressions = args.iterator
      val buf = new StringBuffer(strings.next)
      while (strings.hasNext) {
        buf append expressions.next
        buf append strings.next
      }
      JSON.parseObject(buf.toString)
    }
  }

  def giveMeSomeJson(x: JSONObject): Unit = {
    println(x.getString("name"))
    println(x.getIntValue("age"))
  }

  giveMeSomeJson(json"""{"name": "$name", "age": $age}""")

}



