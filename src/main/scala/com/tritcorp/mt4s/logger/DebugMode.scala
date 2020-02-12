package com.tritcorp.mt4s.logger
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

/**
  * Enumeration used to encapsulate log levels.
  */
object DebugMode {
  sealed trait LogLevel
  case object ALL extends LogLevel{
    override def toString:String="ALL"
  }
  case object TRACE extends LogLevel{
    override def toString:String="TRACE"
  }
  case object DEBUG extends LogLevel{
    override def toString:String="DEBUG"
  }
  case object INFO extends LogLevel{
    override def toString:String="INFO"
  }
  case object WARN extends LogLevel{
    override def toString:String="WARN"
  }
  case object ERROR extends LogLevel{
    override def toString:String="ERROR"
  }
  case object FATAL extends LogLevel{
    override def toString:String="FATAL"
  }
  case object OFF extends LogLevel{
    override def toString:String="OFF"
  }
  val debugModes = Seq(ALL, TRACE, DEBUG, INFO, WARN,ERROR,FATAL,OFF)
}
