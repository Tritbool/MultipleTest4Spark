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
*/ class DebugRDD(rdd: RDD[Row]) extends DebugDatasetBase with Ordered[RDD[Row]] {

  sc = rdd.sparkContext
  sqlContext = SQLContext.getOrCreate(sc)
  logLvl = WARN

  setLogLevel(logLvl)

  /**
    * Compares the embedded rdd with the rdd that is passed as parameter
    *
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


  def equaldRDD(that:RDD[Row]):Boolean={
    if(compare(that)==DF_EQUAL)true else false
  }

  /**
    *
     * @param rddP
    * @return
    */
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
