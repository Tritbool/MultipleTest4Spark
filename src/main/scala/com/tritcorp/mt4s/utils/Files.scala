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

  /**
    * returns a stream contaning all files within the directory f, by recursively exploring it and its subfolders
    *
    * @param f , a directory to explore as a java.io.File
    * @return a Stream contaning all the files within the directory f
    */
  def getFileTree(f: File): Stream[File] =
    f #:: (if (f.isDirectory) f.listFiles().toStream.flatMap(getFileTree)
    else Stream.empty)


  /**
    * returns a subset of the File Stream tree, with all files which name's start with the String start
    *
    * @param tree       a Stream of File to explore
    * @param start      the String to match with File's names
    * @param ignoreCase Sets the case sensitivity (true or false)
    * @return A subset of the File Stream tree, with all files which name's start with the String start
    */
  def getAllFilesStartingWith(tree: Stream[File], start: String, ignoreCase: Boolean = false): Stream[File] = {
    var res: Stream[File] = Stream.empty
    if (ignoreCase) {
      res = tree.filter(_.getName.toLowerCase().startsWith(start.toLowerCase()))
    }
    else {
      res = tree.filter(_.getName.startsWith(start))
    }
    res.force
  }

  /**
    * returns a subset of the File Stream tree, with all files which name's end with the String end
    *
    * @param tree       a Stream of File to explore
    * @param end        the String to match with File's names
    * @param ignoreCase Sets the case sensitivity (true or false)
    * @return A subset of the File Stream tree, with all files which name's end with the String end
    */
  def getAllFilesEndingWith(tree: Stream[File], end: String, ignoreCase: Boolean = false): Stream[File] = {
    var res: Stream[File] = Stream.empty
    if (ignoreCase) {
      res = tree.filter(_.getName.toLowerCase().endsWith(end.toLowerCase()))
    }
    else {
      res = tree.filter(_.getName.endsWith(end))
    }
    res.force
  }

  /**
    * returns a subset of the File Stream tree, with all files which name's contain the String content
    *
    * @param tree       a Stream of File to explore
    * @param content    the String to match with File's names
    * @param ignoreCase Sets the case sensitivity (true or false)
    * @return A subset of the File Stream tree, with all files which name's contain the String content
    */
  def getAllFilesWith(tree: Stream[File], content: String, ignoreCase: Boolean = false): Stream[File] = {
    var res: Stream[File] = Stream.empty
    if (ignoreCase) {
      res = tree.filter(_.getName.toLowerCase().contains(content.toLowerCase()))
    }
    else {
      res = tree.filter(_.getName.contains(content))
    }
    res.force
  }

  /**
    * returns a subset of the File Stream tree, with all files which name's match the String toFind
    *
    * @param tree       a Stream of File to explore
    * @param toFind     the String to match with File's names
    * @param ignoreCase Sets the case sensitivity (true or false)
    * @return A subset of the File Stream tree, with all files which name's Match the String toFind
    */
  def getAllFilesEquals(tree: Stream[File], toFind: String, ignoreCase: Boolean = false): Stream[File] = {
    var res: Stream[File] = Stream.empty
    if (ignoreCase) {
      res = tree.filter(_.getName.equalsIgnoreCase(toFind))
    } else {
      res = tree.filter(_.getName.equals(toFind))
    }
    res.force
  }

}
