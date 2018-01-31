package com.tritcorp.mt4s
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
object ConsoleHeader {

  private var headerShown: Boolean = false;

  private final val VERSION = "0.1"
  private final val LICENSE = "LICENSE GNU GPL 3.0"

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
      System.err.println("MT4S  Copyright (C) 2018  Gauthier LYAN")
      System.err.println(LICENSE)
      System.err.println("This program comes with ABSOLUTELY NO WARRANTY")
      System.err.println("This is free software, and you are welcome to redistribute it under certain conditions")
      System.err.println("")
      System.err.println("")

      headerShown = true;
    }
  }

}
