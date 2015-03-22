package controllers

import javax.inject.{Inject, Singleton}

import business.repositories.IUserRepository
import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.json.Json
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

import business.models.JsonFormats._
import business.models._

/**
 * Created by liviuignat on 21/03/15.
 */

@Singleton
class UsersController @Inject() (userRepository: IUserRepository)  extends Controller with MongoController {
  private final val logger: Logger = LoggerFactory.getLogger(classOf[UsersController])

  def createUser = Action.async(parse.json) { req =>
    Json.fromJson[User](req.body).fold(
      invalid => {
        Future.successful(Ok("Invalid json"))
      },
      user => {
        user._id = Some(BSONObjectID.generate)
        userRepository.insert(user).map(_ => Created("User created"))
      }
    )
  }
}
