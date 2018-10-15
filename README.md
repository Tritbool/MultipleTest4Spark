# MT4S - Mutliple Tests 4 Spark #
[![Build Status](https://travis-ci.org/Tritbool/MT4S.svg?branch=master)](https://travis-ci.org/Tritbool/MT4S)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MT4S&metric=alert_status)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MT4S&metric=bugs)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MT4S&metric=code_smells)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MT4S&metric=sqale_rating)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MT4S&metric=reliability_rating)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MT4S&metric=security_rating)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MT4S&metric=sqale_index)
![Build Status](https://sonarcloud.io/api/project_badges/measure?project=MT4S&metric=vulnerabilities)
----------

**MT4S is an under development framework that allows Scala/Spark developpers to rapidly instantiate tests classes for their Spark applications through Junit and/or ScalaTest.**

N.B : The project is under construction, more features and documentation will be added with time. jar packages will be available as soon as a first stable version will be released

## It provides: ##

	- Scalatest Spark ready classes (Not all types implemented yet)
	- Simple CSV load methods.
	- Junit Spark ready classes
	- DatFrame comparison system
	- RDD Comparison system
	- Logging system (without file storing)
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
- Spark 2.2.1

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
## DataFrame tools

- load csv from resources

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
  override def compare(that: DataFrame): Int 
```

- debugDF

A Spark DataFrame overload, offering debug features like :

```scala
  /**
    * Show the embedded dataframe in the logger at debug level
    *
    * @param numRows the number of rows to show
    * @param debug set to true if you want to have the logger in debug mode for printing
    */
  def showDebug(numRows: Int = 20, debug:Boolean = false)
```

- implicit conversion
```scala
import com.tritcorp.mt4s.dfTools.DataframeTools.df2DebugDF 

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

- load any file from resources
 ```scala
 /* Example */
 val df:DataFrame
 
 //With implicit, you can directly call DebugDF functionalities from a standard DataFrame
 df.showDebug()
 df.compare(anotherDf)
 //...
  ```
- compare rdd
- debugRDD
- implicit conversion from rdd contaning complete csv data to a DataFrame

## Utilities

- Folder exploration to load specific files into RDD's
- Config object allows you to define the spark master, driver IP and driver host. Useful when yout firewall blocks random port access.


#### About CSV

> ***MT4S provides a way to load data from local csv files. However, by default,  it is only allowed to load data from files located in resources folder ***

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
│   |   │   |    File_1.csv
│   |   │   |    File_2.csv
│   |   │   |    ...

```
> From here the path to use for data files would merely be "/File_X.csv"

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

import com.tritcorp.mt4s.logger.DebugMode._
import com.tritcorp.mt4s.dfTools.DataframeTools._
import com.tritcorp.mt4s.dfTools.DataframeTools
import com.tritcorp.mt4s.scalaTest.FlatSpecTest
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

## LICENSE ##
**This software is distributed under the GNU GPL license.**
[https://www.gnu.org/licenses/](https://www.gnu.org/licenses/ "https://www.gnu.org/licenses/")

Copyright © 2018 Gauthier LYAN
