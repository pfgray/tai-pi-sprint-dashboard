package com.learningobjects.tai

import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  *     =O
  */
object JiraDeserializer {

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


  /**
    *
    */
  def sprint(json:JsValue): Sprint = json.validate[Sprint].get

  /**
    *
    */
  def issue(json: JsValue): Issue = json.validate[Issue].get

}
