package com.learningobjects.tai

/**
  *     (⇀_⇀)
  */
object Urls {

  private lazy val host = Props.JiraHost

  def fromPath(path:String)  = s"$host$path"

  val Auth = fromPath("/rest/auth/1/session")
}
