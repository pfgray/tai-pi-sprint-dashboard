package com.learningobjects.tai

import java.net.HttpCookie

import play.api.libs.json._

import scalaj.http.Http

/**
  *     (↼_↼)
  */
object Jira {

  // Magic number
  private val rapidViewId = 130

  /**
    * E.g., val issue = JIRA.issue("ABC-6782")
    *
    * See:
    *   /fields/resolutiondate (null, "2016-10-05T20:48:19.381+0000")
    */
  def issue(issueId:String)(implicit cookies:IndexedSeq[HttpCookie]) = {
    val request = Http(Urls.Issue+issueId).cookies(cookies)
    val response = request.asString

    // TODO: Add model
    Json.parse(response.body)
  }

  /**
    *
    */
  def activeSprint()(implicit cookies:IndexedSeq[HttpCookie]): Option[Sprint] = {

    val request = Http(Urls.SprintDashboard).cookies(cookies)
      .param("rapidViewId", rapidViewId.toString)
    val response = request.asString
    val body = Json.parse(response.body)

    (body \ "sprintsData" \ "sprints")
      .as[JsArray]
      .value
      .map(JiraDeserializer.sprint)
      .find(_.name.startsWith(Props.SprintPrefix.get))
  }

  /**
    *
    */
  def issuesFromSprint(sprintId: Long)(implicit cookies:IndexedSeq[HttpCookie]): Seq[IssueFromSprint] = {

    val request = Http(Urls.SprintDashboard).cookies(cookies)
      .param("rapidViewId", rapidViewId.toString)
      .param("activeSprints", sprintId.toString)
    val response = request.asString

    val body = Json.parse(response.body)

    (body \ "issuesData" \ "issues")
      .as[JsArray]
      .value
      .map(JiraDeserializer.issueFromSprint)
  }

  /**
    *
    */
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
