package com.tritcorp.mt4s
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
object ConsoleHeader {

  private var headerShown: Boolean = false;

  private final val VERSION = "2.0-beta"
  private final val LICENSE = "LICENSE APACHE 2.0"

  def printHeader(): Unit = {
    if (!headerShown) {
      System.err.println("")
      System.err.println(".------------------------------------------------------------------------------------------------.")
      System.err.println(".                                                                                                .")
      System.err.println(".                                         Welcome to                                             .")
      System.err.println(".                                                                                                .")
      System.err.println(".    MMMMMMMM               MMMMMMMMTTTTTTTTTTTTTTTTTTTTTTT    444444444     SSSSSSSSSSSSSSS     .")
      System.err.println(".    M:::::::M             M:::::::MT:::::::::::::::::::::T   4::::::::4   SS:::::::::::::::S    .")
      System.err.println(".    M::::::::M           M::::::::MT:::::::::::::::::::::T  4:::::::::4  S:::::SSSSSS::::::S    .")
      System.err.println(".    M:::::::::M         M:::::::::MT:::::TT:::::::TT:::::T 4::::44::::4  S:::::S     SSSSSSS    .")
      System.err.println(".    M::::::::::M       M::::::::::MTTTTTT  T:::::T  TTTTTT4::::4 4::::4  S:::::S                .")
      System.err.println(".    M:::::::::::M     M:::::::::::M        T:::::T       4::::4  4::::4  S:::::S                .")
      System.err.println(".    M:::::::M::::M   M::::M:::::::M        T:::::T      4::::4   4::::4   S::::SSSS             .")
      System.err.println(".    M::::::M M::::M M::::M M::::::M        T:::::T     4::::444444::::444  SS::::::SSSSS        .")
      System.err.println(".    M::::::M  M::::M::::M  M::::::M        T:::::T     4::::::::::::::::4    SSS::::::::SS      .")
      System.err.println(".    M::::::M   M:::::::M   M::::::M        T:::::T     4444444444:::::444       SSSSSS::::S     .")
      System.err.println(".    M::::::M    M:::::M    M::::::M        T:::::T               4::::4              S:::::S    .")
      System.err.println(".    M::::::M     MMMMM     M::::::M        T:::::T               4::::4              S:::::S    .")
      System.err.println(".    M::::::M               M::::::M      TT:::::::TT             4::::4  SSSSSSS     S:::::S    .")
      System.err.println(".    M::::::M               M::::::M      T:::::::::T           44::::::44S::::::SSSSSS:::::S    .")
      System.err.println(".    M::::::M               M::::::M      T:::::::::T           4::::::::4S:::::::::::::::SS     .")
      System.err.println(".    MMMMMMMM               MMMMMMMM      TTTTTTTTTTT           4444444444 SSSSSSSSSSSSSSS       .")
      System.err.println(".                                                                                                .")
      System.err.println(".                                                                                                .")
      System.err.println(".                                                                                                .")
      System.err.println(".      VERSION " + VERSION + "                                                                               .")
      System.err.println(".                                                                                                .")
      System.err.println(".------------------------------------------------------------------------------------------------.")
      System.err.println("")
      System.err.println("MT4S  Copyright (C) 2020  Gauthier LYAN")
      System.err.println(LICENSE)
      System.err.println("This program comes with ABSOLUTELY NO WARRANTY")
      System.err.println("This is free software, and you are welcome to redistribute it under certain conditions")
      System.err.println("")
      System.err.println("")

      headerShown = true;
    }
  }

}
