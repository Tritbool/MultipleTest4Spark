package com.tritcorp.mt4s.utils

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
