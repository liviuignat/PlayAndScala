package controllers

import javax.inject._
import controllers.requests.user.GetUserResponse
import play.api.libs.json.Json
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._
import play.modules.reactivemongo.MongoController

import scala.concurrent._

import business.repositories._
import business.services._
import business.models._


import controllers.requests.auth._
import controllers.requests.JsonFormats._
import controllers.requests.Mappings._



/**
 * Created by liviu.ignat on 3/24/2015.
 */
@Singleton
class AuthController @Inject() (encriptionService: IStringEncriptionService, userRepository: IUserRepository)  extends Controller with MongoController {
  def createUser = Action.async(parse.json) { req =>
    Json.fromJson[CreateUserRequest](req.body).fold(
      invalid => {
        Future.successful(BadRequest(Json.obj("message" -> "Invalid json")))
      },
      createUserRequest => {
        val promise = Promise[Result]

        userRepository.getByEmail(createUserRequest.email).map({
          case Some(user) => promise success BadRequest(Json.obj("message" -> "User already exists"))
          case None => {
            /* Automatic implicit conversion defined in requests.Mappings._ */
            val user: User = createUserRequest
            user.password = encriptionService.encryptMd5(user.password)

            userRepository.insert(user).map(user => user).map {
              user =>  promise success Created("").withHeaders("Location" -> user._id)
            }
          }
        })

        promise future
      }
    )
  }

  def login() = Action.async(parse.json) {
    req =>
      Json.fromJson[LoginRequest](req.body).fold(
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

  def resetPassword() = Action.async(parse.json) {
    req =>
      Json.fromJson[ResetPasswordRequest](req.body).fold(
        invalid => {
          Future.successful(BadRequest(Json.obj("message" -> "Invalid json")))
        },
        resetPasswordRequest => {
          userRepository.resetPassword(resetPasswordRequest.email).map({
            _ => Ok("")
          })
        }
      )
  }
}
