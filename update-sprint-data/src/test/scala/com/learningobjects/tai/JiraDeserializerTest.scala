package com.learningobjects.tai

import org.scalatest.{Matchers, FlatSpec}
import play.api.libs.json.Json

/**
  * Created by bsmith on 10/7/16.
  */
class JiraDeserializerTest extends FlatSpec with Matchers {

  "JiraDeserializer" should "deserialize a sprint" in {
    val json =
      """
        | {
        |    "id": 510,
        |    "sequence": 510,
        |    "name": "Pied Piper 13",
        |    "state": "ACTIVE",
        |    "linkedPagesCount": 0,
        |    "startDate": "05/Oct/16 1:00 AM",
        |    "endDate": "19/Oct/16 1:00 AM",
        |    "completeDate": "None",
        |    "remoteLinks": [],
        |    "daysRemaining": 8
        | }
      """.stripMargin
    val sprint = JiraDeserializer.sprint(Json.parse(json))
    sprint.id should equal(510)
    sprint.name should equal("Pied Piper 13")
    sprint.startDate should equal("05/Oct/16 1:00 AM")
    sprint.endDate should equal("19/Oct/16 1:00 AM")
    sprint.daysRemaining should equal(8)
  }

  it should "deserialize an issue" in {
    val json =
      """
        | {
        |    "id": 61426,
        |    "key": "ABC-6322",
        |    "hidden": false,
        |    "parentId": 60918,
        |    "parentKey": "ABC-6019",
        |    "typeName": "Sub-task",
        |    "typeId": "5",
        |    "summary": "Update LTI documentation to include line item extensions",
        |    "typeUrl": "https://example.org/jira/images/icons/issuetypes/subtask_alternate.png",
        |    "priorityUrl": "https://example.org/jira/images/icons/priorities/major.png",
        |    "priorityName": "Major",
        |    "done": true,
        |    "hasCustomUserAvatar": false,
        |    "color": "#009999",
        |    "estimateStatistic": {
        |      "statFieldId": "customfield_10008",
        |      "statFieldValue": {
        |        "value": 1.5,
        |        "text": "1.5"
        |      }
        |    },
        |    "statusId": "10001",
        |    "statusName": "Done",
        |    "statusUrl": "https://example.org/jira/images/icons/subtask.gif",
        |    "status": {
        |      "id": "10001",
        |      "name": "Done",
        |      "description": "",
        |      "iconUrl": "https://example.org/jira/images/icons/subtask.gif",
        |      "statusCategory": {
        |      "id": "3",
        |      "key": "done",
        |        "colorName": "green"
        |      }
        |    },
        |    "fixVersions": [
        |      13435
        |    ],
        |    "projectId": 11105,
        |    "linkedPagesCount": 0
        | }
      """.stripMargin
    val issue = JiraDeserializer.issue(Json.parse(json))
    issue.id should equal(61426)
    issue.key should equal("ABC-6322")
    issue.typeName should equal("Sub-task")
    issue.summary should startWith("Update LTI documentation")
    issue.done should equal(true)
    issue.statusName should equal("Done")
    issue.parentId should equal(Some(60918))
    issue.parentKey should equal(Some("ABC-6019"))
    issue.points should equal(Some(1.5))
  }

  it should "deserialize an issue without a parent" in {
    val json =
      """
        | {
        |    "id": 61426,
        |    "key": "ABC-6322",
        |    "hidden": false,
        |    "typeName": "Sub-task",
        |    "typeId": "5",
        |    "summary": "Update LTI documentation to include line item extensions",
        |    "typeUrl": "https://example.org/jira/images/icons/issuetypes/subtask_alternate.png",
        |    "priorityUrl": "https://example.org/jira/images/icons/priorities/major.png",
        |    "priorityName": "Major",
        |    "done": true,
        |    "hasCustomUserAvatar": false,
        |    "color": "#009999",
        |    "estimateStatistic": {
        |      "statFieldId": "customfield_10008",
        |      "statFieldValue": {
        |        "value": 1.5,
        |        "text": "1.5"
        |      }
        |    },
        |    "statusId": "10001",
        |    "statusName": "Done",
        |    "statusUrl": "https://example.org/jira/images/icons/subtask.gif",
        |    "status": {
        |      "id": "10001",
        |      "name": "Done",
        |      "description": "",
        |      "iconUrl": "https://example.org/jira/images/icons/subtask.gif",
        |      "statusCategory": {
        |      "id": "3",
        |      "key": "done",
        |        "colorName": "green"
        |      }
        |    },
        |    "fixVersions": [
        |      13435
        |    ],
        |    "projectId": 11105,
        |    "linkedPagesCount": 0
        | }
      """.stripMargin
    val issue = JiraDeserializer.issue(Json.parse(json))
    issue.id should equal(61426)
    issue.key should equal("ABC-6322")
    issue.typeName should equal("Sub-task")
    issue.summary should startWith("Update LTI documentation")
    issue.done should equal(true)
    issue.statusName should equal("Done")
    issue.parentId should equal(None)
    issue.parentKey should equal(None)
    issue.points should equal(Some(1.5))
  }

  it should "deserialize an issue without points" in {
    val json =
      """
        |{
        |    "id":58576,
        |    "key":"ABC-3328",
        |    "hidden":false,
        |    "typeName":"Epic",
        |    "typeId":"10000",
        |    "summary":"Middle-out compression algorithm should handle 3d video",
        |    "typeUrl":"https://example.org/jira/images/icons/ico_epic.png",
        |    "priorityUrl":"https://foo.com/jira/images/icons/priorities/critical.png",
        |    "priorityName":"Critical",
        |    "done":false,
        |    "assignee":"rhendrinks",
        |    "assigneeName":"Richard Hendricks",
        |    "hasCustomUserAvatar":false,
        |    "color":"#888720",
        |    "statusId":"10000",
        |    "statusName":"To Do",
        |    "statusUrl":"https://foo.com/jira/images/icons/subtask.gif",
        |    "status":{
        |        "id":"10000",
        |        "name":"To Do",
        |        "description":"",
        |        "iconUrl":"https://foo.com/jira/images/icons/subtask.gif",
        |        "statusCategory":{
        |            "id":"2",
        |            "key":"new",
        |            "colorName":"blue-gray"
        |        }
        |    },
        |    "fixVersions":[
        |        13436
        |    ],
        |    "projectId":11105,
        |    "linkedPagesCount":0
        |}
      """.stripMargin

    val issue = JiraDeserializer.issue(Json.parse(json))
    issue.id should equal(58576)
    issue.key should equal("ABC-3328")
    issue.typeName should equal("Epic")
    issue.summary should startWith("Middle-out compression algorithm")
    issue.done should equal(false)
    issue.statusName should equal("To Do")
    issue.parentId should equal(None)
    issue.parentKey should equal(None)
    issue.points should equal(None)
  }
}
