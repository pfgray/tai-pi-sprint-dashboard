package com.learningobjects.tai.app

import org.scalatra._

class TaiScalatraServlet extends TaiSprintDashboardWebappStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

}
