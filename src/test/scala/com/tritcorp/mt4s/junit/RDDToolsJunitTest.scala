package com.tritcorp.mt4s.junit

import com.tritcorp.mt4s.Constants._
import com.tritcorp.mt4s.rddTools.RddTools
import com.tritcorp.mt4s.rddTools.RddTools._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Row}
import org.junit.Test

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

class RDDToolsJunitTest extends JunitTest {

  @Test
  def testImplicitRddCsvToDfOK(): Unit = {
    val res: RDD[String] = sc.parallelize(Seq(
      "Valeur verité,Sujet,Verbe,Determinant Possessif,Complément",
      "oui,je,suis,ton,pere",
      "non,tu,es,ma,soeur"))
    RddTools.csvDelimiter = ","
    val df = res.filter(col("Verbe").equalTo("suis"))
    assert(df.isInstanceOf[DataFrame])
    assert(df.count == 1)


    val res1: RDD[String] = sc.parallelize(Seq(
      "Valeur verité¤Sujet¤Verbe¤Determinant Possessif¤Complément",
      "oui¤je¤suis¤ton¤pere",
      "non¤tu¤es¤ma¤soeur"))
    RddTools.csvDelimiter = "¤"
    val df1 = res1.filter(col("Sujet").equalTo("tu"))
    assert(df1.isInstanceOf[DataFrame])
    assert(df1.count == 1)

  }


  @Test
  def testRddCmpOK() {


    val r = sc.parallelize(
      List(
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq(1.toString)),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    val r1 = sc.parallelize(
      List(
        Row.fromSeq(Seq(1.toString)),
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    assert(r.equalsRDD(r1))
    assert(r.>=(r1))
    assert(r.<=(r1))

  }

  @Test
  def testRddGeqOK() {


    val r = sc.parallelize(
      List(
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq(1.toString)),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    val r1 = sc.parallelize(
      List(
        Row.fromSeq(Seq(1.toString)),
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    assert(r.>=(r1))

  }

  @Test
  def testRddLeqOK() {


    val r = sc.parallelize(
      List(
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq(1.toString)),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    val r1 = sc.parallelize(
      List(
        Row.fromSeq(Seq(1.toString)),
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    assert(r.<=(r1))

  }

  @Test
  def testRddCmpErr1() {


    val r = sc.parallelize(
      List(
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq()),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    val r1 = sc.parallelize(
      List(
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    assert(r.compare(r1) == SCHEMAS_MATCH_ERR)

  }


  @Test
  def testRddCmpErr2() {


    val r = sc.parallelize(
      List(
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq()),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    val r1 = sc.parallelize(
      List(
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    assert(r.compare(r1) == DF1_BIGGER_THAN_DF2)
    assert(r.>(r1))

  }


  @Test
  def testRddCmpErr3() {


    val r = sc.parallelize(
      List(
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq()),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    val r1 = sc.parallelize(
      List(
        Row.fromSeq(Seq()),
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    assert(r.compare(r1) == DF2_BIGGER_THAN_DF1)
    assert(r.<(r1))

  }

  @Test
  def testRddCmpErr5() {


    val r = sc.parallelize(
      List(
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq("NO")),
        Row.fromSeq(Seq()),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    val r1 = sc.parallelize(
      List(
        Row.fromSeq(Seq()),
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("I", "LIKE", "TRAINS")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    assert(r.compare(r1) == DF1_AND_DF2_ROWS_DIFF)

  }


  @Test
  def testRddCmpErr5SameSize() {


    val r = sc.parallelize(
      List(
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq("NO")),
        Row.fromSeq(Seq()),
        Row.fromSeq(Seq(2.toString)),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    val r1 = sc.parallelize(
      List(
        Row.fromSeq(Seq()),
        Row.fromSeq(Seq("oui", "non")),
        Row.fromSeq(Seq("Yes")),
        Row.fromSeq(Seq("SUSS", "MOE")),
        Row.fromSeq(Seq("SUSS", "MOE", "BATER")),
        Row.fromSeq(Seq("I", "LIKE", "TRAINS")),
        Row.fromSeq(Seq("Test", "Test1"))
      )
    )

    assert(r.compare(r1) == DF1_AND_DF2_ROWS_DIFF)

  }

}
