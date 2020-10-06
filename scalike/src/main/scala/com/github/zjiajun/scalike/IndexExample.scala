package com.github.zjiajun.scalike

import com.typesafe.scalalogging.LazyLogging
import scalikejdbc._

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/10/4 23:56
  */
object IndexExample extends App with LazyLogging {

  Class.forName("com.mysql.jdbc.Driver")
  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(enabled = true, singleLineMode = true, logLevel = 'INFO)
  val (url, user, password) =
    ("jdbc:mysql://localhost/sandbox?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true&autoReconnectForPools=true",
     "root",
     "root")
  ConnectionPool.singleton(url, user, password)

  implicit val session = AutoSession

  sql"""
        create table if not exists members (
        id int UNSIGNED auto_increment,
        name varchar(64),
        created_at timestamp not null,
        primary key(id)
        )
  """.execute.apply()

//   insert initial data
//  Seq("Alice", "Bob", "Chris") foreach { name =>
//    sql"insert into members (name, created_at) values (${name}, current_timestamp)".update.apply()
//  }

  // for now, retrieves all data as Map value
  val allValueInMap: List[Map[String, Any]] = sql"select * from members".map(_.toMap).list.apply()
  logger.info(s"allValueInMap $allValueInMap")

  // defines entity object and extractor
  import java.time._
  case class Member(id: Long, name: Option[String], createdAt: ZonedDateTime)
  object Member extends SQLSyntaxSupport[Member] {
    override val tableName = "members"
    def apply(rs: WrappedResultSet) =
      new Member(rs.long("id"), rs.stringOpt("name"), rs.zonedDateTime("created_at"))
    def apply(r: ResultName[Member])(rs: WrappedResultSet) =
      new Member(rs.long(r.id), rs.stringOpt(r.name), rs.zonedDateTime(r.createdAt))
  }

  // find all members
  val members: List[Member] = sql"select * from members".map(rs => Member(rs)).list.apply()
  logger.info(s"list members $members")

  // use paste mode (:paste) on the Scala REPL
  val m = Member.syntax("m")
  val name = "Alice"
  val alice: Option[Member] = withSQL {
    select.from(Member as m).where.eq(m.name, name)
  }.map(rs => Member(m.resultName)(rs)).single.apply()
  logger.info(s"query dsl result $alice")

  val memberId: Option[Long] = DB readOnly { implicit session =>
    sql"select id from members where name = ${name}" // don't worry, prevents SQL injection
      .map(rs => rs.long("id")) // extracts values from rich java.sql.ResultSet
      .single // single, list, traversable
      .apply()
  }
  logger.info(s"memberId $memberId")

}
