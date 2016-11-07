package com.github.zjiajun.other

/**
  * Created by zhujiajun
  * 16/3/31 13:58
  *
  * 使用implicit实现依赖注入
  * ref: http://blog.csdn.net/xiao_jun_0820/article/details/43967565
  *
  */
object UserImplicitDemo extends App {

  case class User(userId: Int,userName: String)

  class UserDao {
    val users = collection.mutable.Map(1 -> User(1,"aa"),2 -> User(2,"bb"), 3 -> User(3,"cc"))

    def findById(userId: Int) = users.get(userId)

    def findAll() = users

    def updateById(user: User) = users.update(user.userId,user)
  }

  implicit val userDao = new UserDao

  class UserService(implicit dao: UserDao) {
    def getAllUsers() = dao.findAll()

    def updateUser(user: User) = dao.updateById(user)
  }

  val userService = new UserService()

  println(userService.getAllUsers())

  userService.updateUser(User(1,"aaa"))
  println(userService.getAllUsers())

  val userService2 = new UserService()
  println(userService2.getAllUsers())


}
