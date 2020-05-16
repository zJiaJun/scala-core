package com.github.zjiajun.impatient.charpter3

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
  * Created by zhujiajun
  * 16/2/7 15:21
  */
object ArrayDemo extends App {

  val nums = new Array[Int](5)
  nums.foreach(println)

  val s = new Array[String](1)
  s(0) = "Leon"
  s.foreach(println)

  val ss = Array("java","scala")
  println(ss(1))

  val ab = ArrayBuffer[Int]()
  ab += 1
  ab += (2,3,4,5)
  ab ++= Array(6,7)
  println(ab)
  ab.trimEnd(2) //移除最后2个元素
  println(ab)
  ab.trimStart(1) //移除头部1个元素
  println(ab)
  ab.insert(0,1)//在下标0之前插入
  println(ab)
  ab.insert(ab.length,6,7,8) //在最后插入任意多的元素
  println(ab)
  ab.remove(ab.length - 1) //移除最后个元素
  println(ab)
  ab.remove(2,2) //在下标2的位置移除2个元素
  println(ab)

  val newArray = ab.toArray //ArrayBuffer转Array
  newArray.foreach(println)
  val newBuffer = newArray.toBuffer //Array转ArrayBuffer
  println(newBuffer)

  for (i <- 0 until newArray.length) //可以用newArray.indices
    println(i + ":" + newArray(i))
  println("--------")
  for (i <- 0 until (newArray.length,2)) //每2个元素一跳
    println(i + ":" + newArray(i))
  println("--------")
  for (i <- 0 until newArray.length reverse) //从数组的尾端开始遍历
    println(i + ":" + newArray(i))
  println("--------")
  for (element <- newArray) //不需要用下标,可以直接访问元素,和java的"增强"for循环相似
    println(element)

  val a = ArrayBuffer(1,2,3,4,5,6)
  val result = for (i <- a) yield i * 2 //产生新的ArrayBuffer,不会修改原有ArrayBuffer
  println(a)
  println(result)

  val result2 = for(i <- a if i % 2 == 0) yield i * 2 //守卫,for中的if条件,对偶数翻倍
  println(result2)

 val result3 = a.filter(_ % 2 == 0).map(_*2) //另一种风格,函数风格
  println(result3)

  val aa = Array[Int](3,2,1)
  println(aa.sum) //6
  println(aa.min) //1
  println(aa.max) //3
  val sort: Any = scala.util.Sorting.quickSort(aa) //可以直接对数组排序,但不能对数组缓冲排序

  val as = Array("array","hello","little")
  println(as.max) //little

  val saa = aa.sorted //不会修改原始的版本
  println(saa.mkString("|")) //1|2|3

  val sw = aa.sortWith(_ > _) //提供一个比较函数
  println(sw.mkString("<",",",">")) // <3,2,1>

  val matrix = Array.ofDim[Int](2,3) //2行 3列
  //Array[Array[Int]] = Array(Array(0, 0, 0), Array(0, 0, 0))
  matrix(0)(0) = 1 //第1行 第1列=1 Array(Array(1, 0, 0), Array(0, 0, 0))
  matrix(1)(0) = 4 //第2行 第1列=4 Array(Array(1, 0, 0), Array(4, 0, 0))
  println(matrix(0).mkString("-")) //1-0-0
  println(matrix(1).mkString("-")) //4-0-0

  //JavaConversions is deprecated, since 2.12.0, use JavaConverters
  import scala.collection.JavaConversions.bufferAsJavaList //buffer转javaList
  import scala.collection.mutable.ArrayBuffer
  val command = ArrayBuffer("ls","-all","/")
  /* 构建java原生的List
  val list = new java.util.ArrayList[String]()
  list.add("jps")
  */
  val pb = new ProcessBuilder(command) //入参是List[String],ArrayBuffer被隐式转换成java的List
  val process = pb.start()
  val stream = Source.fromInputStream(process.getInputStream,"UTF-8")
  println(stream.mkString)

  import scala.collection.JavaConversions.asScalaBuffer //返回List,自动转换成一个Buffer,注意不是ArrayBuffer
  import scala.collection.mutable.Buffer
  val buffer: Buffer[String] = pb.command() //return List[String] => Buffer[String]
  println(buffer.mkString)

}
