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

trait BaseIssue {
  def id: Long
  def key: String // E.g., ABC-123
  def typeName: String // E.g., "Sub-task"
  def summary: String
  def done: Boolean
  def statusName: String
  def parentId: Option[Long]
  def parentKey: Option[String]
  def points: Option[Double]
}

case class IssueFromSprint(
  id: Long,
  key: String,
  typeName: String,
  summary: String,
  done: Boolean,
  statusName: String,
  parentId: Option[Long] = None,
  parentKey: Option[String] = None,
  points: Option[Double] = None
) extends BaseIssue

case class Issue(
  id: Long,
  key: String,
  typeName: String,
  summary: String,
  done: Boolean,
  statusName: String,
  resolutionDate: String,
  parentId: Option[Long] = None,
  parentKey: Option[String] = None,
  points: Option[Double] = None
) extends BaseIssue
