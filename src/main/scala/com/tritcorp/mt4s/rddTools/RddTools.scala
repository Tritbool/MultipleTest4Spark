package com.tritcorp.mt4s.rddTools

import com.tritcorp.mt4s.Config._
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SQLContext, SparkSession}

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
object RddTools extends LazyLogging {

  sc.setLogLevel("WARN")
  /**
    * Change it to convert csv rdd to dataframe.
    */
  var csvDelimiter:String =";"

  /**
    * Implicitly converts a RDD of Row to a DebugRdd
    * @param rdd
    * @return
    */
  implicit def rdd2DebugRdd(rdd: RDD[Row]): DebugRDD = {
    new DebugRDD(rdd)
  }

  implicit def rddString2rddRow(rdd:RDD[String]):RDD[Row]={
    rdd.map(r=>Row.fromSeq(Seq(r)))
  }

  /**
    * Loads a file from the resources folder
    * @param file The file's path in the resources folder
    * @return a RDD[String] with the file's content
    */
  def readRddLocal(file:String):RDD[String]={
    sc.textFile(getClass.getResource(file).toString()).filter(_.nonEmpty)
  }

  /**
    * Loads a file from any folder
    * @param file The file's path
    * @return a RDD[String] with the file's content
    */
  def readRddFree(file:String):RDD[String]={
    sc.textFile(file).filter(_.nonEmpty)
  }

  /**
    * Converts a RDD that contains csv info into a dataframe
    * - The first row of the rdd MUST be the csv header
    * - The rdd rows must all have the same length
    * @param rdd the rdd to convert
    * @return the dataframe from the rdd
    */
  implicit def rddCsvToDF(rdd: RDD[String]): DataFrame = {
    logger.warn("/!\\ DID YOU SET CORRECTLY THE csvDelimiter VALUE IN RddTools FOR RDD TO DF CONVERSION /!\\ ?")
    logger.warn("CURRENT DELIMITER : "+csvDelimiter)

    def dropFirst(rdd: RDD[Row]): RDD[Row] = {
      val fst = rdd.first
      rdd.filter(line => line != fst)
    }

    val res = rdd.map(line => line.split(csvDelimiter))
      .filter(line => line.length > 1)
      .map(line => Row.fromSeq(line))

    val schema = StructType(res.first.toSeq.map(el => StructField(el.asInstanceOf[String], StringType, nullable = true)))

    val df = sqlContext.createDataFrame(dropFirst(res), schema)

  df
  }

}
