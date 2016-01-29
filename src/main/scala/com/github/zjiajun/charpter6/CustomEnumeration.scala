package scala.charpter6

/**
 * Created by zhujiajun
 * 15/6/15 14:50
 */
object CustomEnumeration extends Enumeration with App {

  val Red = Value
  val Yellow = Value("yellow")
  val Green = Value(2,"green")

  println(CustomEnumeration.Red.id + ":" + CustomEnumeration.Red.toString)
  println(CustomEnumeration.Yellow.id + ":" + CustomEnumeration.Yellow.toString)
  println(CustomEnumeration.Green.id + ":" + CustomEnumeration.Green.toString)
  println(CustomEnumeration(0))
  println(CustomEnumeration.withName("green"))

}
