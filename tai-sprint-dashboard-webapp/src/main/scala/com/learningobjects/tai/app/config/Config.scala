package com.learningobjects.tai.app.config

import com.typesafe.config.ConfigFactory

object Config {
  private val config = ConfigFactory.load()
  private val homePath = System.getProperty("user.home")

  val csvFile: String = s"$homePath/${config.getString("app.csvPath")}"
  val keyStoreFile: String = s"$homePath/.keystore"
}
