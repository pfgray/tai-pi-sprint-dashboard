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
    val command = s"echo '${params.get("text")}' " #| "festival --tts"
    //println(s"Executing: $command")
    val result: Int = (command !)
    println(s"Got result: $result")
    redirect("/speak")
  }

}
