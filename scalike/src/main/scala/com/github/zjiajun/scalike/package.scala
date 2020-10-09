package com.github.zjiajun

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import scalikejdbc.{ConnectionPool, DataSourceConnectionPool}

/**
 * @author zhujiajun
 * @version 1.0
 * @since 2020/10/8 14:13
  */
package object scalike {

  val (url, user, password) =
    ("jdbc:mysql://localhost/sandbox?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true&autoReconnectForPools=true",
     "root",
     "root")

  def initHikariConnectionPool(): Unit = {
    val hikariConfig = new HikariConfig()
    hikariConfig.setJdbcUrl(url)
    hikariConfig.setUsername(user)
    hikariConfig.setPassword(password)
    hikariConfig.setMaximumPoolSize(5)
    hikariConfig.setConnectionTimeout(3000L)
    hikariConfig.setPoolName("scalikejdbc-hikari-pool")
    val hikariDataSource = new HikariDataSource(hikariConfig)
    ConnectionPool.singleton(new DataSourceConnectionPool(hikariDataSource))
  }

}
