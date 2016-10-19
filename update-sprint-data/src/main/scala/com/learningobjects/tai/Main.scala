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
      val separateIssues =
        Jira.issueSummaries(sprint.id)
          .map(summary => Jira.issue(summary.key))

      val issues = JiraUtils.bind(separateIssues)

      println(s"DEBUG: ${separateIssues.size} -> ${issues.size}")

      separateIssues
          .foreach(issue => {
            //
            // TODO:
            //   1. Create graph using subtasks, parent fields
            //   2. Generate JSON using done, resolutionDate, points
            //
            println(s"DEBUG: $issue")
          })
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