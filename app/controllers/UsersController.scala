package controllers

import javax.inject.{Inject, Singleton}

import business.repositories.IUserRepository
import business.services._
import controllers.requests._
import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.json.Json
import play.api.libs.json._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._
import play.modules.reactivemongo.MongoController

import scala.concurrent.Future

import business.models.User
import requests.JsonFormats._
import requests.Mappings._

/**
 * Created by liviuignat on 21/03/15.
 */

@Singleton
class UsersController @Inject() (encriptionService: IStringEncriptionService, userRepository: IUserRepository)  extends Controller with MongoController {
  private final val logger: Logger = LoggerFactory.getLogger(classOf[UsersController])

  def createUser = Action.async(parse.json) { req =>
    Json.fromJson[CreateUserRequest](req.body).fold(
      invalid => {
        Future.successful(BadRequest(Json.obj("message" -> "Invalid json")))
      },
      createUserRequest => {
        /* Automatic implicit conversion defined in requests.Mappings._ */
        val user: User = createUserRequest
        user.password = encriptionService.encryptMd5(user.password)

        userRepository.insert(user).map({
          user => Created("").withHeaders("Location" -> user._id)
        })
      }
    )
  }

  def updateUserById(id: String) = Action.async(parse.json) { req =>
    Json.fromJson[UpdateUserRequest](req.body).fold(
      invalid => {
        Future.successful(BadRequest(Json.obj("message" -> "Invalid json")))
      },
      updateUserRequest => {
        updateUserRequest.id = Some(id)
        val user: User = updateUserRequest

        userRepository.update(user).map({
          user => Ok("")
        })
      }
    )
  }

  def getUserById(id: String) = Action.async {
    userRepository.getById(id).map {
      case Some(user) => {
        val response: GetUserResponse = user
        val responseJson = Json.toJson(response)
        Ok(responseJson)
      }
      case None => NotFound(Json.obj("message" -> "No such item"))
    }
  }

  def getByEmailAndPassword() = Action.async(parse.json) {
    req =>
      Json.fromJson[GetUserByEmailAndPassword](req.body).fold(
        invalid => {
          Future.successful(BadRequest(Json.obj("message" -> "Invalid json")))
        },
        getUserRequest => {
          val password = encriptionService.encryptMd5(getUserRequest.password)

          userRepository.getByEmailAndPassword(getUserRequest.email, password).map({
            case Some(user) => {
              val response: GetUserResponse = user
              val responseJson = Json.toJson(response)
              Ok(responseJson)
            }
            case None => NotFound(Json.obj("message" -> "No such item"))
          })
        }
      )
  }
}
