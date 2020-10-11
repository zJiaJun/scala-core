package com.github.zjiajun.scalike

import java.util.Date

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
  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(singleLineMode = true)
  val (m, mc) = (Member.syntax("m"), Member.column)
  usingSimpleDSL()
  usingWithRoundBracket()
  usingAppend()
  usingDynamicQuery(Option("Alice"))
  usingDelete()
  usingUpdate()
  usingInsert()
//  usingInertSelect()


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
          .withRoundBracket {
            _.like(m.name, "%syntax%").or.eq(m.name, "batch")
          }
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

  def usingDynamicQuery(name: Option[String]): Unit =
    DB readOnly { implicit session =>
      val m_1: Option[Member] = withSQL {
        select(m.result.*)
          .from(Member as m)
          .where
          .eq(m.id, 1)
          .append(if (name.isDefined) sqls.and.eq(m.name, name.get) else sqls.empty)
      }.map(Member(m.resultName)).single().apply()
      logger.info(s"usingDynamicQuery_1, $m_1")

      val (cond1, cond2) = (Option("Alice"), Option.empty)
      val m_2 = withSQL {
        select(m.result.*)
          .from(Member as m)
          .where(
            sqls.toAndConditionOpt(
              cond1.map(name => sqls.eq(m.name, name)),
              cond2.map(name => sqls.like(m.name, s"%$name%")),
            )
          )
      }.map(Member(m.resultName)).first.apply()
      logger.info(s"usingDynamicQuery_2, $m_2")

      val existsMembers: List[Member] = DB readOnly { implicit session =>
        withSQL {
          select(m.result.*)
            .from(Member as m)
            .where
            .exists(select.from(Member as m).where.isNotNull(m.name))
        }.map(Member(m.resultName)).list.apply()
      }
      logger.info(s"usingExists, $existsMembers")

      val betweenMembers: List[Member] = DB readOnly { implicit session =>
        withSQL {
          select(m.result.*).from(Member as m).where.between(m.id, 1, 5)
        }.map(Member(m.resultName)).list().apply()
      }
      logger.info(s"usingBetween, $betweenMembers")

      val memberCount = withSQL {
        select(sqls.count(sqls.distinct(m.id))).from(Member as m)
      }.map(_.int(1))
        .single
        .apply()
      logger.info(s"usingCountDistinct, $memberCount")
    }

  def usingDelete(): Unit = {
    val count: Int = DB autoCommit { implicit session =>
      applyUpdate {
        delete.from(Member).where.eq(mc.id, 200135)
      }
    }
    logger.info(s"usingDelete, $count")
  }

  def usingUpdate(): Unit = {
    val count: Int = DB autoCommit { implicit session =>
      applyUpdate {
        update(Member)
          .set(
            mc.name -> "update_name",
            mc.createdAt -> sqls.currentTimestamp
          )
          .where
          .eq(mc.id, 2)
      }
    }
    logger.info(s"usingUpdate, $count")
  }

  def usingInsert(): Unit =
    DB autoCommit { implicit session =>
      val id_1 = withSQL {
        insert
          .into(Member)
          .columns(mc.name, mc.createdAt)
          .values("insert_name_1", sqls.currentTimestamp)
      }.updateAndReturnGeneratedKey().apply()
      logger.info(s"usingInsert_1, $id_1")

      val id_2 = applyUpdateAndReturnGeneratedKey {
        insert
          .into(Member)
          .columns(mc.name, mc.createdAt)
          .values("insert_name_2", sqls.currentTimestamp)
      }
      logger.info(s"usingInsert_2, $id_2")

      val id_3 = applyUpdateAndReturnGeneratedKey {
        insert
          .into(Member)
          .namedValues(
            mc.name -> "insert_name_3",
            mc.createdAt -> sqls.currentTimestamp
          )
      }
      logger.info(s"usingInsert_3, $id_3")

      val member = Member.usingDSLCreate("insert_name_4", new Date)
      logger.info(s"usingInsert_4, $member")
    }

  def usingInertSelect(): Unit = {
    val count: Int = DB autoCommit { implicit session =>
      //throw Duplicate entry '1' for key 'PRIMARY'
      applyUpdate {
        insert.into(Member).select(_.from(Member as m).where.eq(m.id, 1))
      }
    }
    logger.info(s"usingInertSelect, $count")
  }

}
