package com.github.zjiajun.scalike

import java.sql.{Connection, PreparedStatement, ResultSet}

import com.typesafe.scalalogging.LazyLogging
import scalikejdbc._

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/10/6 15:21
  */
object ConnectionPoolExample extends App with LazyLogging {

  val (url, user, password) =
    ("jdbc:mysql://localhost/sandbox?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true&autoReconnectForPools=true",
     "root",
     "root")

  val settings = ConnectionPoolSettings(initialSize = 5,
                                        maxSize = 10,
                                        connectionTimeoutMillis = 3000L,
                                        validationQuery = "select 1 from dual")
  //default use Commons2ConnectionPoolFactory -> Commons2ConnectionPool, depend on common-dbcp2
  ConnectionPool.singleton(url, user, password, settings)
  ConnectionPool.add("example", url, user, password, settings)

  //从连接池中获取一个连接,使用底层原生查询,无封装
  def originQuery(): Unit = {
    val conn: Connection = ConnectionPool.borrow()
    val sql = "select * from members"
    val preparedStatement: PreparedStatement = conn.prepareStatement(sql)
    val resultSet = preparedStatement.executeQuery()
    wrapperDataAndPrint("originQuery", resultSet)
    resultSet.close()
    preparedStatement.close()
    conn.close()
  }

  private[this] def wrapperDataAndPrint(tag: String, resultSet: ResultSet): Unit =
    while (resultSet.next()) {
      val (id, name, createAt) =
        (resultSet.getLong("id"), resultSet.getString("name"), resultSet.getDate("created_at"))
      logger.info(s"$tag: $id, $name, $createAt")
    }

  //借贷模式,主要对资源贷出,省去每次都关闭的操作
  def useLoadPattern(): Unit =
    //会调用conn.close
    using(ConnectionPool.borrow()) { conn: java.sql.Connection =>
      //会调用preparedStatement.close
      using(conn.prepareStatement("select * from members where id = ?")) {
        preparedStatement: java.sql.PreparedStatement =>
          preparedStatement.setLong(1, 1L)
          // 会调用resultSet.close
          using(preparedStatement.executeQuery()) { resultSet: java.sql.ResultSet =>
            wrapperDataAndPrint("useLoadPattern", resultSet)
          }
      }
    }

  def useScalikeDb(): Unit = {
    val list = DB.readOnly { implicit session =>
      sql"select * from members".map(_.toMap()).list().apply()
    }
    logger.info(s"useScalikeDb, $list")
  }

  originQuery()
  useLoadPattern()
  useScalikeDb()

}
