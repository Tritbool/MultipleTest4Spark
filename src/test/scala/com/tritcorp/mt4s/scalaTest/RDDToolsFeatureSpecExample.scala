package com.tritcorp.mt4s.scalaTest

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


import com.tritcorp.mt4s.rddTools.RddTools
import com.tritcorp.mt4s.rddTools.RddTools._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, Row}

class RDDToolsFeatureSpecExample extends FeatureSpecTest {

  info("As a spark developer")
  info("I want to be able to transform a RDD containing a csv read from file")
  info("Into a Spark DataFrame")

  feature("RDD to DF implicit conversion") {
    scenario("Converting CSV data separated by ',' from RDD to DataFrame") {

      val res: RDD[String] = sc.parallelize(Seq(
        "Valeur verité,Sujet,Verbe,Determinant Possessif,Complément",
        "oui,je,suis,ton,pere",
        "non,tu,es,ma,soeur"))
      RddTools.csvDelimiter = ","
      val df = res.filter(col("Verbe").equalTo("suis"))
      assert(df.isInstanceOf[DataFrame])
      assert(df.count == 1)
    }
    scenario("Converting CSV data separated by '¤' from RDD to DataFrame") {
      val res1: RDD[String] = sc.parallelize(Seq(
        "Valeur verité¤Sujet¤Verbe¤Determinant Possessif¤Complément",
        "oui¤je¤suis¤ton¤pere",
        "non¤tu¤es¤ma¤soeur"))
      RddTools.csvDelimiter = "¤"
      val df1 = res1.filter(col("Sujet").equalTo("tu"))
      assert(df1.isInstanceOf[DataFrame])
      assert(df1.count == 1)
    }
  }

  info("as a spark developer")
  info("I want to load a file from my resources folder")

  feature("Load a file from resources and put its content into a RDD") {
    scenario("loading a file, verify it has been loaded") {

      val expected: RDD[Row] = sc.parallelize(
        List(
          Row.fromSeq(Seq("A B C 23 FG 42 FDP |e")),
          Row.fromSeq(Seq("1 2 3 4"))
        ))

      val loaded:RDD[String] = RddTools.readRddLocal("/rddLoad.txt")

      assert(expected.equalsRDD(loaded))

    }
  }

}
