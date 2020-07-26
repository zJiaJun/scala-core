package com.github.zjiajun.other

import java.io.{Closeable, File, FileWriter}

/**
 * @author zhujiajun
 * @version 1.0
 * @since 2020/7/26 16:16
 */
object LoanPattern extends App {

  val file = new File("loan_pattern.txt")

  def basicWriterFile(file: File, op: FileWriter => Unit): Unit = {
    val writer = new FileWriter(file, true) //贷出writer
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  def curringWriterFile(file: File)(op: FileWriter => Unit): Unit = {
    val writer = new FileWriter(file, true)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  def tryWithResource[T <: Closeable, R](resource: T)(op: T => R): R = {
    try {
      op(resource)
    } finally {
      resource.close()
    }
  }

  basicWriterFile(file, writer => writer.write("basic loan\n"))

  curringWriterFile(file) {
    writer => writer.append("curring loan\n")
  }

  tryWithResource(new FileWriter(file, true)) {
    writer => writer.append("try with resource\n")
  }
}
