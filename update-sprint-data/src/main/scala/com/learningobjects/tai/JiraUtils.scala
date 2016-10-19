package com.learningobjects.tai

/**
  * Created by bsmith on 10/19/16.
  */
object JiraUtils {

  /**
    * Constructs parent-child relationships.
    */
  def bind(issues:Seq[Issue]): Seq[IssueNode] = {

    val nodes = issues map IssueNode.apply

    def findNode(key: String) = nodes.find(_.key == key)
    def findChild(key: String, nodes: Seq[IssueNode]) =
      nodes.exists(_.children.exists(_.key == key))

    val expandedNodes = issues
      .zip(nodes)
      .map(tuple => {
        val issue = tuple._1
        val node = tuple._2
        val children = issue.subtaskKeys
             .flatMap(key => findNode(key))
        node.copy(children = children)
      })

    expandedNodes.filterNot(node => findChild(node.key, expandedNodes))
  }
}
