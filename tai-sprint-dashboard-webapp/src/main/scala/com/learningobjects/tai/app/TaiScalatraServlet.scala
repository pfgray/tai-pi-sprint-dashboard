package com.learningobjects.tai.app

import scala.io.Source
import sys.process._

class TaiScalatraServlet extends TaiSprintDashboardWebappStack {

  implicit class SourceHelper(src:Source) {
    def contents(): String = try {
      src.getLines.map(_.trim).mkString("")
    } finally {
      src.close
    }
  }

  get("/current-sprint.json") {
    contentType = "text/json"
    Source.fromFile("/tmp/current-sprint.json").contents
  }

  get("/") {
    val data = Source.fromURL("http://localhost:8080/current-sprint.json").contents
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
