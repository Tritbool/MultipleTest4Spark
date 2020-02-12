package com.tritcorp.mt4s

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
