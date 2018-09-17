package com.tritcorp.mt4s
/* MT4S - Multiple Tests 4 Spark - a simple Junit/Scalatest testing framework for spark
* Copyright (C) 2018  Gauthier LYAN
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
import com.tritcorp.mt4s.logger.DebugMode.{INFO, LogLevel}
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Trait that contains the basic stuff for Spark test classes
  */
trait TestBase extends LazyLogging {

  val conf: SparkConf = new SparkConf().setAppName("SPARK TEST").setMaster(Config.MASTER).set("spark.local.ip",Config.IP).set("spark.driver.host",Config.HOST)
  val ss: SparkSession = SparkSession.builder().config(conf).getOrCreate()
  val sc: SparkContext = ss.sparkContext
  val sqlContext: SQLContext = ss.sqlContext
  var logLvl: LogLevel = INFO
  ConsoleHeader.printHeader()


  logger.info("Spark app launched as : " + ss.sparkContext.appName)

  setLogLevel(logLvl)


  /** used to set the log level of the test class
    *
    * @param lvl the log level to apply
    * @see com.tritcorp.testing.logger.DebugMode
    */
  def setLogLevel(lvl: LogLevel): Unit = {
    val logLvlStr: String = lvl.toString
    sc.setLogLevel(logLvlStr)

    logLvlStr match {
      case "ALL" => logger.trace("LOG LEVEL SET TO " + logLvlStr)
      case "TRACE" => logger.trace("LOG LEVEL SET TO " + logLvlStr)
      case "DEBUG" => logger.debug("LOG LEVEL SET TO " + logLvlStr)
      case "INFO" => logger.info("LOG LEVEL SET TO " + logLvlStr)
      case "WARN" => logger.warn("LOG LEVEL SET TO " + logLvlStr)
      case "ERROR" => logger.error("LOG LEVEL SET TO " + logLvlStr)
      case "FATAL" => {
        System.err.println(this.getClass.toString + "LOG LEVEL SET TO " + logLvlStr)
        System.err.flush()
      }
      case "OFF" => {
        System.err.println(this.getClass.toString + " LOG LEVEL SET TO " + logLvlStr)
        System.err.flush()
      }
      case _ => {
        System.err.println(this.getClass.toString + " INVALID LOG LEVEL. LOG LEVEL RESET TO WARN AS DEFAULT")
        System.err.flush()
        sc.setLogLevel("WARN")
      }
    }
  }

}
