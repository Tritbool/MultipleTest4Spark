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


import java.sql.{Date, Timestamp}

import com.tritcorp.mt4s.DebugDatasetBase
import com.tritcorp.mt4s.logger.DebugMode.{DEBUG, INFO, LogLevel, WARN}
import org.apache.commons.lang3.StringUtils
import org.apache.spark.sql.catalyst.util.DateTimeUtils
import org.apache.spark.sql.DataFrame
import com.tritcorp.mt4s.Constants._

/**
  * DebugDF encapsulates a DataFrame and adds logging and debugging tools to it.
  *
  * @param df the DataFrame to debug
  */
class DebugDF(var df: DataFrame) extends DebugDatasetBase with Ordered[DataFrame] {


  ss = df.sparkSession
  sc = ss.sparkContext
  sqlContext = ss.sqlContext
  logLvl = WARN

  logger.debug("Current Spark app is : " + ss.sparkContext.appName)

  setLogLevel(logLvl)


  /** Taken from Spark DataSet.scala code in order to allow to print dataframes in logs.
    *
    * @param df       the DataFrame to show
    * @param _numRows the number of rows tu display
    * @param truncate the maximum number of rows to display
    * @return the df DataFrame as a String
    */
  private def showString(df: DataFrame, _numRows: Int, truncate: Int = 20): String = {

    val numRows = _numRows.max(0)
    val takeResult = df.take(numRows + 1)
    val hasMoreData = takeResult.length > numRows
    val data = takeResult.take(numRows)
    val schema = df.schema
    lazy val timeZone =
      DateTimeUtils.getTimeZone(ss.sessionState.conf.sessionLocalTimeZone)

    // For array values, replace Seq and Array with square brackets
    // For cells that are beyond `truncate` characters, replace it with the
    // first `truncate-3` and "..."
    val rows: Seq[Seq[String]] = schema.fieldNames.toSeq +: data.map { row =>
      row.toSeq.map { cell =>
        val str = cell match {
          case null => "null"
          case binary: Array[Byte] => binary.map("%02X".format(_)).mkString("[", " ", "]")
          case array: Array[_] => array.mkString("[", ", ", "]")
          case seq: Seq[_] => seq.mkString("[", ", ", "]")
          case d: Date =>
            DateTimeUtils.dateToString(DateTimeUtils.fromJavaDate(d))
          case ts: Timestamp =>
            DateTimeUtils.timestampToString(DateTimeUtils.fromJavaTimestamp(ts), timeZone)
          case _ => cell.toString
        }
        if (truncate > 0 && str.length > truncate) {
          // do not show ellipses for strings shorter than 4 characters.
          if (truncate < 4) str.substring(0, truncate)
          else str.substring(0, truncate - 3) + "..."
        } else {
          str
        }
      }: Seq[String]
    }

    val sb = new StringBuilder
    val numCols = schema.fieldNames.length

    // Initialise the width of each column to a minimum value of '3'
    val colWidths = Array.fill(numCols)(3)

    // Compute the width of each column
    for (row <- rows) {
      for ((cell, i) <- row.zipWithIndex) {
        colWidths(i) = math.max(colWidths(i), cell.length)
      }
    }

    // Create SeparateLine
    val sep: String = colWidths.map("-" * _).addString(sb, "+", "+", "+\n").toString()

    // column names
    rows.head.zipWithIndex.map { case (cell, i) =>
      if (truncate > 0) {
        StringUtils.leftPad(cell, colWidths(i))
      } else {
        StringUtils.rightPad(cell, colWidths(i))
      }
    }.addString(sb, "|", "|", "|\n")

    sb.append(sep)

    // data
    rows.tail.map {
      _.zipWithIndex.map { case (cell, i) =>
        if (truncate > 0) {
          StringUtils.leftPad(cell.toString, colWidths(i))
        } else {
          StringUtils.rightPad(cell.toString, colWidths(i))
        }
      }.addString(sb, "|", "|", "|\n")
    }

    sb.append(sep)

    // For Data that has more than "numRows" records
    if (hasMoreData) {
      val rowsString = if (numRows == 1) "row" else "rows"
      sb.append(s"only showing top $numRows $rowsString\n")
    }

    "\n" + sb.toString()
  }

  /**
    * Show the embedded dataframe in the logger at debug level
    *
    * @param numRows the number of rows to show
    * @param debug   set to true if you want to have the logger in debug mode for printing
    */
  def showDebug(numRows: Int = 20, debug: Boolean = false): Unit = {
    if (debug) {
      setLogLevel(DEBUG)
      logger.debug(showString(df, numRows, numRows))
    }
    else {
      setLogLevel(INFO)
      logger.info(showString(df, numRows, numRows))
    }
    setLogLevel(logLvl)
  }


  /**
    * Compares the embedded DataFrame with the DataFrame that is passed as parameter
    *
    * @param that the DataFrame to compare with the embedded DataFrame
    * @return
    * 0 (Constants.DF_EQUAL) if dfs are semantically equal
    * 1 (Constants.SCHEMAS_MATCH_ERR) if dfs schemas don't match
    * 2 (Constants.DF1_BIGGER_THAN_DF2) if the embedded df has more rows than that
    * 3 (Constants.DF2_BIGGER_THAN_DF1) if that has more rows than the embedded df
    * 5 (Constants.DF1_AND_DF2_ROWS_DIFF) if both df have rows that don't exist in the other
    * 4 (Constants.UNKNOWN_ERR) if an unknown error occurred
    */
  override def compare(that: DataFrame): Int = {

    val ROWS_TO_SHOW = 100

    var thatReordered: DataFrame = that
    df.cache()
    thatReordered.cache()

    var result:Int=0

    // Compare schema sizes.
    if (df.schema.lengthCompare(that.schema.size) != 0) {
      logger.error("Schemas sizes are different :")
      logger.error("DF1 : " + df.columns.mkString("[", ";", "]"))
      logger.error("DF2 : " + that.columns.mkString("[", ";", "]"))
      result=SCHEMAS_MATCH_ERR
    }
    else {

      val thisSortedCol = df.columns.sorted
      val thatSortedCol = that.columns.sorted
      var schemasCheck = DF_EQUAL
      if (thisSortedCol.sameElements(thatSortedCol)) {
        logger.warn("Schemas contain the same columns : " + df.columns.mkString("[", ";", "]"))
      }
      else {
        logger.error("Schemas are different :")
        logger.error("DF1 : " + thisSortedCol.mkString("[", ";", "]"))
        logger.error("DF2 : " + thatSortedCol.mkString("[", ";", "]"))
        schemasCheck = SCHEMAS_MATCH_ERR
      }
      if (schemasCheck == DF_EQUAL) {
        if (!df.columns.sameElements(that.columns)) {

          logger.warn("Schemas are not ordered in the same way : ")
          logger.warn("DF1 : " + df.columns.mkString("[", ";", "]"))
          logger.warn("DF2 : " + that.columns.mkString("[", ";", "]"))

          thatReordered = that.select(df.columns.head, df.columns.tail: _*)
        }

        val countDf1 = df.count()
        val countDf2 = that.count()
        val diff1 = df.except(thatReordered)
        val diff1Count = diff1.count

        val diff2 = thatReordered.except(df)
        val diff2Count = diff2.count

        if (countDf1 != countDf2) {
          logger.error("Dataframes have different size :")
          logger.error("DF1 : " + countDf1 + "rows")
          logger.error("DF2 : " + countDf2 + "rows")

        }
        if (diff1Count > 0 || diff2Count > 0) {

          var df1SupDf2 = DF_EQUAL
          var df2SupDf1 = DF_EQUAL

          if (diff1Count > 0) {
            logger.error("DF1 has " + diff1Count + " line(s) that don't exist in DF2")
            logger.error(showString(diff1, ROWS_TO_SHOW, ROWS_TO_SHOW))
            df1SupDf2 = DF1_BIGGER_THAN_DF2
          }


          if (diff2Count > 0) {
            logger.error("DF2 has " + diff2Count + " line(s) that don't exist in DF1")
            logger.error(showString(diff2, ROWS_TO_SHOW))
            df2SupDf1 = DF2_BIGGER_THAN_DF1
          }

          result=df1SupDf2 + df2SupDf1

        } else {
          val equality = df.union(thatReordered).except(df.intersect(thatReordered))

          if (equality.count == 0) {
            logger.warn("Dataframes contents are identical")
            result=DF_EQUAL
          }
          else {result=UNKNOWN_ERR}
        }
      }
      else {
        result=schemasCheck
      }
    }
    df.unpersist()
    thatReordered.unpersist()
    result
  }

  override def compareTo(that: DataFrame): Int = {
    compare(that)
  }

  override def <(that: DataFrame): Boolean = {
    compare(that) == DF2_BIGGER_THAN_DF1
  }

  override def >(that: DataFrame): Boolean = {
    compare(that) == DF1_BIGGER_THAN_DF2
  }

  override def <=(that: DataFrame): Boolean = {
    val res = compare(that)
    res == DF2_BIGGER_THAN_DF1 || res == DF_EQUAL
  }

  override def >=(that: DataFrame): Boolean = {
    val res = compare(that)
    res == DF1_BIGGER_THAN_DF2 || res == DF_EQUAL
  }


  /**
    * transforms DebugDG.compare's result to a boolean value
    *
    * @param that the df to compare to the embedded df
    * @return true if dfs are equal else false.
    */
  def equalsDF(that: DataFrame): Boolean = {
    if (compare(that) == DF_EQUAL) true else false
  }

}
