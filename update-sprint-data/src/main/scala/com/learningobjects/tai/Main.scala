package com.learningobjects.tai

import java.io.FileInputStream
import java.net.HttpCookie
import java.util.Properties

import scalaj.http._

object Main {

  /**
    *     (^-^*)/
    */
  def main(args: Array[String]): Unit = {

    val (username, password) = readCredentials

    val cookies = Jira.authenticate(username, password)

    val request = Http(Urls.fromPath("/rest/api/2/issue/ABC-6782")).cookies(cookies)

    println(s"DEBUG: ${request.asString}")
  }

  /**
    *     (っ˘ڡ˘ς)
    */
  lazy val properties = {
    val prop = new Properties()
    prop.load(new FileInputStream("run.properties"))
    prop
  }

  /**
    *     ( ˘▽˘)っ♨
    */
  def readCredentials = {
    print("Username: ")
    val username = Console.in.readLine()

    print("Password: ")
    val password = System.console().readPassword()

    (username, new String(password))
  }

  /**
    *     (↼_↼)
    */
  object Jira {

    def authenticate(username:String, password: String): IndexedSeq[HttpCookie] = {

      val payload = s"""
                       | {
                       |   "username": "${username}",
                       |   "password": "${password}"
                       | }
       """.stripMargin

      val request = Http(Urls.Auth).header("content-type", "application/json")
        .postData(payload)

      val response = request.asString

      response.cookies
    }
  }

  /**
    *     (⇀_⇀)
    */
  object Urls {

    private lazy val host = properties.getProperty("jira.host")

    def fromPath(path:String)  = s"$host$path"

    val Auth = fromPath("/rest/auth/1/session")
  }
}