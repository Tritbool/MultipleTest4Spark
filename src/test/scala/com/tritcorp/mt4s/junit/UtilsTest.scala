package com.tritcorp.mt4s.junit

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
