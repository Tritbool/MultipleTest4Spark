package com.tritcorp.mt4s.junit

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

import com.tritcorp.mt4s.dfTools.DataframeTools._
import com.tritcorp.mt4s.logger.DebugMode.WARN
import org.apache.spark.sql.functions._
import org.junit.Test
import com.tritcorp.mt4s.Constants._

case class X(Reponse: String, Entite: String)

class DFToolsJunitTest extends JunitTest {

  @Test
  def testLicensesAreOpenSource(): Unit = {
    val df = readCsvLocal("/licenses.csv", ",").orNull
    val pattern = ".*(BSD|APACHE|MIT|GPL|GNU|NONE|CDDL|COMMON PUBLIC LICENSE|COMMON DEVELOPMENT|ASL|PUBLIC DOMAIN|MPL|MOZILLA|UNRECOGNIZED).*".r
    val licensesCleaning: (String => String) = (l: String) => {

      if (pattern.findFirstMatchIn(l.toUpperCase()).isEmpty) "ERROR" else "OK"

    }
    val licensesUdf = udf(licensesCleaning)
    val deduplicatedLicenses = df.dropDuplicates("Category").select("Category", "License").withColumn("OpenSource", licensesUdf(col("Category")))

    assert(deduplicatedLicenses.filter(col("OpenSource").equalTo("ERROR")).count() == 0)

  }

  @Test
  def testIdentical() {
    import sqlContext.implicits._

    setLogLevel(WARN)
    val rdd = sc.parallelize(List(X("oui", "Maitre"), X("non", "Femme")))
    val df = rdd.toDF()


    val columns = Array("Entite", "Reponse")
    val df2 = sc.parallelize(Seq(
      ("Maitre", "oui"),
      ("Femme", "non"))).toDF(columns: _*)

    assert(df.equalsDF(df2))
    assert(df2.equalsDF(df))
    assert(df2.<=(df))
    assert(df2.>=(df))
    assert(df.<=(df2))
    assert(df.>=(df2))

  }

  @Test
  def testSchemaSameSizeWrongCol() {
    import sqlContext.implicits._

    setLogLevel(WARN)
    val rdd = sc.parallelize(List(X("oui", "Maitre"), X("non", "Femme")))
    val df = rdd.toDF()


    val columns = Array("Entite", "Pâté")
    val df2 = sc.parallelize(Seq(
      ("Maitre", "oui"),
      ("Femme", "non"))).toDF(columns: _*)

    assert(df.compare(df2)==SCHEMAS_MATCH_ERR)
    assert(df2.compare(df)==SCHEMAS_MATCH_ERR)


  }

  @Test
  def testDifferentSize() {
    import sqlContext.implicits._

    val rdd = sc.parallelize(List(X("oui", "Maitre"), X("non", "Femme")))
    val df = rdd.toDF()


    val columns = Array("Entite", "Reponse")
    val df2 = sc.parallelize(Seq(
      ("Maitre", "oui"),
      ("Femme", "non"),
      ("Enfant", "sussmoe"))).toDF(columns: _*)

    assert(df.compare(df2)==DF2_BIGGER_THAN_DF1)
    assert(df2.compare(df)==DF1_BIGGER_THAN_DF2)

  }

  @Test
  def testDifferentSizeAssertable() {
    import sqlContext.implicits._

    val rdd = sc.parallelize(List(X("oui", "Maitre"), X("non", "Femme")))
    val df = rdd.toDF()


    val columns = Array("Entite", "Reponse")
    val df2 = sc.parallelize(Seq(
      ("Maitre", "oui"),
      ("Femme", "non"),
      ("Enfant", "sussmoe"))).toDF(columns: _*)

    assert(df.<(df2))
    assert(df2.>(df))

  }

  @Test
  def testSameSizeDifferentContent() {
    import sqlContext.implicits._

    setLogLevel(WARN)
    val rdd = sc.parallelize(List(X("oui", "Maitre"), X("non", "Femme"), X("Chien","WOUF")))
    val df = rdd.toDF()

    df.show
    val columns = Array("Entite", "Reponse")
    val df2 = sc.parallelize(Seq(
      ("Maitre", "oui"),
      ("Femme", "non"),
      ("Enfant", "sussmoe"))).toDF(columns: _*)

    df2.showDebug()

    assert(df.compare(df2)==DF1_AND_DF2_ROWS_DIFF)
    assert(df2.compare(df)==DF1_AND_DF2_ROWS_DIFF)

  }

  @Test
  def testBothDifferentContent() {
    import sqlContext.implicits._

    setLogLevel(WARN)
    val rdd = sc.parallelize(List(X("oui", "Maitre"), X("non", "Femme"), X("Chien","WOUF"), X("Pâté","croûte")))
    val df = rdd.toDF()

    df.show
    val columns = Array("Entite", "Reponse")
    val df2 = sc.parallelize(Seq(
      ("Maitre", "oui"),
      ("Femme", "non"),
      ("Enfant", "sussmoe"))).toDF(columns: _*)

    df2.show

    assert(df.compare(df2)==DF1_AND_DF2_ROWS_DIFF)
    assert(df2.compare(df)==DF1_AND_DF2_ROWS_DIFF)

  }


  @Test
  def testWrongSchemas() {
    import sqlContext.implicits._

    val rdd = sc.parallelize(List(X("oui", "Maitre"), X("non", "Femme")))
    val df = rdd.toDF()

    val columns = Array("Entite", "Reponse", "Membre")
    val df2 = sc.parallelize(Seq(
      ("Maitre", "oui", "Cadillac"),
      ("Femme", "non", "King Ju"),
      ("Enfant", "sussmoe", "Rascar-Kapac"))).toDF(columns: _*)


    assert(df.compare(df2)==SCHEMAS_MATCH_ERR)
    assert(df2.compare(df)==SCHEMAS_MATCH_ERR)

  }


}
