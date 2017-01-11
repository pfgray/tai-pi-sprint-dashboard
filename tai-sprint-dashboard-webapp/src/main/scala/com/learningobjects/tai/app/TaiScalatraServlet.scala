package com.learningobjects.tai.app

import com.learningobjects.tai.app.config.Config

import scala.io.Source
import scala.sys.process._


class TaiScalatraServlet extends TaiSprintDashboardWebappStack {

  private def getData(): String = {

    contentType = "application/json" // doesn't appear to do anything...

    val entries = {
      def toList(line: String): List[String] =
        line.split(",").map(_.trim).toList

      Source.fromFile(Config.csvFile)
        .lines()
          .tail
          .map(toList)
          .map(CsvEntry.fromLine)
    }

    val latest = entries.last

    s"""{
       "sprint": ${latest.name},
       "remaining": "${latest.total - latest.completed}",
       "completed": ${latest.completed},
       "days_remaining": ${10 - entries.size},
       "history": [
          ${entries.map(_.toHistory).mkString(",\n")}
       ]
    }"""
  }

  get("/") {
    val data = getData()
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
    s"echo '${params("text")}' " #| "festival --tts" !
    redirect("/speak")
  }

}
