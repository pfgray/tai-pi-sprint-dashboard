package com.learningobjects.tai

import java.net.HttpCookie

object Main {

  /**
    *     (^-^*)/
    */
  def main(args: Array[String]): Unit = {

    val (username, password) = readCredentials

    implicit val cookies: IndexedSeq[HttpCookie] = Jira.authenticate(username, password)

    Jira.activeSprint.foreach(sprint => {
      val issues = Jira.issuesFromSprint(sprint.id)
      issues.foreach(i => println(s"DEBUG: ${i}"))
    })
  }

  /**
    *     ( ˘▽˘)っ♨
    */
  def readCredentials = {

    val username = Props.JiraUsername.getOrElse({
      print("Username: ")
      Console.in.readLine()
    })

    val password = Props.JiraPassword.getOrElse({
      print("Password: ")
      new String(System.console().readPassword())
    })

    (username, password)
  }

}