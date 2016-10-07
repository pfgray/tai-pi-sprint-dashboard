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

  lazy val JiraHost = properties.getProperty("jira.host")

}
