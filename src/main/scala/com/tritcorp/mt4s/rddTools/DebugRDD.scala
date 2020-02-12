package com.tritcorp.mt4s.rddTools

import com.tritcorp.mt4s.DebugDatasetBase
import com.tritcorp.mt4s.logger.DebugMode.WARN
import com.tritcorp.mt4s.dfTools.DataframeTools._
import com.tritcorp.mt4s.Constants._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SQLContext}

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
class DebugRDD(rdd: RDD[Row]) extends DebugDatasetBase with Ordered[RDD[Row]] {

  logLvl = WARN

  setLogLevel(logLvl)

  /**
    * Compares the embedded rdd with the rdd that is passed as parameter
    *
    * @see com.tritcorp.mt4s.dfTools.DebugDf.compare
    * @param that the rdd to compare with the embedded rdd
    * @return
    * 0 if rdds are semantically equal
    * 1 if rdds schemas don't match
    * 2 if the embedded rdd has more rows than that
    * 3 if that has more rows than the embedded rdd
    * 5 if both rdd have rows that don't exist in the other
    * 4 if an unknown error occurred
    */
  override def compare(that: RDD[Row]): Int = {
    prepareRdd(rdd).compare(prepareRdd(that))
  }


  def equalsRDD(that:RDD[Row]):Boolean={
    compare(that)==DF_EQUAL
  }

  override def compareTo(that: RDD[Row]): Int ={
    compare(that)
  }

  override def <(that: RDD[Row]): Boolean={
    compare(that)==DF2_BIGGER_THAN_DF1
  }

  override def >(that: RDD[Row]): Boolean={
    compare(that)==DF1_BIGGER_THAN_DF2
  }

  override def <=(that: RDD[Row]): Boolean={
    val res =compare(that)
    res==DF2_BIGGER_THAN_DF1 || res ==DF_EQUAL
  }

  override def >=(that: RDD[Row]): Boolean={
    val res = compare(that)
    res==DF1_BIGGER_THAN_DF2 || res ==DF_EQUAL
  }

  /**
    * Prepares a RDD for comparison
    * In order to do so, a schema is infered from the longest row of the RDD, then each smaller row is filled with phony data.
    * for example, a rdd like
    *
    * A B C D
    * 1 2
    * x y z
    *
    * would become the following DataFrame
    *
    * +-----+-----+-----+-----+
    * | c_0 | c_1 | c_2 | c_3 |
    * +-----+-----+-----+-----+
    * |  A  |  B  |  C  |  D  |
    * |  1  |  2  | n/a | n/a |
    * |  x  |  y  |  z  | n/a |
    * +-----+-----+-----+-----+
    *
    *
     * @param rddP A RDD[Row] to prepare
    * @return a DataFrame infered from rddP
    **/
  private def prepareRdd(rddP: RDD[Row]): DataFrame = {
    val maxSize = rddP.map(row => row.size).reduce((a, b) => if (a > b) a else b)

    var l: List[StructField] = Nil
    for (i <- 0 until maxSize) {
      l ++= StructField("c_" + i, StringType, nullable = true) :: Nil
    }

    val schema = StructType(l)

    val rddRefactored = rddP.map { row =>
      if (row.size < maxSize) {
        var rn = row.toSeq.toList

        for (i <- row.size - 1 until maxSize - 1) {
          rn ++= i.toString :: Nil
        }
        Row.fromSeq(rn)
      }
      else {
        row
      }

    }

    sqlContext.createDataFrame(rddRefactored, schema)
  }


}
