package controllers

import javax.inject.{Inject, Singleton}

import business.models.User
import business.repositories.IUserRepository
import controllers.requests.CreateUserRequest
import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.json.Json
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

import requests._
import requests.JsonFormats._
import requests.Mappings._

/**
 * Created by liviuignat on 21/03/15.
 */

@Singleton
class UsersController @Inject() (userRepository: IUserRepository)  extends Controller with MongoController {
  private final val logger: Logger = LoggerFactory.getLogger(classOf[UsersController])

  def createUser = Action.async(parse.json) { req =>
    Json.fromJson[CreateUserRequest](req.body).fold(
      invalid => {
        Future.successful(Ok("Invalid json"))
      },
      createUserRequest => {
        /* Automatic implicit conversion defined in requests.Mappings._ */
        val user: User = createUserRequest
        userRepository.insert(user).map(_ => Created("User created"))
      }
    )
  }
}
