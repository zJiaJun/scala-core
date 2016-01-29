package scala.charpter13

import scala.collection.mutable

/**
 * Created by zhujiajun
 * 15/7/13 10:50
 */
object LinkedListTest extends App {

  val lst = mutable.LinkedList(-2,-1,0,1,2,3)
  var cur = lst
  while (cur != Nil) {
    if (cur.elem < 0) cur.elem = 0
    cur = cur.next
  }

  println(cur)
  println(lst)

}
