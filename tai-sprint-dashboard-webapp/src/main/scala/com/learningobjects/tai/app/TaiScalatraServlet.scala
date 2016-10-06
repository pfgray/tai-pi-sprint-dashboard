package com.learningobjects.tai.app

class TaiScalatraServlet extends TaiSprintDashboardWebappStack {

  get("/") {
    contentType = "text/html"
    layoutTemplate("index.jade", ("pageTitle", "TAI Burndown"))
  }

}
