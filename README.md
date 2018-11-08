# MT4S - Mutliple Tests 4 Spark #
[![Build Status](https://travis-ci.org/Tritbool/MultipleTest4Spark.svg?branch=master)](https://travis-ci.org/Tritbool/MultipleTest4Spark)
[![codecov](https://codecov.io/gh/Tritbool/MultipleTest4Spark/branch/master/graph/badge.svg)](https://codecov.io/gh/Tritbool/MultipleTest4Spark)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MultipleTest4Spark&metric=alert_status)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MultipleTest4Spark&metric=bugs)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MultipleTest4Spark&metric=code_smells)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MultipleTest4Spark&metric=sqale_rating)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MultipleTest4Spark&metric=reliability_rating)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MultipleTest4Spark&metric=security_rating)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MultipleTest4Spark&metric=sqale_index)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MultipleTest4Spark&metric=vulnerabilities)
----------

**MT4S is an under development framework that allows Scala/Spark developpers to rapidly instantiate tests classes for their Spark applications through Junit and/or ScalaTest.**

## It provides: ##

	- Scalatest Spark ready classes (Not all types implemented yet)
	- Simple CSV load 
	- Simple TextFile load
	- Junit Spark ready classes
	- DatFrame comparison system
	- RDD Comparison system
	- Logging system (without file storing)
	- Implicit DataFrame to DebugDF conversion
	- Implicit RDD to DataFrame and RDD[String] to RDD[Row] conversions
	- Full SBT support 
	
**N.B : No Maven support**


# How to use MT4S
MT4S is thought to be used as an help for Spark developments. According to this, I advise to use an IDE to enjoy the whole capabilities of the tool.

## Get it
 
 I'd really recommend to download the framwork from source  and compile it on your system using sbt. Not only the sources will help you to have more details on the features available, but it will also be more simple to make use of it in your projects.
 
***
### Compile from source 

**Requirements**

- SBT 0.13+
- Scala 2.11
- Spark >= 2.2.1

***

>Just clone/get it from git somewhere on your computer and open a terminal in MT4S-master folder.
Then type in :

```
sbt test
```

 >Please make sure that all the tests are passing.
If OK :

```
sbt clean package publishLocal
```
>MT4S is now available in your local sbt repository.

***
#### Use it in a SBT project
Add the following library dependency in the project's **build.sbt** file:
```
"com.tritcorp.exp" %% "mt4s" % "0.1" % "test"
```
** N.B **: You may also need the followind dependency in some cases :

```
"com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
```

# Features

## Logger

**LOG DOESN'T STORE IN FILE**

- Widely available
- per class log level
```scala
/* choose a value for lvl between ALL, TRACE, DEBUG, INFO, WARN,ERROR,FATAL,OFF */
 setLogLevel(lvl)
```
**Usage**
```scala
logger.debug("Your message")
logger.info("Your message")
logger.warn("Your message")
logger.error("Your message")
// ...

```

## Resources

#### About files loading

> ***MT4S provides a way to load data from local files. However, by default,  it is only allowed to load data from files located in resources folder ***

```
project
|
│   README.md
│   LICENSE.txt    
│
└───src
│   │
│   |───main
│   |   │   App.scala
│   |   └───resources
│   |   │   ...
|   |
|   |
│   └───test
│   |   │   AppTest.scala
│   |   └───resources
│   |   │   |    File_1.txt
│   |   │   |    File_2.csv
│   |   │   |    ...

```
> From here the path to use for data files would merely be "/File_X.csv"

## DataFrame tools

* ##### to access tools
```scala
import com.tritcorp.mt4s.dfTools.DataframeTools
```
* ##### to access implicits
```scala
import com.tritcorp.mt4s.dfTools.DataframeTools._
```

- load csv file from resources folder

**resources file is set as root**
```scala
/* DataframeTools.readCsvLocal
loads csv directly (and only) from your src/test/resources folder : 
- path is the path to use within the resources folder
- delimiter is the delimiter of your csv file
- encoding is the encoding used for your csv file
- inferSchema allows Spark to infer datatypes when loading if set to "true"(as a String)
- nullValue is the value that will be used to replace null values in data
*/

def readCsvLocal(path: String, delimiter: String = ";", encoding: String = "UTF-8", inferSchema:String = "false", nullValue:String = ""): Option[DataFrame] 
```
**Usage**
```scala
// EXAMPLE : loading src/test/resources/myFile.csv

readCsvLocal("/myFile.csv")

```

- DebugDF

A Spark DataFrame overload, offering debug features :

```scala
/**
  * DebugDF encapsulates a DataFrame and adds logging and debugging tools to it.
  *
  * @param df the DataFrame to debug
  */
class DebugDF(var df: DataFrame) extends DebugDatasetBase with Ordered[DataFrame]{

  /**
    * Show the embedded dataframe in the logger at debug level
    *
    * @param numRows the number of rows to show
    * @param debug set to true if you want to have the logger in debug mode for printing
    */
  def showDebug(numRows: Int = 20, debug:Boolean = false)
  
  def compareTo(that: DataFrame): Int 
  
  def <(that: DataFrame): Boolean
  
  def >(that: DataFrame): Boolean
  
  def <=(that: DataFrame): Boolean
  
  def >=(that: DataFrame): Boolean
    ...
  } 
```

- compare df
```scala

/** FROM DebugDF
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
  def compare(that: DataFrame): Int 
```

- implicit conversion
```scala

/**
    * Allows implicit conversion of DataFrame to DebugDF
    * @param df the DataFrame to test
    * @return df encapsulated in a new DebugDF 
    */
  implicit def df2DebugDF(df:DataFrame):DebugDF={
    new DebugDF(df)
  }
```
**Usage**
```scala
 /* Example */
 val df:DataFrame
 
 //With implicit, you can directly call DebugDF functionalities from a standard DataFrame
 df.showDebug()
 df.compare(anotherDf)
 //...
  ```
## RDD tools

* ##### to access tools
```scala
import com.tritcorp.mt4s.rddTools.RddTools
```
* ##### to access implicits
```scala
 import com.tritcorp.mt4s.rddTools.RddTools._
```


- load any file from resources folder
```scala
/**
    * Loads a file from the resources folder
    * @param file The file's path in the resources folder
    * @return a RDD[String] with the file's content
    */
  def readRddLocal(file:String):RDD[String]
```

 ```scala

  ```
  
- debugRDD

A Spark RDD overload, offering debug features :

```scala
class DebugRDD(rdd: RDD[Row]) extends DebugDatasetBase with Ordered[RDD[Row]]{
  /**
    * Compares the embedded rdd with the rdd that is passed as parameter
    *
    * @see com.tritcorp.mt4s.dfTools.DebugDf.compare
    * @param that the rdd to compare with the embedded rdd
    * @return
    * 0 if rdds are semantically equal
    * 1 if rdds schemas don't match
    * 2 if the embedded rdd has more rows than that
    * 3 if that has more rows than the embedded rdd
    * 5 if both rdd have rows that don't exist in the other
    * 4 if an unknown error occurred
    */
   def compare(that: RDD[Row]): Int
  
   def equalsRDD(that:RDD[Row]):Boolean
  
   def compareTo(that: RDD[Row]): Int
  
   def <(that: RDD[Row]): Boolean
   
   def >(that: RDD[Row]): Boolean
   
   def <=(that: RDD[Row]): Boolean
   
   def >=(that: RDD[Row]): Boolean
   
   /**
       * Prepares a RDD for comparison
       * In order to do so, a schema is infered from the longest row of the RDD, then each smaller row is filled with phony data.
       * for example, a rdd like
       * 
       * A B C D 
       * 1 2
       * x y z
       * 
       * would become the following DataFrame
       * 
       * +-----+-----+-----+-----+
       * | c_0 | c_1 | c_2 | c_3 |
       * +-----+-----+-----+-----+
       * |  A  |  B  |  C  |  D  |
       * |  1  |  2  | n/a | n/a |
       * |  x  |  y  |  z  | n/a |
       * +-----+-----+-----+-----+
       *
       * 
        * @param rddP A RDD[Row] to prepare
       * @return a DataFrame infered from rddP
       **/
     private def prepareRdd(rddP: RDD[Row]): DataFrame 
}
```
- compare RDD[Row]

To compare RDD, they are transformed into DataFrame.

In order to do so, a schema is infered from the longest row of the RDD, then each smaller row is filled with phony data.
for example, a rdd like
```
A B C D 
1 2
x y z
```
would become the following DataFrame
```
+-----+-----+-----+-----+
| c_0 | c_1 | c_2 | c_3 |
+-----+-----+-----+-----+
|  A  |  B  |  C  |  D  |
|  1  |  2  | n/a | n/a |
|  x  |  y  |  z  | n/a |
+-----+-----+-----+-----+
```
Then it is easy to use **DebugDF.compare** to compare RDDs

- Implicit RDD[String] to RDD[Row] conversion

Allows to load a text file then compare it easily with a RDD[Row]

**Usage**

``` scala
val expected:RDD[Row]=...
val loaded:RDD[String] = RddTools.readRddLocal("/rddLoad.txt")

//Implicit conversion of loaded from RDD[String] to RDD[Row]
assert(expected.equalsRDD(loaded))
```

- implicit conversion from rdd contaning complete csv data to a DataFrame

``` scala
/**
    * Converts a RDD that contains csv info into a dataframe
    * - The first row of the rdd MUST be the csv header
    * - The rdd rows must all have the same length
    * @param rdd the rdd to convert
    * @return the dataframe from the rdd
    */
  implicit def rddCsvToDF(rdd: RDD[String]): DataFrame
```
**Usage**

``` scala

//RddTools.csvDelimiter must be changed according to the delimiter of your csv data

RddTools.csvDelimiter=","


// Both creating a RDD manually or loading a file work

val res: RDD[String] = sc.parallelize(Seq(
        "Name,surname,age",
        "Maria,Doe,41",
        "John,Nguyen,23"))

val res:RDD[String]=RddTools.readRddFree("/path/to/nameSurnameAge.txt)

res.show()

/** OUTPUT =
  * +-------+---------+-----+
  * | name  | surname | age |
  * +-------+---------+-----+
  * | Maria |   Doe   |  41 |
  * |  Joe  | Nguyen  |  23 |
  * +-------+---------+-----+
  */

```

## Utilities

- Folder exploration to load files that are not from resources folder into RDD's
- Config object allows you to define the spark master, driver IP and driver host. Useful when yout firewall blocks random port access.
```scala
object Config {

  var MASTER="local[8]"
  var IP="127.0.0.1"
  var HOST="127.0.0.1"

}
```
# Tests examples

## Unit Tests
```scala

import com.tritcorp.mt4s.logger.DebugMode._
import com.tritcorp.mt4s.dfTools.DataframeTools._
import com.tritcorp.mt4s.dfTools.DataframeTools
import com.tritcorp.mt4s.junit.JunitTest
import org.junit.Test

class TestingScalatest extends JunitTest{

// Set the log level to the following : DEBUG, INFO, WARN or ERROR
// Default is INFO
setLogLevel(WARN)

  @Test
  def myTest() ={
    val toTest:DataFrame = somecode.that.outputs.a.dataframe()

    //load a file from the resources folder
    val expectedResult:DataFrame = DataframeTools.readCsvLocal("/expected.csv",delimiter=",",encoding="UTF-8",inferSchema = "true",nullValue = "NULL").orNull
    assert(toTest.equalsDF(expectedResult))
  }
}
```
## Spec Tests
```scala

import com.tritcorp.mt4s.scalaTest.FlatSpecTest

import com.tritcorp.mt4s.logger.DebugMode._
import com.tritcorp.mt4s.dfTools.DataframeTools._
import com.tritcorp.mt4s.dfTools.DataframeTools

import com.tritcorp.mt4s.Constants._
import org.junit.Assert._

class TestingScalatest extends FlatSpecTest{

// Set the log level to the following : DEBUG, INFO, WARN or ERROR
// Default is INFO
setLogLevel(WARN)

  "Datasets" must "be identical" in {
    val toTest:DataFrame = somecode.that.outputs.a.dataframe()

    //load a file from the resources folder
    val expectedResult:DataFrame = DataframeTools.readCsvLocal("/expected.csv",delimiter=",",encoding="UTF-8",inferSchema = "true",nullValue = "NULL").orNull
    assert(toTest.equalsDF(expectedResult))
  }
  
  "Generated dataset" should "have less rows than expected" in{
    val toTest:DataFrame = somecode.that.outputs.a.dataframe()
    val expected:DataFrame = somecode.that.outputs.another.dataframe()
    
    //see Constants FILE for more info
    //see DebugDF.compare for doc
    assertEquals(DF2_BIGGER_THAN_DF1,toTest.compare(expected))
  }
}
```
## Features Tests
```scala
import com.tritcorp.mt4s.scalaTest.FeatureSpecTest

import com.tritcorp.mt4s.rddTools.RddTools
import com.tritcorp.mt4s.rddTools.RddTools._

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row}

class RDDToolsFeatureSpecExample extends FeatureSpecTest {
info("as a spark developer")
  info("I want to load a file from any folder")

  feature("Load a file and put its content into a RDD") {
    scenario("loading a file, verify it has been loaded") {

      val expected: RDD[Row] = sc.parallelize(
        List(
          Row.fromSeq(Seq("A B C 23 FG 42 FDP |e")),
          Row.fromSeq(Seq("1 2 3 4"))
        ))

      val loaded:RDD[String] = RddTools.readRddLocal("/path/to/rddLoad.txt")

      assert(expected.equalsRDD(loaded))

    }
  }
}
```
## LICENSE ##
**This software is distributed under the GNU GPL license.**
[https://www.gnu.org/licenses/](https://www.gnu.org/licenses/ "https://www.gnu.org/licenses/")

Copyright © 2018 Gauthier LYAN
