package com.github.zjiajun.scalike

import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{ConnectionPool, ConnectionPoolSettings}

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

}
