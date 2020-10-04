package com.github.zjiajun.scalikejdbc

import com.typesafe.scalalogging.LazyLogging
import scalikejdbc._

/**
 * @author zhujiajun
 * @version 1.0
 * @since 2020/10/4 23:56
 */
object Demo extends App with LazyLogging {

  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton(
    "jdbc:mysql://localhost/performance_schema?characterEncoding=utf-8",
  "root", "root")

  implicit val session = AutoSession

  private val maps: List[Map[String, Any]] = sql"select * from accounts".map(_.toMap()).list().apply()

  logger.info(maps.toString())
}
