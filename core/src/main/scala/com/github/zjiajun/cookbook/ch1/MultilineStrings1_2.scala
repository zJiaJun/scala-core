package com.github.zjiajun.cookbook.ch1

/**
  * @author zhujiajun
  * @since 2017/8/28
  * 1.2
  */
object MultilineStrings1_2 extends App {

  val m1 =
    """This is a
    multiline
    String"""

  println(m1)

  val m2 =
    """This is a
      |multiline
      |String
    """.stripMargin

  println(m2)

  val m3 =
    """This is a
      #multiline
      #String
    """.stripMargin('#')

  println(m3)

  val m4 =
    """Four score and
      |seven years ago
      |our fathers
    """.stripMargin.replaceAll("\n", " ")

  println(m4)

  val m5 =
    """This is know a
      |"multiline" string
      |or 'heredoc' syntax
    """.stripMargin.replaceAll("\n", " ")

  println(m5)
}
