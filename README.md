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

- Widely available
- per class log level

## DataFrame tools

- load csv 
- compare df
- debugDF
- implicit conversion

## RDD tools

- load any file
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

# Tests

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
