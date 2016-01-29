name := "scala-sbt"

version := "1.0"

scalaVersion := "2.11.7"

val AKKA_VERSION = "2.4.1"

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"       % AKKA_VERSION,
  "com.typesafe.akka" %% "akka-remote"      % AKKA_VERSION,
  "com.typesafe.akka" %% "akka-contrib"     % AKKA_VERSION,
  "com.typesafe.akka" %% "akka-slf4j"       % AKKA_VERSION
)