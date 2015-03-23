package controllers

import javax.inject.{Inject, Singleton}
import org.slf4j.{Logger, LoggerFactory}
import play.api.mvc._


@Singleton
class IndexController @Inject() () extends Controller {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[ApplicationController])

  def index = Action {
    logger.info("Serving index page...")
    Ok(views.html.index())
  }

}
