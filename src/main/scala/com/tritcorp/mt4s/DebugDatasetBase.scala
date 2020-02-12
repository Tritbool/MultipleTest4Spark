package com.tritcorp.mt4s

import com.tritcorp.mt4s.logger.DebugMode.{LogLevel, WARN}
import com.typesafe.scalalogging.LazyLogging
import com.tritcorp.mt4s.Config._
import org.apache.spark.SparkContext
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

trait DebugDatasetBase extends LazyLogging{

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
