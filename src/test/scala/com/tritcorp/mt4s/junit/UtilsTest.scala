package com.tritcorp.mt4s.junit

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

import com.tritcorp.mt4s.utils.Files
import org.junit.Test


class UtilsTest extends JunitTest {

  @Test
  def testTree():Unit={
    val c = getClass
    val folder = c.getResource("/subfolder/").toURI.toString.replace("file:","")

    val f=new File(folder)

    assert(Files.getFileTree(f).nonEmpty)
  }

  @Test
  def testStartWith():Unit={
    val c = getClass
    val folder = c.getResource("/subfolder/").toURI.toString.replace("file:","")
    val f=new File(folder)
    val tree = Files.getFileTree(f)
    val tree2 = Files.getAllFilesStartingWith(tree,"test").toArray
    logger.warn("tree: "+ tree2.length)
    assert(tree2.length==2)
    assert(tree2(0).getName=="test1.data" || tree2(1).getName=="test1.data")
    assert(tree2(0).getName=="test2.data" || tree2(1).getName=="test2.data")

  }

  @Test
  def testStartWithIgnoreCase():Unit={
    val c = getClass
    val folder = c.getResource("/subfolder/").toURI.toString.replace("file:","")
    val f=new File(folder)
    val tree = Files.getFileTree(f)
    val tree2 = Files.getAllFilesStartingWith(tree,"TeST",ignoreCase = true).toArray
    logger.warn("tree: "+ tree2.length)
    assert(tree2.length==2)
    assert(tree2(0).getName=="test1.data" || tree2(1).getName=="test1.data")
    assert(tree2(0).getName=="test2.data" || tree2(1).getName=="test2.data")

  }

  @Test
  def testEndWith():Unit={
    val c = getClass
    val folder = c.getResource("/subfolder/").toURI.toString.replace("file:","")
    val f=new File(folder)
    val tree = Files.getFileTree(f)
    val tree2 = Files.getAllFilesEndingWith(tree,"non").toArray
    logger.warn("tree: "+ tree2.length)
    assert(tree2.length==1)
    assert(tree2(0).getName=="oui.non")

  }

  @Test
  def testEndWithIgnoreCase():Unit={
    val c = getClass
    val folder = c.getResource("/subfolder/").toURI.toString.replace("file:","")
    val f=new File(folder)
    val tree = Files.getFileTree(f)
    val tree2 = Files.getAllFilesEndingWith(tree,"nOn", ignoreCase = true).toArray
    logger.warn("tree: "+ tree2.length)
    assert(tree2.length==1)
    assert(tree2(0).getName=="oui.non")

  }

  @Test
  def testEqualWith():Unit={
    val c = getClass
    val folder = c.getResource("/subfolder/").toURI.toString.replace("file:","")
    val f=new File(folder)
    val tree = Files.getFileTree(f)
    val tree2 = Files.getAllFilesEquals(tree,"oui.non").toArray
    logger.warn("tree: "+ tree2.length)
    assert(tree2.length==1)
    assert(tree2(0).getName=="oui.non")

  }

  @Test
  def testEqualWithCaseInsensitive():Unit={
    val c = getClass
    val folder = c.getResource("/subfolder/").toURI.toString.replace("file:","")
    val f=new File(folder)
    val tree = Files.getFileTree(f)
    val tree2 = Files.getAllFilesEquals(tree,"OUI.Non",ignoreCase = true).toArray
    logger.warn("tree: "+ tree2.length)
    assert(tree2.length==1)
    assert(tree2(0).getName=="oui.non")

  }

  @Test
  def testAllFilesWith():Unit={
    val c = getClass
    val folder = c.getResource("/subfolder/").toURI.toString.replace("file:","")
    val f=new File(folder)
    val tree = Files.getFileTree(f)
    val tree2 = Files.getAllFilesWith(tree,".").toArray
    logger.warn("tree: "+ tree2.length)
    assert(tree2.length==3)

  }

  @Test
  def testAllFilesWithCaseInsensitive():Unit={
    val c = getClass
    val folder = c.getResource("/subfolder/").toURI.toString.replace("file:","")
    val f=new File(folder)
    val tree = Files.getFileTree(f)
    val tree2 = Files.getAllFilesWith(tree,".daTA", ignoreCase = true).toArray
    logger.warn("tree: "+ tree2.length)
    assert(tree2.length==2)

  }


}
