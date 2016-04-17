package com.github.zjiajun.dataguru

/**
  * Created by zhujiajun
  * 16/4/17 13:34
  */
object Week04 extends App {


  type Set = Int => Boolean

  def contains(s: Set, elem: Int) : Boolean = s(elem)

  def singletonSet(elem: Int) : Set = {
    x: Int => x == elem
  }

  def union(s: Set, t: Set): Set = {
    x: Int => s(x) | t(x)
  }

  def intersect(s: Set, t: Set): Set = {
    x: Int => s(x) & t(x)

  }

  def diff(s: Set, t: Set): Set = {
    x: Int => s(x) & !t(x)
  }

  def filter(s: Set, p: Int => Boolean): Set = {
    x: Int => s(x) && p(x)
  }

  def forall(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (a == java.lang.Integer.MIN_VALUE) true
      else if( s(a) && !p(a)) false
      else iter(a-1)
    }

    iter(java.lang.Integer.MAX_VALUE)
  }

  println(contains(singletonSet(3),3))

}
