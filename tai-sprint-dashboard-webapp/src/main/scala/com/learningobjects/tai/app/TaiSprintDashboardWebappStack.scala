package com.learningobjects.tai.app

import org.scalatra._
import scalate.ScalateSupport
import org.fusesource.scalate.{ TemplateEngine, Binding }
import org.fusesource.scalate.layout.DefaultLayoutStrategy
import javax.servlet.http.HttpServletRequest
import collection.mutable

trait TaiSprintDashboardWebappStack extends ScalatraServlet with ScalateSupport {

  notFound {
    serveStaticResource() getOrElse  {
      // remove content type in case it was set through an action
      contentType = null
      // Try to render a ScalateTemplate if no route matched
      findTemplate(requestPath) map { path =>
        contentType = "text/html"
        layoutTemplate(path)
      } getOrElse resourceNotFound()
    }
  }

}
