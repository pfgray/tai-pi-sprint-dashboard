package com.learningobjects.tai

import java.net.HttpCookie

import play.api.libs.json._
import play.api.libs.functional.syntax._

import scalaj.http.Http

/**
  *     (↼_↼)
  */
object JIRA {

  /**
    * e.g.,
    *   {
    *     "id": 510,
    *     "sequence": 510,
    *     "name": "Pied Piper Sprint 15",
    *     "state": "ACTIVE",
    *     "linkedPagesCount": 0,
    *     "startDate": "05/Oct/16 1:00 AM",
    *     "endDate": "19/Oct/16 1:00 AM",
    *     "completeDate": "None",
    *     "remoteLinks": [],
    *     "daysRemaining": 8
    *   }
    */

  implicit val sprintReads: Reads[Sprint] = (
      (JsPath \ "id").read[Long] and
      (JsPath \ "name").read[String] and
      (JsPath \ "startDate").read[String] and
      (JsPath \ "endDate").read[String] and
      (JsPath \ "daysRemaining").read[Long]
    )(Sprint.apply _)

  /**
    * E.g.,
    *
    *   {
    *     "id": 61426,
    *     "key": "ABC-6322",
    *     "hidden": false,
    *     "parentId": 60918,
    *     "parentKey": "ABC-6019",
    *     "typeName": "Sub-task",
    *     "typeId": "5",
    *     "summary": "(OBE) Update LTI documentation to include line item extensions",
    *     "typeUrl": "https://foo.com/jira/images/icons/issuetypes/subtask_alternate.png",
    *     "priorityUrl": "https://foo.com/jira/images/icons/priorities/major.png",
    *     "priorityName": "Major",
    *     "done": true,
    *     "hasCustomUserAvatar": false,
    *     "color": "#009999",
    *     "estimateStatistic": {
    *       "statFieldId": "customfield_10008",
    *       "statFieldValue": {
    *         "value": 0,
    *         "text": "0"
    *       }
    *     },
    *     "statusId": "10001",
    *     "statusName": "Done",
    *     "statusUrl": "https://foo.com/jira/images/icons/subtask.gif",
    *     "status": {
    *       "id": "10001",
    *       "name": "Done",
    *       "description": "",
    *       "iconUrl": "https://foo.com/jira/images/icons/subtask.gif",
    *       "statusCategory": {
    *       "id": "3",
    *       "key": "done",
    *         "colorName": "green"
    *       }
    *     },
    *     "fixVersions": [
    *       13435
    *     ],
    *     "projectId": 11105,
    *     "linkedPagesCount": 0
    *   }
    */
  implicit val issueReads: Reads[Issue] = (
      (JsPath \ "id").read[Long] and
      (JsPath \ "key").read[String] and
//      (JsPath \ "parentId").readNullable[Long] and
//      (JsPath \ "parentKey").readNullable[String] and
      (JsPath \ "typeName").read[String] and
      (JsPath \ "summary").read[String] and
      (JsPath \ "done").read[Boolean] and
      (JsPath \ "statusName").read[String]
//      (JsPath \ "estimateStatistic" \ "statFieldValue" \ "value" ).readNullable[Double]
  )(Issue.apply _)

  // Magic number
  private val rapidViewId = 130

  /**
    * E.g., val issue = JIRA.issue("ABC-6782")
    *
    * See:
    *   /fields/resolutiondate (null, "2016-10-05T20:48:19.381+0000")
    */
  def issue(issueId:String)(implicit cookies:IndexedSeq[HttpCookie]) = {
    val request = Http(Urls.IssuePartial+issueId).cookies(cookies)
    val response = request.asString
    Json.parse(response.body)
  }

  //
  def activeSprint()(implicit cookies:IndexedSeq[HttpCookie]): Sprint = {

    val request = Http(Urls.AllData).cookies(cookies)
      .param("rapidViewId", rapidViewId.toString)
    val response = request.asString
    val body = Json.parse(response.body)

    val sprints =  (body \ "sprintsData" \ "sprints")
      .as[JsArray]
      .value

    sprints.map(_.validate[Sprint].get)
      .find(_.name.startsWith("Team Admin "))
      .get
  }

  //
  def issues(sprintId: Long)(implicit cookies:IndexedSeq[HttpCookie]): Seq[Issue] = {
    val request = Http(Urls.AllData).cookies(cookies)
      .param("rapidViewId", rapidViewId.toString)
      .param("activeSprints", sprintId.toString)
    val response = request.asString
    val body = Json.parse(response.body)

//    println(s"DEBUG: ${Json.prettyPrint(body)}")

    (body \ "issuesData" \ "issues")
      .as[JsArray]
      .value
      .map(_.validate[Issue].get)
  }

  //
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
