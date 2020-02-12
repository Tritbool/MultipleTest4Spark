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

import com.tritcorp.mt4s.logger.DebugMode._
import com.tritcorp.mt4s.dfTools.DataframeTools._
import com.tritcorp.mt4s.Config._
import org.apache.spark.sql.functions.{col, udf}

case class X(Reponse: String, Entite: String)

class TestingScalatest extends FlatSpecTest{

  setLogLevel(WARN)

  "DFs" must "be identical" in {
    import sqlContext.implicits._

    val rdd = sc.parallelize(List(X("oui", "Maitre"), X("non", "Femme")))
    val df = rdd.toDF()


    val columns = Array("Entite", "Reponse")
    val df2 = sc.parallelize(Seq(
      ("Maitre", "oui"),
      ("Femme", "non"))).toDF(columns: _*)

    // Implicit conversion
    assert(df.compareTo(df2)==0)

    val ddf2 = df2DebugDF(df2)
    ddf2.setLogLevel(ERROR)
    ddf2.showDebug()
    assert(ddf2.compareTo(df)==0)


  }

  "File" should "be loaded" in {
    assert(readCsvLocal("/test.csv").nonEmpty)
  }

  "Licenses" should "all be open source" in {

    val df = readCsvLocal("/licenses.csv",",").orNull
    val pattern = ".*(BSD|APACHE|MIT|GPL|GNU|NONE|CDDL|COMMON PUBLIC LICENSE|COMMON DEVELOPMENT|ASL|PUBLIC DOMAIN|MPL|MOZILLA|UNRECOGNIZED).*".r
    val licensesCleaning:(String=>String)=(l:String)=>{

      if(pattern.findFirstMatchIn(l.toUpperCase()).isEmpty) "ERROR" else "OK"

    }
    val licensesUdf= udf(licensesCleaning)
    val deduplicatedLicenses = df.dropDuplicates("Category").select("Category","License").withColumn("OpenSource",licensesUdf(col("Category")))

    assert(deduplicatedLicenses.filter(col("OpenSource").equalTo("ERROR")).count()==0)

  }


}
