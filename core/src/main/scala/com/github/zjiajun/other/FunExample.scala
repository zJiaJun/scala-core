package com.github.zjiajun.other

/**
  * @author zhujiajun
  * @since 2016/11/8
  */
object FunExample extends App {

  def newThread(fun: ()=> Unit): Thread = {
    new Thread {
      override def run() = fun()
    }
  }

  def startNewThread(fun: => Unit): Unit = {
    try {
      println("begin new thread")
      newThread(()=>fun).start()
      println("finished thread work")
    } catch {
      case e: Throwable => e.printStackTrace()
    }
  }

  def doWorkInThread() = {
    println("doWork")
    Thread.sleep(1000)
    println("finished")
  }

  startNewThread(doWorkInThread())





}
