package com.learningobjects.tai

import java.io.FileInputStream
import java.util.Properties

import scala.util.Try

/**
  *     (っ˘ڡ˘ς)
  */
object Props {

  private lazy val properties = {
    val file = "run.properties"
    val prop = new Properties()
    val fs = Try(new FileInputStream(file))
    if (fs.isFailure) {
      System.err.println(s"Could not find file: $file")
      System.exit(1)
    }
    prop.load(fs.get)
    prop
  }

  private def prop(key:String): Option[String] = Option(properties.getProperty(key))

  lazy val JiraHost = prop("jira.host")
  lazy val JiraRapidViewId = prop("jira.rapidViewId")
  lazy val JiraIssuePointsFieldName = prop("jira.issuePointsFieldName")
  lazy val SprintPrefix = prop("sprint.prefix")

  //
  // For development-purposes only.
  //
  lazy val JiraUsername = prop("jira.username")
  lazy val JiraPassword = prop("jira.password")

}
