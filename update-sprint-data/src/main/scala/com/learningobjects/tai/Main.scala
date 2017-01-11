package com.learningobjects.tai

import java.net.HttpCookie
import java.text.SimpleDateFormat

import org.joda.time.DateTime

//import sys.process._

object Main {

  /**
    *     (^-^*)/
    */
  def main(args: Array[String]): Unit = {

    if (args.length != 1) {
      Console.err.println("Expecting 1 argument: <path to output>")
      System.exit(1)
    }

    val (username, password) = readCredentials

    implicit val cookies: IndexedSeq[HttpCookie] = Jira.authenticate(username, password)

    val fileName = args(0)
    val writer = JiraOutputWriter.open(fileName)

    Jira.activeSprint.foreach(sprint => {

      // Current sprint start date
      val format = new SimpleDateFormat("dd/MMM/yy hh:mm a")
      val sprintStart = format.parse(sprint.startDate)
      val sprintEnd = format.parse(sprint.endDate)

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

      writer.write(sprint, sprintStart, sprintEnd, System.currentTimeMillis(), completed, total)

      //
      // TODO: called separately
      //
//      val remaining = total - completed
//      s"""/home/pi/bin/update-seven-segs $completed $remaining""" !
    })

    writer.close
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