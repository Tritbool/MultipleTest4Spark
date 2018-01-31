package com.tritcorp.mt4s

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
object Constants {

  /**
    * DataFrames/RDD Comparison constants
    */
  final val DF_EQUAL = 0
  final val SCHEMAS_MATCH_ERR = 1
  final val DF1_BIGGER_THAN_DF2 = 2
  final val DF2_BIGGER_THAN_DF1 = 3
  final val DF1_AND_DF2_ROWS_DIFF = 5
  final val UNKNOWN_ERR = 4

}
