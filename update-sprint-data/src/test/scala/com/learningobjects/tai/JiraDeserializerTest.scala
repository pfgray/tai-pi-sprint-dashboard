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

  it should "deserialize an issue summary" in {
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
    val issueSummary = JiraDeserializer.issueSummary(Json.parse(json))
    issueSummary.id should equal(61426)
    issueSummary.key should equal("ABC-6322")
    issueSummary.typeName should equal("Sub-task")
    issueSummary.summary should startWith("Update LTI documentation")
    issueSummary.done should equal(true)
    issueSummary.statusName should equal("Done")
    issueSummary.parentId should equal(Some(60918))
    issueSummary.parentKey should equal(Some("ABC-6019"))
    issueSummary.points should equal(Some(1.5))
  }

  it should "deserialize an issue summary without a parent" in {
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
    val issueSummary = JiraDeserializer.issueSummary(Json.parse(json))
    issueSummary.id should equal(61426)
    issueSummary.key should equal("ABC-6322")
    issueSummary.typeName should equal("Sub-task")
    issueSummary.summary should startWith("Update LTI documentation")
    issueSummary.done should equal(true)
    issueSummary.statusName should equal("Done")
    issueSummary.parentId should equal(None)
    issueSummary.parentKey should equal(None)
    issueSummary.points should equal(Some(1.5))
  }

  it should "deserialize an issue summary without points" in {
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
        |    "priorityUrl":"https://piedpiper.com/jira/images/icons/priorities/critical.png",
        |    "priorityName":"Critical",
        |    "done":false,
        |    "assignee":"rhendrinks",
        |    "assigneeName":"Richard Hendricks",
        |    "hasCustomUserAvatar":false,
        |    "color":"#888720",
        |    "statusId":"10000",
        |    "statusName":"To Do",
        |    "statusUrl":"https://piedpiper.com/jira/images/icons/subtask.gif",
        |    "status":{
        |        "id":"10000",
        |        "name":"To Do",
        |        "description":"",
        |        "iconUrl":"https://piedpiper.com/jira/images/icons/subtask.gif",
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

    val issueSummary = JiraDeserializer.issueSummary(Json.parse(json))
    issueSummary.id should equal(58576)
    issueSummary.key should equal("ABC-3328")
    issueSummary.typeName should equal("Epic")
    issueSummary.summary should startWith("Middle-out compression algorithm")
    issueSummary.done should equal(false)
    issueSummary.statusName should equal("To Do")
    issueSummary.parentId should equal(None)
    issueSummary.parentKey should equal(None)
    issueSummary.points should equal(None)
  }

  it should "deserialize an issue that is a story" in {
    val json =
      """
        | {
        |    "expand":"renderedFields,names,schema,transitions,operations,editmeta,changelog",
        |    "id":"58990",
        |    "self":"https://piedpiper.com/jira/rest/api/2/issue/58990",
        |    "key":"ABC-3477",
        |    "fields":{
        |        "fixVersions":[
        |
        |        ],
        |        "resolution":null,
        |        "customfield_10500":null,
        |        "customfield_10900":null,
        |        "customfield_10901":null,
        |        "lastViewed":"2016-10-09T14:29:28.841+0000",
        |        "priority":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/priority/3",
        |            "iconUrl":"https://piedpiper.com/jira/images/icons/priorities/major.png",
        |            "name":"Major",
        |            "id":"3"
        |        },
        |        "customfield_10100":null,
        |        "labels":[
        |
        |        ],
        |        "customfield_11700":null,
        |        "customfield_11701":null,
        |        "timeestimate":null,
        |        "aggregatetimeoriginalestimate":null,
        |        "versions":[
        |
        |        ],
        |        "issuelinks":[
        |            {
        |                "id":"30776",
        |                "self":"https://piedpiper.com/jira/rest/api/2/issueLink/30776",
        |                "type":{
        |                    "id":"10000",
        |                    "name":"Blocks",
        |                    "inward":"is blocked by",
        |                    "outward":"blocks",
        |                    "self":"https://piedpiper.com/jira/rest/api/2/issueLinkType/10000"
        |                },
        |                "outwardIssue":{
        |                    "id":"58997",
        |                    "key":"ABC-3484",
        |                    "self":"https://piedpiper.com/jira/rest/api/2/issue/58997",
        |                    "fields":{
        |                        "summary":"Lorem dim sum vegetarian crisp spring rolls dried scallop and leek puff deep fried seaweed roll BBQ pork puff",
        |                        "status":{
        |                            "self":"https://piedpiper.com/jira/rest/api/2/status/10001",
        |                            "description":"",
        |                            "iconUrl":"https://piedpiper.com/jira/images/icons/subtask.gif",
        |                            "name":"Done",
        |                            "id":"10001",
        |                            "statusCategory":{
        |                                "self":"https://piedpiper.com/jira/rest/api/2/statuscategory/3",
        |                                "id":3,
        |                                "key":"done",
        |                                "colorName":"green",
        |                                "name":"Done"
        |                            }
        |                        },
        |                        "priority":{
        |                            "self":"https://piedpiper.com/jira/rest/api/2/priority/2",
        |                            "iconUrl":"https://piedpiper.com/jira/images/icons/priorities/critical.png",
        |                            "name":"Critical",
        |                            "id":"2"
        |                        },
        |                        "issuetype":{
        |                            "self":"https://piedpiper.com/jira/rest/api/2/issuetype/1",
        |                            "id":"1",
        |                            "description":"Rudderfish alooh flagblenny beluga sturgeon tilapia redside",
        |                            "iconUrl":"https://piedpiper.com/jira/images/icons/issuetypes/bug.png",
        |                            "name":"Bug",
        |                            "subtask":false
        |                        }
        |                    }
        |                }
        |            },
        |            {
        |                "id":"30780",
        |                "self":"https://piedpiper.com/jira/rest/api/2/issueLink/30780",
        |                "type":{
        |                    "id":"10000",
        |                    "name":"Blocks",
        |                    "inward":"is blocked by",
        |                    "outward":"blocks",
        |                    "self":"https://piedpiper.com/jira/rest/api/2/issueLinkType/10000"
        |                },
        |                "outwardIssue":{
        |                    "id":"59000",
        |                    "key":"ABC-3487",
        |                    "self":"https://piedpiper.com/jira/rest/api/2/issue/59000",
        |                    "fields":{
        |                        "summary":"Lorem dim sum Mango pudding coconut milk pudding black sesame soft ball",
        |                        "status":{
        |                            "self":"https://piedpiper.com/jira/rest/api/2/status/10103",
        |                            "description":"This status is managed internally by JIRA Agile",
        |                            "iconUrl":"https://piedpiper.com/jira/images/icons/subtask.gif",
        |                            "name":"Ideation",
        |                            "id":"10103",
        |                            "statusCategory":{
        |                                "self":"https://piedpiper.com/jira/rest/api/2/statuscategory/2",
        |                                "id":2,
        |                                "key":"new",
        |                                "colorName":"blue-gray",
        |                                "name":"To Do"
        |                            }
        |                        },
        |                        "priority":{
        |                            "self":"https://piedpiper.com/jira/rest/api/2/priority/3",
        |                            "iconUrl":"https://piedpiper.com/jira/images/icons/priorities/major.png",
        |                            "name":"Major",
        |                            "id":"3"
        |                        },
        |                        "issuetype":{
        |                            "self":"https://piedpiper.com/jira/rest/api/2/issuetype/3",
        |                            "id":"3",
        |                            "description":"foobarbaz",
        |                            "iconUrl":"https://piedpiper.com/jira/images/icons/issuetypes/task.png",
        |                            "name":"Task",
        |                            "subtask":false
        |                        }
        |                    }
        |                }
        |            }
        |        ],
        |        "assignee":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/user?username=dchugtai",
        |            "name":"dchugtai",
        |            "key":"dchugtai",
        |            "emailAddress":"dchugtai@piedpiper.com",
        |            "avatarUrls":{
        |            },
        |            "displayName":"Dinesh Chugtai",
        |            "active":true,
        |            "timeZone":"America/San_Francisco"
        |        },
        |        "status":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/status/3",
        |            "description":"Spiderfish kaluga upside-down catfish yellowtail horse mackerel longfin smelt tope",
        |            "iconUrl":"https://piedpiper.com/jira/images/icons/statuses/inprogress.png",
        |            "name":"In Progress",
        |            "id":"3",
        |            "statusCategory":{
        |                "self":"https://piedpiper.com/jira/rest/api/2/statuscategory/4",
        |                "id":4,
        |                "key":"indeterminate",
        |                "colorName":"yellow",
        |                "name":"In Progress"
        |            }
        |        },
        |        "components":[
        |
        |        ],
        |        "customfield_11300":null,
        |        "customfield_11414":null,
        |        "customfield_10203":null,
        |        "customfield_11413":null,
        |        "customfield_10204":null,
        |        "customfield_10600":"dchugtai",
        |        "customfield_10205":null,
        |        "customfield_11418":"1|hzv03l:",
        |        "aggregatetimeestimate":null,
        |        "customfield_11419":"0|i069mf:",
        |        "creator":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/user?username=rhendricks",
        |            "name":"rhendricks",
        |            "key":"rhendricks",
        |            "emailAddress":"rhendricks@piedpiper.com",
        |            "avatarUrls":{
        |            },
        |            "displayName":"Richard Hendricks",
        |            "active":true,
        |            "timeZone":"America/San_Francisco"
        |        },
        |        "subtasks":[
        |            {
        |                "id":"62318",
        |                "key":"ABC-6802",
        |                "self":"https://piedpiper.com/jira/rest/api/2/issue/62318",
        |                "fields":{
        |                    "summary":"Lorem dim sum Pai gwut Ma lai go Do fu fa Shaomai Congee Shangai steam buns.",
        |                    "status":{
        |                        "self":"https://piedpiper.com/jira/rest/api/2/status/3",
        |                        "description":"Spiderfish kaluga upside-down catfish yellowtail horse mackerel longfin smelt tope",
        |                        "iconUrl":"https://piedpiper.com/jira/images/icons/statuses/inprogress.png",
        |                        "name":"In Progress",
        |                        "id":"3",
        |                        "statusCategory":{
        |                            "self":"https://piedpiper.com/jira/rest/api/2/statuscategory/4",
        |                            "id":4,
        |                            "key":"indeterminate",
        |                            "colorName":"yellow",
        |                            "name":"In Progress"
        |                        }
        |                    },
        |                    "priority":{
        |                        "self":"https://piedpiper.com/jira/rest/api/2/priority/4",
        |                        "iconUrl":"https://piedpiper.com/jira/images/icons/priorities/minor.png",
        |                        "name":"Minor",
        |                        "id":"4"
        |                    },
        |                    "issuetype":{
        |                        "self":"https://piedpiper.com/jira/rest/api/2/issuetype/5",
        |                        "id":"5",
        |                        "description":"Lorem dim sum Congee Shangai steam buns chicken feet mini egg tarts steamed sponge cake",
        |                        "iconUrl":"https://piedpiper.com/jira/images/icons/issuetypes/subtask_alternate.png",
        |                        "name":"Sub-task",
        |                        "subtask":true
        |                    }
        |                }
        |            }
        |        ],
        |        "reporter":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/user?username=rhendricks",
        |            "name":"rhendricks",
        |            "key":"rhendricks",
        |            "emailAddress":"rhendricks@piedpiper.com",
        |            "avatarUrls":{
        |            },
        |            "displayName":"Richard Hendricks",
        |            "active":true,
        |            "timeZone":"America/San_Francisco"
        |        },
        |        "customfield_12101":null,
        |        "aggregateprogress":{
        |            "progress":0,
        |            "total":0
        |        },
        |        "customfield_12103":null,
        |        "customfield_12102":null,
        |        "customfield_11412":null,
        |        "customfield_12104":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/customFieldOption/11303",
        |            "value":"No",
        |            "id":"11303"
        |        },
        |        "customfield_11403":null,
        |        "customfield_11402":null,
        |        "customfield_11405":{
        |            "name":"jira-pied-piper",
        |            "self":"https://piedpiper.com/jira/rest/api/2/group?groupname=jira-pied-piper"
        |        },
        |        "customfield_11404":"9223372036854775807",
        |        "customfield_11408":null,
        |        "customfield_11804":[
        |
        |        ],
        |        "progress":{
        |            "progress":0,
        |            "total":0
        |        },
        |        "votes":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/issue/ABC-3477/votes",
        |            "votes":0,
        |            "hasVoted":false
        |        },
        |        "worklog":{
        |            "startAt":0,
        |            "maxResults":20,
        |            "total":0,
        |            "worklogs":[
        |
        |            ]
        |        },
        |        "issuetype":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/issuetype/1",
        |            "id":"1",
        |            "description":"Rudderfish alooh flagblenny beluga sturgeon tilapia redside",
        |            "iconUrl":"https://piedpiper.com/jira/images/icons/issuetypes/bug.png",
        |            "name":"Bug",
        |            "subtask":false
        |        },
        |        "timespent":null,
        |        "project":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/project/11105",
        |            "id":"11105",
        |            "key":"ABC",
        |            "name":"Pied Piper Product",
        |            "avatarUrls":{
        |                "48x48":"https://piedpiper.com/jira/secure/projectavatar?avatarId=10011",
        |                "24x24":"https://piedpiper.com/jira/secure/projectavatar?size=small&avatarId=10011",
        |                "16x16":"https://piedpiper.com/jira/secure/projectavatar?size=xsmall&avatarId=10011",
        |                "32x32":"https://piedpiper.com/jira/secure/projectavatar?size=medium&avatarId=10011"
        |            }
        |        },
        |        "customfield_12210":"-",
        |        "aggregatetimespent":null,
        |        "customfield_11401":null,
        |        "customfield_11400":null,
        |        "customfield_12206":null,
        |        "customfield_12205":"-",
        |        "customfield_12208":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/customFieldOption/11441",
        |            "value":"Unknown",
        |            "id":"11441"
        |        },
        |        "customfield_12207":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/customFieldOption/11433",
        |            "value":"Not Known",
        |            "id":"11433"
        |        },
        |        "customfield_12209":[
        |            {
        |                "self":"https://piedpiper.com/jira/rest/api/2/customFieldOption/11504",
        |                "value":"Unknown",
        |                "id":"11504"
        |            }
        |        ],
        |        "resolutiondate":null,
        |        "workratio":-1,
        |        "watches":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/issue/ABC-3477/watchers",
        |            "watchCount":1,
        |            "isWatching":true
        |        },
        |        "created":"2016-06-13T15:20:32.969+0000",
        |        "customfield_12200":null,
        |        "customfield_12202":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/customFieldOption/11506",
        |            "value":"Unknown",
        |            "id":"11506"
        |        },
        |        "customfield_12201":null,
        |        "customfield_10300":null,
        |        "customfield_12204":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/customFieldOption/11520",
        |            "value":"Unknown",
        |            "id":"11520"
        |        },
        |        "customfield_12203":null,
        |        "customfield_11900":null,
        |        "customfield_11902":null,
        |        "customfield_11901":null,
        |        "updated":"2016-10-05T14:50:10.763+0000",
        |        "timeoriginalestimate":null,
        |        "description":"Lorem dim sum Golden pumpkin fries soy sauce duck wings octopus seaweed sauteed string beans",
        |        "customfield_10010":null,
        |        "customfield_11100":null,
        |        "customfield_11500":null,
        |        "timetracking":{
        |
        |        },
        |        "customfield_12305":null,
        |        "customfield_10006":null,
        |        "customfield_12304":null,
        |        "security":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/securitylevel/10001",
        |            "id":"10001",
        |            "description":"",
        |            "name":"Pied Piper Internal"
        |        },
        |        "customfield_10007":null,
        |        "customfield_12307":null,
        |        "customfield_10008":9.5,
        |        "customfield_10800":null,
        |        "customfield_12306":null,
        |        "attachment":[
        |            {
        |                "self":"https://piedpiper.com/jira/rest/api/2/attachment/34780",
        |                "id":"34780",
        |                "filename":"2016-06-09.C-3328-diagram.jpg",
        |                "author":{
        |                    "self":"https://piedpiper.com/jira/rest/api/2/user?username=rhendricks",
        |                    "name":"rhendricks",
        |                    "key":"rhendricks",
        |                    "emailAddress":"rhendricks@piedpiper.com",
        |                    "avatarUrls":{
        |                        "48x48":"https://piedpiper.com/jira/secure/useravatar?ownerId=rhendricks&avatarId=10705",
        |                        "24x24":"https://piedpiper.com/jira/secure/useravatar?size=small&ownerId=rhendricks&avatarId=10705",
        |                        "16x16":"https://piedpiper.com/jira/secure/useravatar?size=xsmall&ownerId=rhendricks&avatarId=10705",
        |                        "32x32":"https://piedpiper.com/jira/secure/useravatar?size=medium&ownerId=rhendricks&avatarId=10705"
        |                    },
        |                    "displayName":"Richard Hendricks",
        |                    "active":true,
        |                    "timeZone":"America/San_Francisco"
        |                },
        |                "created":"2016-06-13T15:21:37.057+0000",
        |                "size":1683468,
        |                "mimeType":"image/jpeg",
        |                "content":"https://piedpiper.com/jira/secure/attachment/34780/2016-06-09.C-3328-diagram.jpg",
        |                "thumbnail":"https://piedpiper.com/jira/secure/thumbnail/34780/_thumb_34780.png"
        |            }
        |        ],
        |        "customfield_10801":null,
        |        "customfield_10802":null,
        |        "summary":"Taro cake Deep fried pumpkin and egg-yolk ball vegetarian",
        |        "customfield_10000":[
        |            "com.atlassian.greenhopper.service.sprint.Sprint@342ca01b[id=510,rapidViewId=130,state=ACTIVE,name=Pied Piper 15,startDate=2016-10-05T05:00:00.000Z,endDate=2016-10-19T05:00:00.000Z,completeDate=<null>,sequence=510]"
        |        ],
        |        "customfield_10001":"ABC-3328",
        |        "customfield_10002":"9223372036854775807",
        |        "customfield_12300":null,
        |        "customfield_12303":"-",
        |        "customfield_12302":null,
        |        "environment":null,
        |        "customfield_10912":null,
        |        "duedate":null,
        |        "customfield_10913":null,
        |        "customfield_10914":null,
        |        "customfield_10915":null,
        |        "customfield_10916":null,
        |        "comment":{
        |            "startAt":0,
        |            "maxResults":1,
        |            "total":1,
        |            "comments":[
        |                {
        |                    "self":"https://piedpiper.com/jira/rest/api/2/issue/58990/comment/99378",
        |                    "id":"99378",
        |                    "author":{
        |                        "self":"https://piedpiper.com/jira/rest/api/2/user?username=rhendricks",
        |                        "name":"rhendricks",
        |                        "key":"rhendricks",
        |                        "emailAddress":"rhendricks@piedpiper.com",
        |                        "avatarUrls":{
        |                            "48x48":"https://piedpiper.com/jira/secure/useravatar?ownerId=rhendricks&avatarId=10705",
        |                            "24x24":"https://piedpiper.com/jira/secure/useravatar?size=small&ownerId=rhendricks&avatarId=10705",
        |                            "16x16":"https://piedpiper.com/jira/secure/useravatar?size=xsmall&ownerId=rhendricks&avatarId=10705",
        |                            "32x32":"https://piedpiper.com/jira/secure/useravatar?size=medium&ownerId=rhendricks&avatarId=10705"
        |                        },
        |                        "displayName":"Richard Hendricks",
        |                        "active":true,
        |                        "timeZone":"America/San_Francisco"
        |                    },
        |                    "body":"Spiced salt baked octopus Fung zao Ngao yuk kau Pai gwut Ma lai go.",
        |                    "updateAuthor":{
        |                        "self":"https://piedpiper.com/jira/rest/api/2/user?username=rhendricks",
        |                        "name":"rhendricks",
        |                        "key":"rhendricks",
        |                        "emailAddress":"rhendricks@piedpiper.com",
        |                        "avatarUrls":{
        |                            "48x48":"https://piedpiper.com/jira/secure/useravatar?ownerId=rhendricks&avatarId=10705",
        |                            "24x24":"https://piedpiper.com/jira/secure/useravatar?size=small&ownerId=rhendricks&avatarId=10705",
        |                            "16x16":"https://piedpiper.com/jira/secure/useravatar?size=xsmall&ownerId=rhendricks&avatarId=10705",
        |                            "32x32":"https://piedpiper.com/jira/secure/useravatar?size=medium&ownerId=rhendricks&avatarId=10705"
        |                        },
        |                        "displayName":"Richard Hendricks",
        |                        "active":true,
        |                        "timeZone":"America/San_Francisco"
        |                    },
        |                    "created":"2016-06-13T20:10:36.049+0000",
        |                    "updated":"2016-06-13T20:11:37.244+0000",
        |                    "visibility":{
        |                        "type":"role",
        |                        "value":"Pied Piper Internal"
        |                    }
        |                }
        |            ]
        |        }
        |    }
        |}
      """.stripMargin
    val issue = JiraDeserializer.issue(Json.parse(json))
    issue.id should equal(58990)
    issue.key should equal("ABC-3477")
    issue.typeName should equal("Bug")
    issue.summary should equal("Taro cake Deep fried pumpkin and egg-yolk ball vegetarian")
    issue.done should equal(false)
    issue.resolutionDate should equal(None)
    issue.parentId should equal(None)
    issue.parentKey should equal(None)
    issue.points should equal(Some(9.5))
    issue.subtaskKeys should equal(Seq("ABC-6802"))
  }

  it should "deserialize an issue that is a sub-task" in {
    val json =
      """
        | {
        |    "expand":"renderedFields,names,schema,transitions,operations,editmeta,changelog",
        |    "id":"62318",
        |    "self":"https://piedpiper.com/jira/rest/api/2/issue/62318",
        |    "key":"ABC-6802",
        |    "fields":{
        |        "parent":{
        |            "id":"58990",
        |            "key":"ABC-3477",
        |            "self":"https://piedpiper.com/jira/rest/api/2/issue/58990",
        |            "fields":{
        |                "summary":"Chubsucker slimehead waryfish combtooth blenny collared dogfish rough sculpin sea bream triggerfish crucian carp ilisha dogfish shark. Jawfish springfish cherry salmon bala shark yellowfin tuna barramundi, catalufa.",
        |                "status":{
        |                    "self":"https://piedpiper.com/jira/rest/api/2/status/3",
        |                    "description":"Spiderfish kaluga upside-down catfish yellowtail horse mackerel longfin smelt tope",
        |                    "iconUrl":"https://piedpiper.com/jira/images/icons/statuses/inprogress.png",
        |                    "name":"In Progress",
        |                    "id":"3",
        |                    "statusCategory":{
        |                        "self":"https://piedpiper.com/jira/rest/api/2/statuscategory/4",
        |                        "id":4,
        |                        "key":"indeterminate",
        |                        "colorName":"yellow",
        |                        "name":"In Progress"
        |                    }
        |                },
        |                "priority":{
        |                    "self":"https://piedpiper.com/jira/rest/api/2/priority/3",
        |                    "iconUrl":"https://piedpiper.com/jira/images/icons/priorities/major.png",
        |                    "name":"Major",
        |                    "id":"3"
        |                },
        |                "issuetype":{
        |                    "self":"https://piedpiper.com/jira/rest/api/2/issuetype/1",
        |                    "id":"1",
        |                    "description":"Rudderfish alooh flagblenny beluga sturgeon tilapia redside",
        |                    "iconUrl":"https://piedpiper.com/jira/images/icons/issuetypes/bug.png",
        |                    "name":"Bug",
        |                    "subtask":false
        |                }
        |            }
        |        },
        |        "fixVersions":[
        |            {
        |                "self":"https://piedpiper.com/jira/rest/api/2/version/13436",
        |                "id":"13436",
        |                "name":"Pied 0.15.0",
        |                "archived":false,
        |                "released":false,
        |                "releaseDate":"2016-11-22"
        |            }
        |        ],
        |        "resolution":null,
        |        "customfield_10500":null,
        |        "lastViewed":"2016-10-09T14:30:00.772+0000",
        |        "priority":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/priority/4",
        |            "iconUrl":"https://piedpiper.com/jira/images/icons/priorities/minor.png",
        |            "name":"Minor",
        |            "id":"4"
        |        },
        |        "customfield_10100":null,
        |        "labels":[
        |
        |        ],
        |        "customfield_11700":null,
        |        "customfield_11701":null,
        |        "timeestimate":null,
        |        "aggregatetimeoriginalestimate":null,
        |        "versions":[
        |
        |        ],
        |        "issuelinks":[
        |
        |        ],
        |        "assignee":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/user?username=dchugtai",
        |            "name":"dchugtai",
        |            "key":"dchugtai",
        |            "emailAddress":"dchugtai@piedpiper.com",
        |            "avatarUrls":{
        |            },
        |            "displayName":"Dinesh Chugtai",
        |            "active":true,
        |            "timeZone":"America/San_Francisco"
        |        },
        |        "status":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/status/3",
        |            "description":"Spiderfish kaluga upside-down catfish yellowtail horse mackerel longfin smelt tope",
        |            "iconUrl":"https://piedpiper.com/jira/images/icons/statuses/inprogress.png",
        |            "name":"In Progress",
        |            "id":"3",
        |            "statusCategory":{
        |                "self":"https://piedpiper.com/jira/rest/api/2/statuscategory/4",
        |                "id":4,
        |                "key":"indeterminate",
        |                "colorName":"yellow",
        |                "name":"In Progress"
        |            }
        |        },
        |        "components":[
        |            {
        |                "self":"https://piedpiper.com/jira/rest/api/2/component/11506",
        |                "id":"11506",
        |                "name":"Integrations"
        |            }
        |        ],
        |        "customfield_11300":null,
        |        "customfield_11414":null,
        |        "customfield_10203":null,
        |        "customfield_11413":null,
        |        "customfield_10204":null,
        |        "customfield_10600":"dchugtai",
        |        "customfield_10205":null,
        |        "customfield_11418":"1|i011cf:",
        |        "aggregatetimeestimate":null,
        |        "customfield_11419":"0|i06tqn:",
        |        "creator":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/user?username=rhendricks",
        |            "name":"rhendricks",
        |            "key":"rhendricks",
        |            "emailAddress":"rhendricks@piedpiper.com",
        |            "avatarUrls":{
        |            },
        |            "displayName":"Richard Hendricks",
        |            "active":true,
        |            "timeZone":"America/San_Francisco"
        |        },
        |        "subtasks":[
        |
        |        ],
        |        "reporter":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/user?username=rhendricks",
        |            "name":"rhendricks",
        |            "key":"rhendricks",
        |            "emailAddress":"rhendricks@piedpiper.com",
        |            "avatarUrls":{
        |                "48x48":"https://piedpiper.com/jira/secure/useravatar?ownerId=rhendricks&avatarId=10705",
        |                "24x24":"https://piedpiper.com/jira/secure/useravatar?size=small&ownerId=rhendricks&avatarId=10705",
        |                "16x16":"https://piedpiper.com/jira/secure/useravatar?size=xsmall&ownerId=rhendricks&avatarId=10705",
        |                "32x32":"https://piedpiper.com/jira/secure/useravatar?size=medium&ownerId=rhendricks&avatarId=10705"
        |            },
        |            "displayName":"Richard Hendricks",
        |            "active":true,
        |            "timeZone":"America/San_Francisco"
        |        },
        |        "customfield_12101":null,
        |        "aggregateprogress":{
        |            "progress":0,
        |            "total":0
        |        },
        |        "customfield_12103":null,
        |        "customfield_12102":null,
        |        "customfield_11412":null,
        |        "customfield_11403":null,
        |        "customfield_11402":null,
        |        "customfield_11405":{
        |            "name":"jira-pied-piper",
        |            "self":"https://piedpiper.com/jira/rest/api/2/group?groupname=jira-pied-piper"
        |        },
        |        "customfield_11404":"9223372036854775807",
        |        "customfield_11408":null,
        |        "customfield_11804":[
        |
        |        ],
        |        "progress":{
        |            "progress":0,
        |            "total":0
        |        },
        |        "votes":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/issue/ABC-6802/votes",
        |            "votes":0,
        |            "hasVoted":false
        |        },
        |        "worklog":{
        |            "startAt":0,
        |            "maxResults":20,
        |            "total":0,
        |            "worklogs":[
        |
        |            ]
        |        },
        |        "issuetype":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/issuetype/5",
        |            "id":"5",
        |            "description":"Lorem dim sum Congee Shangai steam buns chicken feet mini egg tarts steamed sponge cake",
        |            "iconUrl":"https://piedpiper.com/jira/images/icons/issuetypes/subtask_alternate.png",
        |            "name":"Sub-task",
        |            "subtask":true
        |        },
        |        "timespent":null,
        |        "project":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/project/11105",
        |            "id":"11105",
        |            "key":"ABC",
        |            "name":"Pied Piper Product",
        |            "avatarUrls":{
        |                "48x48":"https://piedpiper.com/jira/secure/projectavatar?avatarId=10011",
        |                "24x24":"https://piedpiper.com/jira/secure/projectavatar?size=small&avatarId=10011",
        |                "16x16":"https://piedpiper.com/jira/secure/projectavatar?size=xsmall&avatarId=10011",
        |                "32x32":"https://piedpiper.com/jira/secure/projectavatar?size=medium&avatarId=10011"
        |            }
        |        },
        |        "customfield_12210":"-",
        |        "aggregatetimespent":null,
        |        "customfield_11401":null,
        |        "customfield_11400":null,
        |        "customfield_12206":null,
        |        "customfield_12205":"-",
        |        "customfield_12208":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/customFieldOption/11441",
        |            "value":"Unknown",
        |            "id":"11441"
        |        },
        |        "customfield_12207":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/customFieldOption/11433",
        |            "value":"Not Known",
        |            "id":"11433"
        |        },
        |        "customfield_12209":[
        |            {
        |                "self":"https://piedpiper.com/jira/rest/api/2/customFieldOption/11504",
        |                "value":"Unknown",
        |                "id":"11504"
        |            }
        |        ],
        |        "resolutiondate":null,
        |        "workratio":-1,
        |        "watches":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/issue/ABC-6802/watchers",
        |            "watchCount":1,
        |            "isWatching":true
        |        },
        |        "created":"2016-10-03T18:27:47.118+0000",
        |        "customfield_12200":null,
        |        "customfield_12202":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/customFieldOption/11506",
        |            "value":"Unknown",
        |            "id":"11506"
        |        },
        |        "customfield_12201":null,
        |        "customfield_10300":null,
        |        "customfield_12204":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/customFieldOption/11520",
        |            "value":"Unknown",
        |            "id":"11520"
        |        },
        |        "customfield_12203":null,
        |        "customfield_11900":null,
        |        "customfield_11902":null,
        |        "customfield_11901":null,
        |        "updated":"2016-10-05T14:50:11.690+0000",
        |        "timeoriginalestimate":null,
        |        "description":"Popular shumai cha siu bao A creamy mango pudding Chiu-chao fan guo Siu mai.",
        |        "customfield_10010":null,
        |        "customfield_11100":null,
        |        "customfield_11500":null,
        |        "timetracking":{
        |
        |        },
        |        "customfield_12305":null,
        |        "customfield_10006":null,
        |        "customfield_12304":null,
        |        "security":{
        |            "self":"https://piedpiper.com/jira/rest/api/2/securitylevel/10001",
        |            "id":"10001",
        |            "description":"",
        |            "name":"Pied Piper Internal"
        |        },
        |        "customfield_10007":null,
        |        "customfield_12307":null,
        |        "customfield_10008":5.0,
        |        "customfield_10800":null,
        |        "customfield_12306":null,
        |        "attachment":[
        |
        |        ],
        |        "customfield_10801":null,
        |        "customfield_10802":null,
        |        "summary":"Black sesame soft ball deep fried bean curd skin rolls rice noodle roll deep fried crab claw soup dumpling cold chicken",
        |        "customfield_10000":[
        |            "com.atlassian.greenhopper.service.sprint.Sprint@342ca01b[id=510,rapidViewId=130,state=ACTIVE,name=Pied Piper 15,startDate=2016-10-05T05:00:00.000Z,endDate=2016-10-19T05:00:00.000Z,completeDate=<null>,sequence=510]"
        |        ],
        |        "customfield_10001":null,
        |        "customfield_10002":"9223372036854775807",
        |        "customfield_12300":null,
        |        "customfield_12303":"-",
        |        "customfield_12302":null,
        |        "environment":null,
        |        "customfield_10912":null,
        |        "duedate":null,
        |        "customfield_10913":null,
        |        "customfield_10914":null,
        |        "customfield_10915":null,
        |        "customfield_10916":null,
        |        "comment":{
        |            "startAt":0,
        |            "maxResults":0,
        |            "total":0,
        |            "comments":[
        |
        |            ]
        |        }
        |    }
        |}
      """.stripMargin

    val issue = JiraDeserializer.issue(Json.parse(json))
    issue.id should equal(62318)
    issue.key should equal("ABC-6802")
    issue.typeName should equal("Sub-task")
    issue.summary should equal("Black sesame soft ball deep fried bean curd skin rolls rice noodle roll deep fried crab claw soup dumpling cold chicken")
    issue.statusName should equal("In Progress")
    issue.done should equal(false)
    issue.resolutionDate should equal(None)
    issue.parentId should equal(Some(58990))
    issue.parentKey should equal(Some("ABC-3477"))
    issue.points should equal(Some(5.0))
    issue.subtaskKeys should equal(Seq())
  }
}
