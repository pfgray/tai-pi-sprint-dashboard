package com.learningobjects.tai.app

import sys.process._

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

  get("/speak") {
    contentType = "text/html"
    layoutTemplate("speak.jade", ("pageTitle", "Speak"))
  }

  post("/speakit") {
    val command = s"echo '${params("text")}' " #| "festival --tts"
    //println(s"Executing: $command")
    val result: Int = (command !)
    println(s"Got result: $result")
    redirect("/speak")
  }

}
