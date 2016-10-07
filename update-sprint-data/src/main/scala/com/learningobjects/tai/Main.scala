package com.learningobjects.tai

import java.net.HttpCookie

object Main {

  /**
    *     (^-^*)/
    */
  def main(args: Array[String]): Unit = {

    val (username, password) = readCredentials

    implicit val cookies: IndexedSeq[HttpCookie] = JIRA.authenticate(username, password)

    val sprint = JIRA.activeSprint()
    val issues = JIRA.issues(sprint.id)
    issues.foreach(i => println(s"DEBUG: ${i}"))
  }

  /**
    *     ( ˘▽˘)っ♨
    */
  def readCredentials = {
    print("Username: ")
    val username = Console.in.readLine()

    print("Password: ")
    val password = System.console().readPassword()

    (username, new String(password))
  }

}