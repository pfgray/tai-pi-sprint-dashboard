package com.learningobjects.tai

import java.io.FileInputStream
import java.util.Properties

/**
  *     (っ˘ڡ˘ς)
  */
object Props {

  private lazy val properties = {
    val prop = new Properties()
    prop.load(new FileInputStream("run.properties"))
    prop
  }

  private def prop(key:String): Option[String] = Option(properties.getProperty(key))

  lazy val JiraHost = prop("jira.host")
  lazy val SprintPrefix = prop("sprint.prefix")

  //
  // For development-purposes only.
  //
  lazy val JiraUsername = prop("jira.username")
  lazy val JiraPassword = prop("jira.password")

}
