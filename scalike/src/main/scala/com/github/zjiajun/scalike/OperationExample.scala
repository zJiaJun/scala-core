package com.github.zjiajun.scalike

import java.sql.{PreparedStatement, ResultSet}
import java.util.Date

import com.github.zjiajun.scalike.entity._
import com.typesafe.scalalogging.LazyLogging
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
  usingExecute()
  usingSingleSimple(1L)
  usingDefineMapper(2L)
  usingDefineClass(3L)
  usingQueryDSl(1L)
  usingFirstResult()
  usingListResult()
  usingForeachResult()
  usingParameterBinder()
  usingTypeBinder()
  usingUpdateWithTx()
  usingDSLUpdateWithTx()
  usingBatchInsert()

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

  def usingUpdateWithTx(): Unit = {
    val name = "Leon"
    DB localTx { implicit session =>
      sql"insert into members(name, created_at) values ($name, now())".update.apply()
      val newId: Long =
        sql"""insert into members(name, created_at) 
            values (${"new" + name}, now())""".updateAndReturnGeneratedKey.apply()
      sql"update members set name=${"update" + name} where id = $newId".update.apply()
      sql"delete from members where id = $newId".update.apply()
    }
  }

  def usingDSLUpdateWithTx(): Unit = {
    val dslName = "DSL_Leon"
    val column = Member.column
    DB localTx { implicit session =>
      withSQL {
        insert
          .into(Member)
          .namedValues(
            column.name -> dslName,
            column.createdAt -> sqls.currentTimestamp
          )
      }.update.apply()

      val dslNewId: Long = withSQL {
        insert
          .into(Member)
          .namedValues(column.name -> dslName.concat("_new"), column.createdAt -> sqls.currentTimestamp)
      }.updateAndReturnGeneratedKey.apply()

      withSQL {
        update(Member).set(column.name -> dslName.concat("_update")).where.eq(column.id, dslNewId)
      }.update.apply()

      withSQL {
        delete.from(Member).where.eq(column.id, dslNewId)
      }.update.apply()
    }
  }

  def usingExecute(): Unit =
    DB autoCommit { implicit session =>
      sql"""
            create table if not exists members(
              id int unsigned auto_increment,
              name varchar(64),
              created_at timestamp not null,
              primary key(id))
           """.execute.apply()

      sql"alter table members modify name varchar(64) null comment 'name'".execute().apply()
    }

  def usingBatchInsert(): Unit = {
    val sum_1: IndexedSeq[Int] = DB localTx { implicit session =>
      //      val batchParams: Seq[Seq[Any]] = Seq(List("100", new Date()), List("200", new Date()))
      val batchParams: Seq[Seq[Any]] = (1 to 3).map(i => Seq(s"batch_$i", new Date()))
      sql"insert into members(name, created_at) values (?, ?)".batch(batchParams: _*).apply()
    }
    logger.info(s"usingBatch batch,$sum_1")

    val sum_2: IndexedSeq[Int] = DB localTx { implicit session =>
      val batchNameParams =
        Seq(
          Seq('name -> "batchName_1", 'createdAt -> new Date()),
          Seq('name -> "batchName_2", 'createdAt -> new Date())
        )
      sql"insert into members(name, created_at) values ({name}, {createdAt})".batchByName(batchNameParams: _*).apply()
    }
    logger.info(s"usingBatch batchByName, $sum_2")

    val idKeys: IndexedSeq[Long] = DB localTx { implicit session =>
      val batchParams: Seq[Seq[Any]] = (1 to 3).map(i => Seq(s"batchAndReturnGeneratedKey_$i", new Date()))
      sql"insert into members(name, created_at) values (?, ?)".batchAndReturnGeneratedKey(batchParams: _*).apply()
    }
    logger.info(s"usingBatch batchAndReturnGeneratedKey $idKeys")
  }
}
