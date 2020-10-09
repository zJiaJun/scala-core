package com.github.zjiajun.scalike

import com.typesafe.scalalogging.LazyLogging
import com.github.zjiajun.scalike.entity._
import scalikejdbc._

/**
 * @author zhujiajun
 * @version 1.0
 * @since 2020/10/9 19:51
 *
 * @see [[http://scalikejdbc.org/documentation/auto-session.html]]
*/
object AutoSessionExample extends App with LazyLogging {

  initHikariConnectionPool()
  DB readOnly {implicit session =>
    findById(1L) // 适用session ActiveSession
  }
  findById(2L) //使用默认session AutoSession 或者 ReadOnlyAutoSession

  def findById(id: Long)(implicit session: DBSession = ReadOnlyAutoSession): Option[Member] = {
    logger.info(s"findById using session, $session")
      val memberOpt = sql"select * from members where id = $id"
        .map(rs => Member(rs.long(1), rs.string(2), rs.date(3)))
        .single().apply()
    logger.info(s"findById, $memberOpt")
    memberOpt
  }


}
