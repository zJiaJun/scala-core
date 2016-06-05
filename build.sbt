name := "scala-sbt"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= {
  val akkaV = "2.4.1"
  val jedisV = "2.8.0"
  val json4sV = "3.2.11"
  val fastjsonV = "1.2.8"
  Seq(
    "org.scala-lang" % "scala-compiler" % "2.11.7",
    "org.scala-lang" % "scala-reflect" % "2.11.7",
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-remote" % akkaV,
    "com.typesafe.akka" %% "akka-contrib" % akkaV,
    "com.typesafe.akka" %% "akka-slf4j" % akkaV,
    "redis.clients" % "jedis" % jedisV,
    "mysql" % "mysql-connector-java" % "5.1.36",
    "org.json4s" %% "json4s-jackson" % json4sV,
    "org.json4s" %% "json4s-native" % json4sV,
    "com.alibaba" % "fastjson" %  fastjsonV
  )
}