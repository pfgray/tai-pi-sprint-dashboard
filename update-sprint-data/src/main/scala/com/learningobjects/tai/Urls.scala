package com.learningobjects.tai

/**
  *     (⇀_⇀)
  */
object Urls {

  private lazy val host = Props.JiraHost.get

  def fromPath(path:String)  = s"$host$path"

  val Auth = fromPath("/rest/auth/1/session")

  val SprintDashboard = fromPath("/rest/greenhopper/1.0/xboard/work/allData.json")

  val Issue = fromPath("/rest/api/2/issue/")
}
