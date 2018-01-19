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
import com.tritcorp.mt4s.junit._
import com.tritcorp.mt4s.logger.DebugMode.{ERROR, WARN}
import com.tritcorp.mt4s.dfTools.DataframeTools._

class TestingScalatest extends FlatSpecTest{

  setLogLevel(WARN)

  "DFs" should "be identical" in {
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


}
