name := """play-tapir-sandbox"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.3"

val tapirVersion = "1.11.1"
val playSlickVersion = "6.1.1"

libraryDependencies ++= Seq(
  guice,
  "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-play-server" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-json-play" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui" % tapirVersion,
  "org.playframework" %% "play-slick" % playSlickVersion,
  "org.playframework" %% "play-slick-evolutions" % playSlickVersion,
  "mysql" % "mysql-connector-java" % "8.0.33",
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
)
