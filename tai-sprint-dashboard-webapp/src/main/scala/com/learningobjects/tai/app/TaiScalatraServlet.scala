package com.learningobjects.tai.app

import java.text.SimpleDateFormat
import java.util.Date
import scala.io.Source
import sys.process._
import org.scalatra._


class TaiScalatraServlet extends TaiSprintDashboardWebappStack {

  //
  // TODO: Move to properties file
  //
  object Config {
    private val home = System.getProperty("user.home")
    val csvPath = s"${home}/data/update-sprint-data.csv"
    val jsonUrl = "http://localhost:8080/current-sprint.json"
  }

  implicit class SourceHelper(src:Source) {

    def contents(): String = src.lines.mkString("\n")

    def lines(): List[String] = try {
      src.getLines.map(_.trim).toList
    } finally {
      src.close
    }
  }

  implicit class LongHelper(l:Long) {
    def toDate(): Date =
      new Date(l)

  }

  implicit class DateHelper(date:Date) {
    def toShortIso8601(): String =
      new SimpleDateFormat("yyyy-MM-dd").format(date);
  }

  def debug(msg:String) = println(s"""DEBUG: $msg""")

  case class CsvEntry (
    name: String,
    start: Date,
    end: Date,
    now: Date,
    completed: Double,
    total: Double
  ) {
    def toHistory(): String = s"""
      {
        "date": "${this.now.toShortIso8601}",
        "remaining": ${this.total - this.completed},
        "completed": ${this.completed}
      }
    """.trim
  }

  object CsvEntry {
    def fromLine(line:List[String]) = CsvEntry(
      line(0),
      line(1).toLong.toDate,
      line(2).toLong.toDate,
      line(3).toLong.toDate,
      line(4).toDouble,
      line(5).toDouble
    )
  }

  def dataFromCsv(): List[CsvEntry] = {

    def toList(line:String): List[String] =
      line.split(",").map(_.trim).toList

    Source.fromFile(Config.csvPath)
          .lines
          .tail
          .map(toList)
          .map(CsvEntry.fromLine)
  }

  get("/current-sprint.json") {

    contentType = "application/json" // doesn't appear to do anything...

    val entries = dataFromCsv
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

    // Sample data
    //Source.fromFile("/tmp/current-sprint.json").contents
  }

  get("/") {
    val data = Source.fromURL(Config.jsonUrl).contents
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
