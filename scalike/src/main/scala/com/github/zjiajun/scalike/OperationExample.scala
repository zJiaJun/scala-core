package com.github.zjiajun.scalike

import java.sql.{PreparedStatement, ResultSet}

import com.github.zjiajun.scalike.entity._
import com.typesafe.scalalogging.LazyLogging
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import scalikejdbc._

/**
  *@author zhujiajun
  *@version 1.0
  *@since 2020/10/8 14:06
  *
  * @see [[http://scalikejdbc.org/documentation/operations.html]]
  */
object OperationExample extends App with LazyLogging {

  initHikariConnectionPool()
//  usingSingleSimple(1L)
//  usingDefineMapper(2L)
//  usingDefineClass(3L)
//  usingQueryDSl(1L)
//  usingFirstResult()
//  usingListResult()
//  usingForeachResult()
  usingParameterBinder()
//  usingTypeBinder()

  def initHikariConnectionPool(): Unit = {
    val hikariConfig = new HikariConfig()
    hikariConfig.setJdbcUrl(url)
    hikariConfig.setUsername(user)
    hikariConfig.setPassword(password)
    hikariConfig.setMaximumPoolSize(10)
    hikariConfig.setConnectionTimeout(3000L)
    hikariConfig.setPoolName("scalikejdbc-hikari-pool")
    val hikariDataSource = new HikariDataSource(hikariConfig)
    ConnectionPool.singleton(new DataSourceConnectionPool(hikariDataSource))
  }

  def usingSingleSimple(id: Long): Unit = {
    val nameOpt: Option[String] = DB readOnly { implicit session =>
      sql"select name from members where id = $id".map(_.string("name")).single().apply()
    }
    logger.info(s"usingSingleSimple, $nameOpt")
  }

  def usingDefineMapper(id: Long): Unit = {
    val nameOnly = (rs: WrappedResultSet) => rs.string("name")
    val nameOpt: Option[String] = DB readOnly { implicit session =>
      sql"select name from members where id = $id".map(nameOnly).single().apply()
    }
    logger.info(s"usingDefinedMapper, $nameOpt")
  }

  def usingDefineClass(id: Long): Unit = {
    val member: Option[Member] = DB readOnly { implicit session =>
      sql"select * from members where id = $id"
        .map(rs => Member(rs.long(1), rs.string(2), rs.date(3)))
        .single()
        .apply()
    }
    logger.info(s"usingDefineClass, $member")
  }

  def usingQueryDSl(id: Long): Unit = {
    val m = Member.syntax("m")
    val member: Option[Member] = DB readOnly { implicit session =>
      withSQL {
        select.from(Member as m).where.eq(m.id, id)
      }.map(rs => Member(m.resultName)(rs)).single().apply()
    }
    logger.info(s"usingQueryDSl, $member")
  }

  def usingFirstResult(): Unit = {
    val nameOpt: Option[String] = DB readOnly { implicit session =>
      sql"select name from members".map(_.string("name")).first().apply()
    }
    logger.info(s"usingFirstRow  $nameOpt")
  }

  def usingListResult(): Unit = {
    val members: List[Member] = DB readOnly { implicit session =>
      sql"select * from members"
        .map(rs => Member(rs.long(1), rs.string(2), rs.date(3)))
        .list()
        .apply()
    }
    logger.info(s"usingListResult, $members")
  }

  def usingForeachResult(): Unit =
    DB readOnly { implicit session =>
      sql"select * from members".foreach { rs =>
        logger.info(s"usingForeachResult, ${rs.long("id")}, ${rs.string("name")}, ${rs.date("created_at")}")
      }
    }

  case class MemberId(id: Long)
  def usingParameterBinder(): Unit = {
    val memberId = MemberId(2L)
    val memberIdParameterBinder =
      ParameterBinder(value = memberId, binder = (stmt: PreparedStatement, idx: Int) => stmt.setLong(idx, memberId.id))

    val name: Option[String] = DB readOnly { implicit session =>
      sql"select name from members where id = $memberIdParameterBinder".map(_.string("name")).single().apply()
    }
    logger.info(s"usingParameterBinder, $name")
  }

  def usingTypeBinder(): Unit = {
    implicit val memberIdTypeBinder: TypeBinder[MemberId] = new TypeBinder[MemberId] {

      override def apply(rs: ResultSet, columnIndex: Int): MemberId = MemberId(rs.getLong(columnIndex))

      override def apply(rs: ResultSet, columnLabel: String): MemberId = MemberId(rs.getLong(columnLabel))
    }
    val memberIds: List[MemberId] = DB readOnly { implicit session =>
      sql"select id from members".map(_.get[MemberId]("id")).list().apply()
    }
    logger.info(s"usingTypeBinder, $memberIds")
  }
}
