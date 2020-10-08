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

}
