package com.tritcorp.mt4s.dfTools
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
import com.tritcorp.mt4s.Config
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object DataframeTools extends LazyLogging {

  private val conf = new SparkConf().setAppName("DataframeTools").setMaster(Config.MASTER).set("spark.local.ip",Config.IP).set("spark.driver.host",Config.HOST)
  private val ss: SparkSession = SparkSession.builder().config(conf).getOrCreate()
  private val sc: SparkContext = ss.sparkContext
  private val sqlContext: SQLContext = ss.sqlContext
  sc.setLogLevel("WARN")

  /*TODO : ADD readRddLocal */

  def readCsvLocal(path: String, delimiter: String = ";", encoding: String = "UTF-8", inferSchema:String = "false", nullValue:String = ""): Option[DataFrame] = {
    logger.debug("Attempting to load csv file at : "+ getClass.getResource(path).toString())
    Option(ss.read
      .option("delimiter", delimiter)
      .option("inferSchema", inferSchema)
      .option("mode", "DROPMALFORMED")
      .option("header", "true")
      .option("encoding", encoding)
      .option("nullValue",nullValue)
      .csv(getClass.getResource(path).toURI.toString))
  }

  /**
    * Allows implicit conversion of DataFrame to DebugDF
    * @param df the DataFrame to test
    * @return df encapsulated in a new DebugDF 
    */
  implicit def df2DebugDF(df:DataFrame):DebugDF={
    new DebugDF(df)
  }

}
