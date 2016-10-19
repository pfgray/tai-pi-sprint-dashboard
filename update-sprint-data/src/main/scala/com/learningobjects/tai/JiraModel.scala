package com.learningobjects.tai

/**
  *     (⌒_⌒;)
  */

case class Sprint(
  id: Long,
  name: String,
  startDate: String,
  endDate: String,
  daysRemaining: Long
)

case class IssueSummary(
  id: Long,
  key: String,
  typeName: String,
  summary: String,
  done: Boolean,
  statusName: String,
  parentId: Option[Long] = None,
  parentKey: Option[String] = None,
  points: Option[Double] = None
)

case class Issue(
  id: Long,
  key: String,
  typeName: String,
  summary: String,
  statusName: String,
  done: Boolean,
  resolutionDate: Option[String],
  parentId: Option[Long] = None,
  parentKey: Option[String] = None,
  points: Option[Double],
  subtaskKeys: Seq[String]
)

case class IssueNode(
  id: Long,
  key: String,
  typeName: String,
  summary: String,
  statusName: String,
  done: Boolean,
  resolutionDate: Option[String],
  points: Option[Double],
  children: Seq[IssueNode]
)

object IssueNode {
  def apply(issue:Issue) =
    new IssueNode(
      id = issue.id,
      key = issue.key,
      typeName = issue.typeName,
      summary = issue.summary,
      statusName = issue.statusName,
      done = issue.done,
      resolutionDate = issue.resolutionDate,
      points = issue.points,
      children = Seq()
    )
}