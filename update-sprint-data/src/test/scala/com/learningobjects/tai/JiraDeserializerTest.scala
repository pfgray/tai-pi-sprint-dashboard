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
        |    "name": "Pied Piper Sprint 15",
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
    sprint.name should equal("Pied Piper Sprint 15")
    sprint.startDate should equal("05/Oct/16 1:00 AM")
    sprint.endDate should equal("19/Oct/16 1:00 AM")
    sprint.daysRemaining should equal(8)
  }

  "JiraDeserializer" should "deserialize an issue" in {
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
        |        "value": 0,
        |        "text": "0"
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
  }
}
