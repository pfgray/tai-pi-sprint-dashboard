import javax.servlet.ServletContext

import com.learningobjects.tai.app._
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new TaiScalatraServlet, "/*")
  }
}
