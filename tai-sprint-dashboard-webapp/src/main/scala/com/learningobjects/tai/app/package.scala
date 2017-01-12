package com.learningobjects.tai

import java.text.SimpleDateFormat
import java.util.Date

import scala.io.Source

package object app {

  implicit class SourceHelper(src: Source) {

    def contents(): String = src.lines().mkString("\n")

    def lines(): List[String] = try {
      src.getLines.map(_.trim).toList
    } finally {
      src.close
    }
  }

  implicit class LongHelper(l: Long) {
    def toDate: Date = new Date(l)
  }

  implicit class DateHelper(date: Date) {
    def toShortIso8601: String = new SimpleDateFormat("yyyy-MM-dd").format(date)
  }
}
