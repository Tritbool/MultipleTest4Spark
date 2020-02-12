package com.tritcorp.mt4s.dfTools
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
