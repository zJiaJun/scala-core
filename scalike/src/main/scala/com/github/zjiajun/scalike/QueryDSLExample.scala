package com.github.zjiajun.scalike

import com.github.zjiajun.scalike.entity.Member
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc._

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/10/10 22:55
  *
  * @see [[http://scalikejdbc.org/documentation/query-dsl.html]]
  */
object QueryDSLExample extends App with LazyLogging {

  initHikariConnectionPool()
  val m = Member.syntax("m")
//  usingSimpleDSL()
//  usingWithRoundBracket()
//  usingAppend()
//  usingDynamicQuery(Option.empty)
  usingDynamicQuery(Option("Alice"), Option.empty)

  def usingSimpleDSL(): Unit = {
    val ids: List[Long] = DB readOnly { implicit session =>
      withSQL {
        select(m.result.id).from(Member as m).where.isNotNull(m.createdAt)
      }.map(_.long(m.resultName.id)).list().apply()
    }
    logger.info(s"usingSimpleDSL, $ids")
  }

  def usingWithRoundBracket(): Unit = {
    val names: List[String] = DB readOnly { implicit session =>
      withSQL {
        select(m.result.*)
          .from(Member as m)
          .where
          .ge(m.id, 1)
          .and
          .withRoundBracket { _.like(m.name, "%syntax%").or.eq(m.name, "batch") }
      }.map(_.string(m.resultName.name)).list().apply()
    }
    logger.info(s"usingWithRoundBracket, $names")
  }

  def usingAppend(): Unit = {
    val names: List[String] = DB readOnly { implicit session =>
      withSQL {
        select(m.result.*)
          .from(Member as m)
          .where
          .ge(m.id, 1)
          .and
          .append(sqls"(${m.name} like '%syntax%' or ${m.name} = 'batch')")
      }.map(_.string(m.resultName.name)).list().apply()
    }
    logger.info(s"usingAppend, $names")
  }

  def usingDynamicQuery(name: Option[String]): Unit = {
    val member: Option[Member] = DB readOnly { implicit session =>
      withSQL {
        select(m.result.*)
          .from(Member as m)
          .where
          .eq(m.id, 1) //
      }.map(Member(m.resultName)).single().apply()
    }
    logger.info(s"usingDynamicQuery, $member")
  }

  def usingDynamicQuery(cond1: Option[String], cond2: Option[String]): Unit = {
    val member: Option[Member] = DB readOnly { implicit session =>
      withSQL {
        select(m.result.*)
          .from(Member as m)
          .where(sqls.toAndConditionOpt(
            cond1.map( name => sqls.eq(m.name, name)),
            cond2.map( name => sqls.like(m.name, name)),
          ))
      }.map(Member(m.resultName)).single().apply()
    }
    logger.info(s"usingDynamicQuery, $member")
  }

}
