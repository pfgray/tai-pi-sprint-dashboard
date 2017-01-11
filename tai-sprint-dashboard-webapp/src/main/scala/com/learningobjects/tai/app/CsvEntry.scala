package com.learningobjects.tai.app

import java.util.Date

case class CsvEntry(name: String, start: Date, end: Date, now: Date,
                    completed: Double, total: Double) {
  def toHistory: String =
    s"""
      {
        "date": "${this.now.toShortIso8601}",
        "remaining": ${this.total - this.completed},
        "completed": ${this.completed}
      }
    """.trim
}

object CsvEntry {
  def fromLine(line: List[String]) = CsvEntry(
    line.head,
    line(1).toLong.toDate,
    line(2).toLong.toDate,
    line(3).toLong.toDate,
    line(4).toDouble,
    line(5).toDouble
  )
}

