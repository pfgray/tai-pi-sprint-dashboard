package com.learningobjects.tai

/**
  *     (⇀_⇀)
  */
object Urls {

  private lazy val host = Props.JiraHost

  def fromPath(path:String)  = s"$host$path"

  val Auth = fromPath("/rest/auth/1/session")

  val AllData = fromPath("/rest/greenhopper/1.0/xboard/work/allData.json")

  val IssuePartial = fromPath("/rest/api/2/issue/")
}
