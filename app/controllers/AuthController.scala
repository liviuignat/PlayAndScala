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
class AuthController @Inject() (encriptionService: IStringEncriptionService,
                                randomStringGenerator: IRandomStringService,
                                emailService: IEmailService,
                                userRepository: IUserRepository)  extends Controller with MongoController {

  def createUser = Action.async(parse.json) { req =>
    req.body.validate[CreateUserRequest].map {
      createUserRequest => {
        val user: User = createUserRequest
        user.password = encriptionService.encryptMd5(user.password)
        val promise = Promise[Result]

        userRepository.getByEmail(createUserRequest.email).map({
          case Some(user) => promise success BadRequest(Json.obj("message" -> "User already exists"))
          case None => {
            userRepository.insert(user).map(user => user).map {
              case lastError if !lastError.ok() => promise success InternalServerError(Json.obj("message" -> "Internal server error"))
              case lastError if lastError.ok() => {
                emailService.sendCreatedAccountEmail(user.email).map {
                  lastError => promise success Created("").withHeaders("Location" -> user._id)
                }
              }
            }
          }
        })

        promise future
      }
    }.getOrElse(Future.successful(BadRequest(Json.obj("message" -> "Invalid json"))))
  }

  def login() = Action.async(parse.json) { req =>
    req.body.validate[LoginRequest].map {
      getUserRequest => {
        userRepository.getByEmailAndPassword(getUserRequest.email, getUserRequest.password).map({
          case Some(user) => {
            val response: GetUserResponse = user
            val responseJson = Json.toJson(response)
            Ok(responseJson)
          }
          case None => BadRequest(Json.obj("message" -> "No such item"))
        })
      }
    }.getOrElse(Future.successful(BadRequest(Json.obj("message" -> "Invalid json"))))
  }

  def resetPassword() = Action.async(parse.json) { req =>
    req.body.validate[ResetPasswordRequest].map {
      resetPasswordRequest => {
        val newPassword = randomStringGenerator.randomAlphaNumeric(8)
        val newMd5Password = encriptionService.encryptMd5(newPassword)

        val promise = Promise[Result]

        userRepository.resetPassword(resetPasswordRequest.email, newMd5Password).map({
          case lastError if !lastError.ok() => promise success InternalServerError(Json.obj("message" -> "Internal server error"))
          case lastError if lastError.ok() => {
            emailService.sendResetPasswordEmail(resetPasswordRequest.email).map {
              lastError => promise success Ok("")
            }
          }
        })

        promise future
      }
    }.getOrElse(Future.successful(BadRequest(Json.obj("message" -> "Invalid json"))))
  }
}
