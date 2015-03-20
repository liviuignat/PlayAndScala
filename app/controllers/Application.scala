package controllers

import java.io.File
import javax.inject.{Inject, Singleton}

import org.slf4j.{Logger, LoggerFactory}
import play.api.mvc._

@Singleton
class Application @Inject() () extends Controller {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Application])

  def index() = staticFile("public/dev/assets/index.html")

  def app(path: String) = staticFile("public/assets/serve/index.html")

  def staticFile(file: String) = Action {
    val f = new File(file)

    if (f.exists())
      Ok(scala.io.Source.fromFile(f.getCanonicalPath()).mkString).as("text/html")
    else
      NotFound("Resource not found")
  }
}
