package com.tritcorp.mt4s.scalaTest

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


import com.tritcorp.mt4s.rddTools.RddTools
import com.tritcorp.mt4s.rddTools.RddTools._
import com.tritcorp.mt4s.Config._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, Row}

class RDDToolsFeatureSpecExample extends FeatureSpecTest {

  info("As a spark developer")
  info("I want to be able to transform a RDD containing a csv read from file")
  info("Into a Spark DataFrame")

  Feature("RDD to DF implicit conversion") {
    Scenario("Converting CSV data separated by ',' from RDD to DataFrame") {

      val res: RDD[String] = sc.parallelize(Seq(
        "Valeur verité,Sujet,Verbe,Determinant Possessif,Complément",
        "oui,je,suis,ton,pere",
        "non,tu,es,ma,soeur"))
      RddTools.csvDelimiter = ","
      val df = res.filter(col("Verbe").equalTo("suis"))
      assert(df.isInstanceOf[DataFrame])
      assert(df.count == 1)
    }
    Scenario("Converting CSV data separated by '¤' from RDD to DataFrame") {
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

  Feature("Load a file from resources and put its content into a RDD") {
    Scenario("loading a file, verify it has been loaded") {

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
