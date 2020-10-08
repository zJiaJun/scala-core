package com.github.zjiajun.scalike

import com.typesafe.scalalogging.LazyLogging
import scalikejdbc._
import scalikejdbc.config.{DBs, DBsWithEnv}

/**
  *@author zhujiajun
  *@version 1.0
  *@since 2020/10/7 12:53
  *
  * use scalikejdbc-config, load application.conf
  *
  * @see [[http://scalikejdbc.org/documentation/configuration.html]]
  */
object ConfigExample extends App with LazyLogging {

  // no load global settings
//  DBs.setup()
  DBs.setupAll()

  //setupAll 就是下面这些方法的封装
  DBs.loadGlobalSettings()
  val map: Map[String, String] = DBs.readAsMap()
  val JDBCSettings(url, user, password, driver) = DBs.readJDBCSettings()
  val poolSettings: ConnectionPoolSettings = DBs.readConnectionPoolSettings()

  //带环境初始化
  DBsWithEnv("dev").setup('example)

  val allMap: List[Map[String, Any]] = DB readOnly { implicit session =>
    sql"select * from members".map(_.toMap()).list().apply()
  }
  logger.info(s"all map $allMap")

  val ids: List[Long] = NamedDB('example) readOnly { implicit session =>
    sql"select id from members".map(_.long(1)).list().apply()
  }
  logger.info(s"ids $ids")

}
