package com.learningobjects.tai

import java.net.HttpCookie

import scalaj.http.Http

/**
  *     (↼_↼)
  */
object JIRA {

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
