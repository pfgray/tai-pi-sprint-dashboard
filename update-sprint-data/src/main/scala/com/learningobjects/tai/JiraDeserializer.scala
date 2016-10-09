package com.learningobjects.tai

import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, _}

/**
  *     =O
  */
object JiraDeserializer {

  /**
    *
    */
  private implicit val sprintReads: Reads[Sprint] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "name").read[String] and
      (JsPath \ "startDate").read[String] and
      (JsPath \ "endDate").read[String] and
      (JsPath \ "daysRemaining").read[Long]
    )(Sprint.apply _)

  /**
    *
    */
  private implicit val issueSummaryReads: Reads[IssueSummary] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "key").read[String] and
      (JsPath \ "typeName").read[String] and
      (JsPath \ "summary").read[String] and
      (JsPath \ "done").read[Boolean] and
      (JsPath \ "statusName").read[String] and
      (JsPath \ "parentId").readNullable[Long] and
      (JsPath \ "parentKey").readNullable[String] and

      //
      // TODO: can't figure out how to handle optional estimateStatistic property,
      //       so using None and setting later.
      //

      //      (JsPath \ "estimateStatistic" \ "statFieldValue" \ "value" ).readNullable[Double]
      Reads.pure(None)
    )(IssueSummary.apply _)

  /**
    *
    */
  private implicit val issueReads: Reads[Issue] = {
    val issueDate = (JsPath \ "fields" \ "resolutiondate").readNullable[String]
    (
      (JsPath \ "id").read[String].map(_.toLong) and
        (JsPath \ "key").read[String] and
        (JsPath \ "fields" \ "issuetype" \ "name").read[String] and
        (JsPath \ "fields" \ "summary").read[String] and
        (JsPath \ "fields" \ "status" \ "name").read[String] and
        issueDate.map(_.isDefined) and
        issueDate and
        Reads.pure(None) and
        Reads.pure(None) and
        (JsPath \ "fields" \ "customfield_10008").readNullable[Double] and
        Reads.pure(Seq())
//        (JsPath \ "fields" \ "subtasks" \\ "key").read[Seq[String]]
      )(Issue.apply _)
  }

  /**
    *
    */
  def sprint(json:JsValue): Sprint = json.validate[Sprint].get

  /**
    *
    */
  def issueSummary(json: JsValue): IssueSummary = {
    val pointsResult = json \ "estimateStatistic" \ "statFieldValue" \ "value"
    json.validate[IssueSummary]
        .map(_.copy(points = pointsResult.toOption.map(_.as[Double])))
        .get
  }

  /**
    *
    */
  def issue(json: JsValue): Issue = {

    val subtaskKeys = (json \ "fields" \ "subtasks").as[JsArray]
          .value
          .map(subtask => (subtask \ "key").as[String])

    val parentId = (json \ "fields" \ "parent" \ "id")
      .toOption
      .map(_.as[String].toLong)

    val parentKey = (json \ "fields" \ "parent" \ "key")
      .toOption
      .map(_.as[String])

    json.validate[Issue]
        .map(_.copy(subtaskKeys = subtaskKeys,
                    parentId = parentId,
                    parentKey = parentKey))
        .get
  }
  
}
