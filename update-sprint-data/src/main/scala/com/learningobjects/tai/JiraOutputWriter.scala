package com.learningobjects.tai

import java.io.{BufferedWriter, File, FileWriter}
import java.util.Date

class JiraOutputWriter(val fileName:String) {

  val file = new File(fileName)
  val exists = file.exists()
  val bw = new BufferedWriter(new FileWriter(file, true))

  if (!exists) {
    val header = s""""Name","Start","End","Now","Completed","Total"\n"""
    bw.write(header)
  }

  def write(sprint: Sprint, sprintStart: Date, sprintEnd: Date, timestamp: Long, completed: Double, total: Double): Unit = {
    val data = s""""${sprint.name}",${sprintStart.getTime},${sprintEnd.getTime},${System.currentTimeMillis()},${completed},${total}\n"""
    bw.write(data)
  }

  def close(): Unit = {
    bw.close
  }
}

object JiraOutputWriter {
  def open(fileName:String): JiraOutputWriter =
    new JiraOutputWriter(fileName)
}