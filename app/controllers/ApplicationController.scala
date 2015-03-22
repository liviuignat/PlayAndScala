package controllers

import javax.inject.{Inject, Singleton}

import org.slf4j.{Logger, LoggerFactory}
import play.api.mvc._

@Singleton
class ApplicationController @Inject() () extends Controller {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[ApplicationController])

  def index() = Action {
    Ok(views.html.app())
  }

  def app(path: String) = Action {
    Ok(views.html.app())
  }
}
