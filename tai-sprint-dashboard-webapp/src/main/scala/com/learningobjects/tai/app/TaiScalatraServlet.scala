package com.learningobjects.tai.app

class TaiScalatraServlet extends TaiSprintDashboardWebappStack {

  get("/") {
    val src = scala.io.Source.fromFile("/tmp/current-sprint.json")
    val data = try src.getLines.map(_.trim).mkString("") finally src.close
    contentType = "text/html"
    layoutTemplate("index.jade",
      ("pageTitle", "TAI Burndown"),
      ("data", data)
    )
  }

}
