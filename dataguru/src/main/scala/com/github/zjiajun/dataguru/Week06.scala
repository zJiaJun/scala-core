package com.github.zjiajun.dataguru

/**
  * Created by zhujiajun
  * 16/4/30 22:08
  *
  *  实现分数的计算：
  *
  *  实现对分数的加减乘除计算
  *  实现创建的分数对象的约分
  *  实现分数对象和整数对象的加减乘除操作
  */
object Week06 extends App {

  class Rational(val numerator: Int, val denominator: Int) {
    override def toString: String = numerator + "/" + denominator

    def +(operand: Rational): Rational = {
      val n = operand.numerator
      val d = operand.denominator
      val newNumerator = numerator * d + denominator * n
      val newDenominator = denominator * d
      new Rational(newNumerator, newDenominator)
    }

    def -(operand: Rational): Rational = {
      val n = operand.numerator
      val d = operand.denominator
      val newNumerator = numerator * d - denominator * n
      val newDenominator = denominator * d
      new Rational(newNumerator, newDenominator)
    }

    def *(operand: Rational): Rational = {
      val n = operand.numerator
      val d = operand.denominator
      val newNumerator = numerator * n
      val newDenominator = denominator * d
      new Rational(newNumerator, newDenominator)
    }

    def /(operand: Rational): Rational = {
      val n = operand.numerator
      val d = operand.denominator
      val newNumerator = numerator * d
      val newDenominator = denominator * n
      new Rational(newNumerator, newDenominator)
    }

    def gcd(numerator: Int,denominator: Int): Int = if (denominator==0) numerator else gcd(denominator,numerator % denominator)

    def +(n: Int): Rational = this + new Rational(n * denominator,denominator)

    def -(n: Int): Rational = this - new Rational(n * denominator,denominator)

    def *(n: Int): Rational = this * new Rational(n * denominator,denominator)

    def /(n: Int): Rational = this / new Rational(n * denominator,denominator)

  }

  //分数对象的加减乘除
  val r_1 = new Rational(3,6)
  val r_2 = new Rational(1,2)
  println(r_1 + r_2)
  println(r_1 - r_2)
  println(r_1 * r_2)
  println(r_1 / r_2)

  //约分
  val n = r_1.gcd(3,3)
  println(r_1.numerator / n)
  println(r_1.denominator / n)

  //分数对象和整数对象加减乘除
  println(r_1 + 5)
  println(r_1 - 5)
  println(r_1 * 5)
  println(r_1 / 5)





}
