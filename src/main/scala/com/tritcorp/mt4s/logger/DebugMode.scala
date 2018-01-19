package com.tritcorp.mt4s.logger
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
