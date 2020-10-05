package com.github.zjiajun.impatient.charpter8

/**
  * Created by zhujiajun
  * 16/4/3 19:44
  */
class Person(name: String, age: Int) {
  override def toString: String = getClass.getName + s":{name:$name,age:$age}"
}

class Employee(name: String, age: Int, salary: Double) extends Person(name, age) {
  override def toString: String = super.toString + getClass.getName + s":{name:$name,age:$age,salary:$salary}"
}

object Test extends App {

  val employee = new Employee("e1", 30, 1234.56)
  println(employee)
}
