organization := "com.tritcorp.exp"

name := "MT4S"

version := "1.0"

scalaVersion := "2.12.4"

resolvers += "Artima" at "http://repo.artima.com/releases"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.1",
  "org.apache.spark" %% "spark-sql" % "2.4.1",
  "org.apache.spark" %% "spark-hive" % "2.4.1",
  "org.apache.spark" %% "spark-mllib" % "2.4.1",
  "com.novocode" % "junit-interface" % "0.11",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  "org.scalactic" %% "scalactic" % "3.0.4",
  "org.scalatest" %% "scalatest" % "3.0.4"
)

