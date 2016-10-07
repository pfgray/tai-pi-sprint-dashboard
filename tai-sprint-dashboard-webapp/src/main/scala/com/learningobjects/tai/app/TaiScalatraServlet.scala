package com.learningobjects.tai.app

import sys.process._

class TaiScalatraServlet extends TaiSprintDashboardWebappStack {

  get("/") {
    contentType = "text/html"
    layoutTemplate("index.jade", ("pageTitle", "TAI Burndown"))
  }

  get("/speak") {
    contentType = "text/html"
    layoutTemplate("speak.jade", ("pageTitle", "Speak"))
  }

  post("/speakit") {
    val result = s"echo '${params.get("text")}' | festival --tts" !
    redirect("/speak")
  }

}
