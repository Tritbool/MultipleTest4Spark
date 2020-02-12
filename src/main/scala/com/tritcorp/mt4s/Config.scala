package com.tritcorp.mt4s

import com.tritcorp.mt4s.logger.DebugMode.{LogLevel, WARN}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SparkSession}

/* MT4S - Multiple Tests 4 Spark - a simple Junit/Scalatest testing framework for spark
* Copyright (C) 2018  Gauthier LYAN
*
*
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR *CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.
*/

object Config {

  var MASTER="local[8]"
  var IP="127.0.0.1"
  var HOST="127.0.0.1"

  val conf: SparkConf = new SparkConf().setAppName("SPARK TEST").setMaster(Config.MASTER).set("spark.local.ip",Config.IP).set("spark.driver.host",Config.HOST)
  val ss: SparkSession = SparkSession.builder().config(conf).getOrCreate()
  val sc: SparkContext = ss.sparkContext
  val sqlContext: SQLContext = ss.sqlContext
  var logLvl:LogLevel = WARN

}
