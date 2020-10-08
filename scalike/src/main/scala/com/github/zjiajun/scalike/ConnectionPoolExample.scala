package com.github.zjiajun.scalike

import java.sql.{Connection, PreparedStatement, ResultSet}

import com.typesafe.scalalogging.LazyLogging
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import scalikejdbc._

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/10/6 15:21
  *
  * @see [[http://scalikejdbc.org/documentation/connection-pool.html]]
  */
object ConnectionPoolExample extends App with LazyLogging {

  initConnectionPool()
  usingOriginQuery()
  usingLoadPattern()
  usingScalikeDb()
  usingHikariConnectionPool()

  def initConnectionPool(): Unit = {
    val settings = ConnectionPoolSettings(initialSize = 5,
                                          maxSize = 10,
                                          connectionTimeoutMillis = 3000L,
                                          validationQuery = "select 1 from dual")
    //default use Commons2ConnectionPoolFactory -> Commons2ConnectionPool, depend on common-dbcp2
    ConnectionPool.singleton(url, user, password, settings)
    //other connection pool
//    ConnectionPool.add("example", url, user, password, settings)
  }

  //从连接池中获取一个连接,使用底层原生查询,无封装
  def usingOriginQuery(): Unit = {
    val conn: Connection = ConnectionPool.borrow()
    val sql = "select * from members"
    val preparedStatement: PreparedStatement = conn.prepareStatement(sql)
    val resultSet = preparedStatement.executeQuery()
    wrapperDataAndPrint("usingOriginQuery", resultSet)
    resultSet.close()
    preparedStatement.close()
    conn.close()
  }

  private[this] def wrapperDataAndPrint(tag: String, resultSet: ResultSet): Unit =
    while (resultSet.next()) {
      val (id, name, createdAt) =
        (resultSet.getLong("id"), resultSet.getString("name"), resultSet.getDate("created_at"))
      logger.info(s"$tag: $id, $name, $createdAt")
    }

  //借贷模式,主要对资源贷出,省去每次关闭的操作,see trait LoanPattern
  def usingLoadPattern(): Unit =
    //会调用conn.close
    using(ConnectionPool.borrow()) { conn: Connection =>
      //会调用preparedStatement.close
      using(conn.prepareStatement("select * from members where id = ?")) { preparedStatement: PreparedStatement =>
        preparedStatement.setLong(1, 1L)
        // 会调用resultSet.close
        using(preparedStatement.executeQuery()) { resultSet: ResultSet =>
          wrapperDataAndPrint("usingLoadPattern", resultSet)
        }
      }
    }

  //DB.xxx的方法, 源码就是useLoadPattern的抽象
  def usingScalikeDb(): Unit = {
    val list = DB.readOnly { implicit session =>
      sql"select * from members".map(_.toMap()).list().apply()
    }
    logger.info(s"usingScalikeDb, $list")
  }

  /*

    切换连接池方式,默认使用的是commons-dbcp2, 连接池工厂Commons2ConnectionPoolFactory
    第一种
    1.实现ConnectionPoolFactory 特质， 实现ConnectionPool 抽象类
    2.注册poolFactory, 名称是 "yourConnectionPoolName"
    3.连接池settings设置connectionPoolFactoryName属性, 即上部注册的名称

    第二种
    1.实现ConnectionPoolFactory 特质， 实现ConnectionPool 抽象类(和第一种一样)
    2.声明隐式ConnectionPoolFactory
    3.初始化时,会自动传入到singleton或add方法中

   */
  def switchConnectionPool(): Unit = {
    ConnectionPoolFactoryRepository.add("yourConnectionPoolName", Commons2ConnectionPoolFactory)
    ConnectionPool.singleton(url,
                             user,
                             password,
                             ConnectionPoolSettings(connectionPoolFactoryName = "yourConnectionPoolName"))

    //or

    /*
    implicit val connectionPoolFactory: ConnectionPoolFactory = Commons2ConnectionPoolFactory
    打开会报编译错误
    forward reference extends over definition of value connectionPoolFactory one error found
    大概意思就是, 定义在后, 使用在前, 将注释的语句移到第一个singleton之前就ok.
    这里关注两种方式切换连接池实现,实际不会编写这样代码
     */
    ConnectionPool.singleton(url, user, password)(Commons2ConnectionPoolFactory)
  }

  def usingHikariConnectionPool(): Unit = {
    val hikariConfig = new HikariConfig()
    hikariConfig.setJdbcUrl(url)
    hikariConfig.setUsername(user)
    hikariConfig.setPassword(password)
    hikariConfig.setMaximumPoolSize(10)
    hikariConfig.setConnectionTimeout(3000L)
    hikariConfig.setPoolName("scalikejdbc-hikari-pool")
    val hikariDataSource = new HikariDataSource(hikariConfig)
    ConnectionPool.add("hikari", new DataSourceConnectionPool(hikariDataSource))

    val list = NamedDB("hikari").readOnly { implicit session =>
      sql"select * from members".map(_.toMap()).list().apply()
    }
    logger.info(s"usingHikariConnectionPool, $list")
  }

}
