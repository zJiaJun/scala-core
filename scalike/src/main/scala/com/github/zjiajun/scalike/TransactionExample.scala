package com.github.zjiajun.scalike

import java.sql.SQLException

import com.typesafe.scalalogging.LazyLogging
import scalikejdbc._

/**
  * @author zhujiajun
  * @version 1.0
  * @since 2020/10/9 12:45
  *
  * @see [[http://scalikejdbc.org/documentation/transaction.html]]
  */
object TransactionExample extends App with LazyLogging {

  initHikariConnectionPool()
  usingReadOnly()
  usingReadOnlyExecuteUpdateThrowException()
  usingAutoCommit()
  usingLocalTx()
  usingWithInTx()

  def usingReadOnly(): Unit = {
    val nameOpt: Option[String] = DB readOnly { implicit session =>
      sql"select * from members".map(_.string("name")).first().apply()
    }
    logger.info(s"usingReadOnly, $nameOpt")
  }

  def usingReadOnlyExecuteUpdateThrowException(): Unit =
    DB readOnly { implicit session =>
      try {
        sql"update members set name='something' where id = 1".update().apply()
      } catch {
        case ex: SQLException => logger.error("usingReadOnlyExecuteUpdateThrowException", ex)
      }
    }

  def usingAutoCommit(): Unit =
    DB autoCommit { implicit session =>
      sql"update members set name='Alice' where id = 1".update().apply()
      sql"update members set name='something' where id = 2".update().apply()
    }

  def usingLocalTx(): Unit =
    DB localTx { implicit session =>
      //事务开始
      sql"update members set name='Alice_tx' where id = 1".update().apply()
      sql"update members set name='Bob_tx' where id = 2".update().apply()
      //事务结束
    }

  //withIn,在已存在的事务中执行, 事务的操作,如 begin, commit, rollback需要使用者自己维护
  def usingWithInTx(): Unit = {
    val db = DB(ConnectionPool.borrow())
    db.begin()
    try {
      db withinTx { implicit session =>
        sql"update members set name='Alice_with_in_tx' where id = 1".update().apply()
        sql"update members set name='Bob_with_in_tx' where id = 2".update().apply()
      }
      db.commit()
    } catch {
      case ex: Throwable => {
        db.rollback()
        logger.error(s"usingWithInTx exception roll back", ex)
      }
    } finally {
      db.close()
    }
  }

}
