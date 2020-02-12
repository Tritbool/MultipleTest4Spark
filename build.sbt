organization := "com.tritcorp.exp"

name := "MT4S"

version := "1.12"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.5",
  "org.apache.spark" %% "spark-sql" % "2.4.5",
  "org.apache.spark" %% "spark-hive" % "2.4.5",
  "org.apache.spark" %% "spark-mllib" % "2.4.5",
  "com.novocode" % "junit-interface" % "0.11",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.scalactic" %% "scalactic" % "3.1.0",
  "org.scalatest" %% "scalatest" % "3.1.0"
)

