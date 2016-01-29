package scala.charpter5

/**
 * Created by zhujiajun
 * 15/6/12 15:20
 */

class Time (val hours:Int,val minutes:Int) {

  def before(other: Time): Boolean = {
    if (hours < other.hours) true
    else if (hours == other.hours) minutes < other.minutes
    else false
  }

}

class Practice5(a : String)  {

  def this(name : String,age : Int) {
    this("aa")
    println(name)
    println(age)
  }

}

object Practice5 extends App{

  def apply(x : Int) = {
    new Practice5("name",x)
  }

  val time1 = new Time(2,1)
  val time2 = new Time(2,2)
  val b = time1.before(time2)
  println(b)

  val l = new Practice5("a") {
    def aaa = "123"
  }

}
