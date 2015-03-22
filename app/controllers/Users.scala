package controllers

//import javax.inject.Singleton
//
//import org.slf4j.{Logger, LoggerFactory}
//
//import play.api.mvc._
//import play.api.libs.json._
//import play.modules.reactivemongo.MongoController
//import play.modules.reactivemongo.json.collection.JSONCollection
//
//import scala.concurrent.Future

import javax.inject.Singleton

import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection

import scala.concurrent.Future

/**
 * Created by liviuignat on 21/03/15.
 */

@Singleton
class Users extends Controller with MongoController {
  private final val logger: Logger = LoggerFactory.getLogger(classOf[Users])

  def collection: JSONCollection = db.collection[JSONCollection]("users")

  import business.models.JsonFormats._
  import business.models._

  def createUser = Action.async(parse.json) {
    request =>
      request.body.validate[User].map {
        user =>
          collection.insert(user).map {
            lastError =>
              logger.debug(s"Successfully inserted with LastError: $lastError")
              Created(s"User Created")
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }
}
