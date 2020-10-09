package com.github.zjiajun.scalike

import java.util.Date

import com.github.zjiajun.scalike.entity.Member
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc._

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/10/9 22:29
  *
  * @see [[http://scalikejdbc.org/documentation/sql-interpolation.html]]
  */
object SQLInterpolationExample extends App with LazyLogging {

  initHikariConnectionPool()
  usingSQLSyntax(false)
  usingSQLSyntaxSupport()

  //it's awesome 动态sql everything
  def usingSQLSyntax(isDesc: Boolean): Unit = {
    val ordering = if (isDesc) sqls"desc" else sqls.asc
    val nameOpt: Option[String] = DB readOnly { implicit session =>
      sql"select name from members order by id $ordering ${sqls.limit(5)}".map(_.string("name")).first().apply()
    }
    logger.info(s"usingSQLSyntax, $nameOpt")
  }

  def usingSQLSyntaxSupport(): Unit = {
    val m = Member.syntax("m")
    val mc = Member.column
    DB localTx { implicit session =>
      val nameOpt = sql"select ${m.result.name} from ${Member as m} where ${m.id} = 1"
        .map(rs => rs.string(m.resultName.name))
        .single()
        .apply()
      logger.info(s"usingSQLSyntaxSupport, $nameOpt")

      val (name, createdAt) = ("syntaxName", new Date())
      val id = sql"insert into ${Member.table} (${mc.name},${mc.createdAt}) values (${name}, ${createdAt})"
        .updateAndReturnGeneratedKey()
        .apply()
      logger.info(s"usingSQLSyntaxSupport, $id")

      val member = Member.create("entityCreate", new Date())
      logger.info(s"usingSQLSyntaxSupport entityCreate, $member")
    }
  }
}
