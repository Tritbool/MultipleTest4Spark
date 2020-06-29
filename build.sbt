organization := "com.tritcorp.exp"

name := "MT4S"

version := "2.0-beta"

scalaVersion := "2.12.4"

resolvers += "Artima" at "http://repo.artima.com/releases"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.0.0",
  "org.apache.spark" %% "spark-sql" % "3.0.0",
  "org.apache.spark" %% "spark-hive" % "3.0.0",
  "org.apache.spark" %% "spark-mllib" % "3.0.0",
  "com.novocode" % "junit-interface" % "0.11",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.scalactic" %% "scalactic" % "3.1.0",
  "org.scalatest" %% "scalatest" % "3.1.0"
)
scalacOptions += "-target:jvm-1.8"

parallelExecution in Test := false
