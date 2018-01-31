organization := "com.tritcorp.exp"

name := "MT4S"

version := "0.1"

scalaVersion := "2.11.5"

resolvers += "Artima" at "http://repo.artima.com/releases"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.2.1",
  "org.apache.spark" %% "spark-sql" % "2.2.1",
  "org.apache.spark" %% "spark-hive" % "2.2.1",
  "org.apache.spark" %% "spark-mllib" % "2.2.1",
  "com.novocode" % "junit-interface" % "0.11",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  "org.scalactic" %% "scalactic" % "3.0.4",
  "org.scalatest" %% "scalatest" % "3.0.4"
)

parallelExecution in Test := false