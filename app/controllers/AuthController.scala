package controllers

import javax.inject._
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
        /* Automatic implicit conversion defined in requests.Mappings._ */
        val user: User = createUserRequest
        user.password = encriptionService.encryptMd5(user.password)

        userRepository.insert(user).map({
          user => Created("").withHeaders("Location" -> user._id)
        })
      }
    )
  }

  def login() = Action.async(parse.json) {
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
