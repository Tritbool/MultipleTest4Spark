package com.tritcorp.mt4s

import com.tritcorp.mt4s.logger.DebugMode.{LogLevel, WARN}
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.SparkContext
import org.apache.spark.sql.{SQLContext, SparkSession}

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

trait DebugDatasetBase extends LazyLogging{
  protected var ss:SparkSession = _
  protected var sc: SparkContext = _
  protected var sqlContext: SQLContext = _
  protected var logLvl:LogLevel = WARN


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
        System.err.println(this.getClass.toString + " LOG LEVEL SET TO " + logLvlStr)
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
