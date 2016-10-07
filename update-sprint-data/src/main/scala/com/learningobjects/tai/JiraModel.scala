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
//  def parentId: Option[Long]
//  def parentKey: Option[String]
  def typeName: String // E.g., "Sub-task"
  def summary: String
  def done: Boolean
  def statusName: String
//  def points: Option[Double]
}

case class Issue(
  id: Long,
  key: String,
//  parentId: Option[Long],
//  parentKey: Option[String],
  typeName: String,
  summary: String,
  done: Boolean,
  statusName: String
//  points: Option[Double]
) extends BaseIssue

case class IssueWithResolutionDate(
  id: Long,
  key: String,
//  parentId: Option[Long],
//  parentKey: Option[String],
  typeName: String,
  summary: String,
  done: Boolean,
  statusName: String,
//  points: Option[Double],
  resolutionDate: String
) extends BaseIssue