package com.learningobjects.tai

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.Json

import scala.io.Source

/**
  * Created by bsmith on 10/7/16.
  */
class JiraDeserializerTest extends FlatSpec with Matchers {

  def sampleJson(fileName:String) =
    Source.fromURL(getClass.getResource(s"/jira-json-samples/$fileName")).mkString

  "JiraDeserializer" should "deserialize a sprint" in {
    val json = sampleJson("/sprint-1.json")
    val sprint = JiraDeserializer.sprint(Json.parse(json))
    sprint.id should equal(510)
    sprint.name should equal("Pied Piper 13")
    sprint.startDate should equal("05/Oct/16 1:00 AM")
    sprint.endDate should equal("19/Oct/16 1:00 AM")
    sprint.daysRemaining should equal(8)
  }

  it should "deserialize an issue summary" in {
    val json = sampleJson("issue-summary-1.json")
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
    val json = sampleJson("issue-summary-2.json")
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
    val json = sampleJson("issue-summary-3.json")
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
    val json = sampleJson("issue-1.json")
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
    val json = sampleJson("issue-2.json")
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
