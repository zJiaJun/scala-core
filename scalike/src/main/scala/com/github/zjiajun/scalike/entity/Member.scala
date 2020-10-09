package com.github.zjiajun.scalike.entity

import java.util.Date

import scalikejdbc._

/**
  *@author zhujiajun
  *@version 1.0
  *@since 2020/10/8 14:05
  */
case class Member(id: Long, name: String, createdAt: Date)

object Member extends SQLSyntaxSupport[Member] {

  override val tableName = "members"

  def apply(m: ResultName[Member])(rs: WrappedResultSet): Member =
    new Member(rs.long(m.id), rs.string(m.name), rs.date(m.createdAt))

  //it's awesome. Domain Driven Design. 实体不再是单一的数据承载或容器, 由贫血模型 -> 充血模型
  def create(name: String, createdAt: Date)(implicit session: DBSession): Member = {
    val id = sql"insert into $table (${column.name}, ${column.createdAt}) values ($name, $createdAt)"
      .updateAndReturnGeneratedKey()
      .apply()
    Member(id = id, name = name, createdAt = createdAt)
  }

}
