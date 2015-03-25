package controllers

import javax.inject._
import controllers.requests.user.{GetUserResponse, UpdateUserRequest}
import org.slf4j._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._
import play.modules.reactivemongo.MongoController

import scala.concurrent.Future

import business.repositories._
import business.services._
import business.models._

import requests._
import requests.JsonFormats._
import requests.Mappings._


/**
 * Created by liviuignat on 21/03/15.
 */

@Singleton
class UsersController @Inject() (encriptionService: IStringEncriptionService, userRepository: IUserRepository)  extends Controller with MongoController {
  private final val logger: Logger = LoggerFactory.getLogger(classOf[UsersController])

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

  def getUserSearch() = Action.async(parse.anyContent) {
    req => {
      val query = req.getQueryString("q");

      userRepository.getAll(query).map({
        users => {
          val response: List[GetUserResponse] = users.map(user => {
            val userResponse: GetUserResponse = user
            userResponse
          })
          Ok(Json.toJson(response))
        }
      })
    }
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
          case lastError if lastError.ok() => Ok("")
          case lastError if !lastError.ok() => InternalServerError(Json.obj("message" -> "Internal server error"))
        })
      }
    )
  }

  def deleteUserById(id: String) = Action.async {
      userRepository.delete(id).map({
        case lastError if lastError.ok() => Ok("")
        case lastError if !lastError.ok() => InternalServerError(Json.obj("message" -> "Internal server error"))
      })
  }
}
