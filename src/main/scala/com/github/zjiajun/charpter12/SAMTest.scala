package scala.charpter12

import com.github.zjiajun.{TInterface, TClass}


/**
 * Created by zhujiajun
 * 15/7/15 15:14
 */
/*
 * SAM = single abstract method
 */
object SAMTest extends App {

  val t = new TClass

  t.addInterface(new TInterface {
    override def test(fuck: String): Unit = println(fuck + "_123")
  })

  t.addInterface((fun : String) => println(fun + "_456"))
  implicit def functionToTInterface(fun :(String) => Unit) : TInterface = {
    new TInterface {
      override def test(fuck: String): Unit = fun(fuck)//有参数调用 fun是function
    }
  }

  t.addInterface(() => println("456"))
  implicit def functionToTInterface(fun : () => Unit) :TInterface = {
    new TInterface {
      override def test(fuck: String): Unit = fun() //无参
    }
  }


}
