package controllers.requests

import controllers.requests.auth.{LoginRequest, ResetPasswordRequest, CreateUserRequest}
import controllers.requests.user.{GetUserResponse, UpdateUserRequest}
import play.api.libs.json.Json

/**
 * Created by liviuignat on 22/03/15.
 */
object JsonFormats {
  implicit val CreateUserRequestFormat = Json.format[CreateUserRequest]
  implicit val GetUserResponseFormat = Json.format[GetUserResponse]
  implicit val LoginFormat = Json.format[LoginRequest]

  implicit val ResetPasswordRequestFormat = Json.format[ResetPasswordRequest]
  implicit val UpdateUserRequestFormat = Json.format[UpdateUserRequest]
}
