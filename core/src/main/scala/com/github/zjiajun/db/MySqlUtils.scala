package com.github.zjiajun.db

import java.sql._

/**
  * Created by zhujiajun
  * 16/2/14 21:28
  */
object MySqlUtils extends App {

  val driverClass = "com.mysql.jdbc.Driver"
  val jdbcUrl = "jdbc:mysql://127.0.0.1:3306/lbs"
  val user = "root"
  val password = "12345"

  try {
    Class.forName(driverClass)
  } catch {
    case e: ClassNotFoundException => throw e
    case e: Exception => throw e
  }

  @throws(classOf[SQLException])
  def getConnection: Connection = DriverManager.getConnection(jdbcUrl,user,password)

  @throws(classOf[SQLException])
  def doTransaction(transactions: Set[String]): Unit = {
    val connection = getConnection
    connection.setAutoCommit(false)
    transactions.foreach(connection.createStatement().execute)
    connection.commit()
    connection.close()
  }

  val connection: Connection = getConnection
  val sql = "SELECT name FROM l_lbs WHERE type = 003"
  private val resultSet: ResultSet = connection.createStatement().executeQuery(sql)
  while (resultSet.next()) {
    val name: String = resultSet.getString(1)
    println(name)
  }
}
