package com.github.zjiajun.collections

/**
  * Created by zhujiajun
  * 16/2/12 21:41
  *
  * Tuple(元组)迭代,继承了Product特质,使用productIterator进行迭代
  */
object TupleIterator extends App {

  val tupleValue = (1, 2, 3, 4, 5, 6)

  def tupleIterator(tuple: Product): Unit = tuple.productIterator.foreach(println)

  tupleIterator(tupleValue)

}
