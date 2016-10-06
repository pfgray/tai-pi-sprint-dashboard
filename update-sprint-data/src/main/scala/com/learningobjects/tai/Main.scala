package com.learningobjects.tai

object Main {

  /**
    *             (^-^*)/
    */
  def main(args: Array[String]): Unit = {

    val (username, password) = readCredentials


  }

  /**
    * Ask user for credentials from stdin.
    */
  def readCredentials = {
    print("Username: ")
    val username = Console.in.readLine()

    print("Password: ")
    val password = System.console().readPassword()

    (username, new String(password))
  }
}