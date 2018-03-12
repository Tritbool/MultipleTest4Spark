package com.tritcorp.mt4s.utils
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
import java.io.File

/**
  * An utility to parse folders and find files within it
  */
object Files {

  def getFileTree(f: File): Stream[File] =
    f #:: (if (f.isDirectory) f.listFiles().toStream.flatMap(getFileTree)
    else Stream.empty)

  def getAllFilesStartingWith(fs: Stream[File], start: String): Stream[File] = {
    fs.filter(_.getName.startsWith(start))
  }

  def getAllFilesEndinggWith(fs: Stream[File], end: String): Stream[File] = {
    fs.filter(_.getName.endsWith(end))
  }

  def getAllFilesWith(fs: Stream[File], content: String): Stream[File] = {
    fs.filter(_.getName.contains(content))
  }

  def getAllFilesEquals(fs: Stream[File], toFind: String, ignoreCase: Boolean = false): Stream[File] = {
    if (ignoreCase)
      fs.filter(_.getName.equalsIgnoreCase(toFind))
    else
      fs.filter(_.getName.equals(toFind))
  }

}
