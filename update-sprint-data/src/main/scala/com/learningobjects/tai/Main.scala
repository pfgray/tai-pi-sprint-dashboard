package com.learningobjects.tai

import java.net.HttpCookie
import java.text.SimpleDateFormat
import java.util.Locale

import org.joda.time.DateTime

object Main {

  /**
    *     (^-^*)/
    */
  def main(args: Array[String]): Unit = {

    val (username, password) = readCredentials

    implicit val cookies: IndexedSeq[HttpCookie] = Jira.authenticate(username, password)

    Jira.activeSprint.foreach(sprint => {

      // Current sprint start date
      val format = new SimpleDateFormat("dd/MMM/yy hh:mm a")
      val sprintStart = format.parse(sprint.startDate)

      // Filter out anything from before this sprint
      val separateIssues =
        Jira.issueSummaries(sprint.id)
          .map(summary => Jira.issue(summary.key))
          .filterNot(_.resolutionDate
            .map(DateTime.parse)
            .exists(_.toDate.before(sprintStart)))

      // Bind parent tickets to children
      val issues = JiraUtils.bind(separateIssues)

      val total = issues.map(JiraUtils.points).sum
      val completed = issues.map(JiraUtils.completedPoints).sum

      println(s"DEBUG: finished ${completed} out of ${total}")

      issues.foreach(issue => {
        //
        // TODO:
        //   1. Create graph using subtasks, parent fields
        //   2. Generate JSON using done, resolutionDate, points
        //
//        println(s"DEBUG: $issue")
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