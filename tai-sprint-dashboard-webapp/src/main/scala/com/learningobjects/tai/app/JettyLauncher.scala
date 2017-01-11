package com.learningobjects.tai.app

import com.learningobjects.tai.app.config.Config
import org.eclipse.jetty.http.HttpVersion
import org.eclipse.jetty.server._
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.util.ssl.SslContextFactory
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener

object JettyLauncher {
  private val port = 8181

  private implicit class ServerConf(server: Server) {
    def configureSsl(): Unit = {
      server.removeConnector(server.getConnectors.head) // remove non-secure connections

      val sslContextFactory = new SslContextFactory(Config.keyStoreFile)
      sslContextFactory.setKeyStorePassword("secret")

      val httpConf = new HttpConfiguration()
      httpConf.setSecurePort(port)
      httpConf.setSecureScheme("https")
      httpConf.addCustomizer(new SecureRequestCustomizer())

      val connector = new ServerConnector(server,
        new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
        new HttpConnectionFactory(httpConf))
      connector.setPort(port)
      server.addConnector(connector)
    }
  }

  // this is my entry object as specified in sbt project definition
  def main(args: Array[String]) {
    val server = new Server(port)
    server.configureSsl()

    val context = new WebAppContext()
    context.setContextPath("/")
    context.setResourceBase(getClass.getClassLoader.getResource("webapp").toExternalForm)
    context.addEventListener(new ScalatraListener)
    context.addServlet(classOf[DefaultServlet], "/")

    server.setHandler(context)

    server.start()
    server.join()
  }
}