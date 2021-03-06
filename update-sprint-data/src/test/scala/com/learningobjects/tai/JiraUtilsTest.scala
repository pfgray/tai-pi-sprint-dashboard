package com.learningobjects.tai

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by bsmith on 10/19/16.
  */
class JiraUtilsTest extends FlatSpec with Matchers {

  object Issue {
    def apply(id:Long, key: String, parentKey: Option[String], subtaskKeys:Seq[String]): Issue = new Issue(
      id = id,
      key = key,
      typeName = "",
      summary = "",
      statusName = "",
      done = true,
      resolutionDate = Some("2999-12-31"),
      parentId = None,
      parentKey = parentKey,
      points = Some(1.0),
      subtaskKeys = subtaskKeys
    )
  }
  
  object IssueNode {
    def apply(points: Double): IssueNode = new IssueNode(
      id = 1,
      key = 1.toString,
      typeName = "",
      summary = "",
      statusName = "",
      done = true,
      resolutionDate = None,
      points = Some(points),
      children = Seq()
    )

    def apply(points: Double, childrenPoints: Seq[Double]): IssueNode =
      IssueNode(points).copy(children = childrenPoints map IssueNode.apply)

  }

  "JiraUtils" should "return a sequence without any relationships" in {
    val test = Seq(
      Issue(1, 1.toString, None, Seq()),
      Issue(2, 2.toString, None, Seq()),
      Issue(3, 3.toString, None, Seq())
    )
    val found = JiraUtils.bind(test)
    found.size should be (3)

    found.head.id should be (1)
    found.head.children.isEmpty should be (true)
    found(1).id should be (2)
    found(1).children.isEmpty should be (true)
    found(2).id should be (3)
    found(2).children.isEmpty should be (true)
  }

  it should "correctly identify parent and children" in {
    val test = Seq(
      Issue(1, 1.toString, None, Seq(2.toString)),
      Issue(2, 2.toString, Some(1.toString), Seq()),
      Issue(3, 3.toString, None, Seq())
    )
    val found = JiraUtils.bind(test)
    found.size should be (2)

    found.head.id should be (1)
    found.head.children.size should be (1)
    found.head.children.head.id should be (2)
    found(1).id should be (3)
    found(1).children.isEmpty should be (true)
  }

  it should "not filter out child if parent not found" in {
    val test = Seq(
      Issue(1, 1.toString, None, Seq()),
      Issue(2, 2.toString, Some(7.toString), Seq()),
      Issue(3, 3.toString, None, Seq())
    )
    val found = JiraUtils.bind(test)
    found.size should be (3)
  }

  it should "return no children if not children not found" in {
    val test = Seq(
      Issue(1, 1.toString, None, Seq(7.toString)),
      Issue(2, 2.toString, None, Seq()),
      Issue(3, 3.toString, None, Seq())
    )
    val found = JiraUtils.bind(test)
    found.size should be (3)

    found(0).children.isEmpty should be (true)
  }

  it should "return correct point for an issue without children" in {
    val test = IssueNode(2.5)
    JiraUtils.points(test) should be (2.5)

  }

  it should "return ignore a parent's points in favor of summing children" in {
    val test = IssueNode(2.5, Seq(0, 1, 0, .5, 1, 2))
    JiraUtils.points(test) should be (4.5)
  }

}
