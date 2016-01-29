package scala


/**
 * Created by zhujiajun
 * 15/5/26 17:03
 */


object HelloWorld {

  def main(args: Array[String]) : Unit = {
    cover()
    for (i <- 0 until array.length) println(array(i))
  }


  def first(x:Int)  = {
    val r = 1
    val l = 2
    var x  = r + l
    x = x * 10
    x
  }

  def fac(n:Int = 3) : Int = if (n<=0) 1 else n * fac(n-1)

  def is(i:Int) = if (i>0) 1 else if (i<0) -1 else 0

  def countdown(n:Int): Unit = {
    for (i <- 0.to(n).reverse) println(i)
  }

  def str(s:String) : Long = {
    var a : Long = 1
    for (i <- s)  a=a*i;a
  }

  val array = Array[Int](1,2,3,4)
  var temp  = 0
  def cover(): Unit = {
    for (i <- 0 until array.length)
      if (i % 2 == 0 && i < array.length - 1) {
        temp = array(i)
        array(i) = array(i + 1)
      } else if (i%2==0 && i==array.length-1) {
        array(i)
      } else {
        array(i) = temp
      }
  }


}
