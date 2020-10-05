package com.github.zjiajun.impatient.charpter14

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/8/16 22:50
  *
  */
object VariableAndConstantMathAmbiguous extends App {

  val someConstant = "OK"

  /*
    case 后跟着变量名，匹配的表达式会被赋值给变量
   */
  def mathVariable(param: String): Unit =
    param match {
      case variable if variable.charAt(0) == 'O' => println(variable)
      case _                                     => println("something is wrong!")
    }

  /*
    匹配常量, 如何知道是常量还是变量?
    规则是，变量必须以小写字母开头
    如果有小写字母开头的常量，需要用反引号
   */
  def mathConstant(param: String): Unit =
    param match {
      case `someConstant` => println("someConstant")
      case _              => println("something is wrong!")
    }

  mathVariable("OK")
  mathConstant("OK")

}
