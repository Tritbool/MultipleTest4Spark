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
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object DataframeTools extends LazyLogging {

  private val conf = new SparkConf().setAppName("Comparison").setMaster("local[*]")
  private val ss: SparkSession = SparkSession.builder().config(conf).getOrCreate()
  private val sc: SparkContext = ss.sparkContext
  private val sqlContext: SQLContext = ss.sqlContext
  sc.setLogLevel("WARN")


  def readCsvLocal(path: String, delimiter: String = ";", encoding: String = "UTF-8"): Option[DataFrame] = {
    Option(ss.read
      .option("delimiter", delimiter)
      .option("inferSchema", "true")
      .option("mode", "DROPMALFORMED")
      .option("header", "true")
      .option("encoding", encoding)
      .csv(getClass.getResource(path).toURI.toString))
  }

  implicit def df2DebugDF(df:DataFrame):DebugDF={
    new DebugDF(df)
  }

}
