package com.learningobjects.tai

import play.api.libs.json.Json

import scalaj.http._

object Main {

  /**
    *     (^-^*)/
    */
  def main(args: Array[String]): Unit = {

    val (username, password) = readCredentials

    val cookies = JIRA.authenticate(username, password)

    val request = Http(Urls.fromPath("/rest/api/2/issue/ABC-6782")).cookies(cookies)
    val response = request.asString
    val json = Json.parse(response.body)

    println(s"DEBUG: ${Json.prettyPrint(json)}")
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

}