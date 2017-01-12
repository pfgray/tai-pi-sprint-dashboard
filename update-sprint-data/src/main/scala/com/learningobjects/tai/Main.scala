package com.learningobjects.tai

import java.net.HttpCookie
import java.text.SimpleDateFormat

import org.joda.time.DateTime

import sys.process._

object Main {

  /**
    * Run the update and write results to specified CSV file.
    * @param username The JIRA username
    * @param passwordChars The JIRA password (as char array)
    * @param fileName The output filename. Will create if doesn't exist, else append.
    */
  def runUpdate(username:String, passwordChars:Array[Char], fileName: String): Unit = {

    val password = new String(passwordChars)

    implicit val cookies: IndexedSeq[HttpCookie] = Jira.authenticate(username, password)

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
      val remaining = total - completed
      s"""/home/pi/bin/update-seven-segs $completed $remaining""" !
    })

    writer.close
  }

  /**
    *     (^-^*)/
    */
  def main(args: Array[String]): Unit = {

    if (args.length != 1) {
      Console.err.println("Expecting 1 argument: <path to output>")
      System.exit(1)
    }

    val (username, passwordChars) = readCredentials

    val fileName = args(0)

    runUpdate(username, passwordChars, fileName)
  }

  /**
    *     ( ˘▽˘)っ♨
    */
  def readCredentials: (String, Array[Char]) = {

    val username = Props.JiraUsername.getOrElse({
      print("Username: ")
      Console.in.readLine()
    })

    val password = Props.JiraPassword.getOrElse({
      print("Password: ")
      System.console().readPassword()
    })

    (username, password)
  }

}