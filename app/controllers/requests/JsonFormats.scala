package controllers.requests

import play.api.libs.json.Json

/**
 * Created by liviuignat on 22/03/15.
 */
object JsonFormats {
  implicit val createUserRequestFormat = Json.format[CreateUserRequest]
  implicit val getUserResponseFormat = Json.format[GetUserResponse]
  implicit val getUserByEmailAndPasswordFormat = Json.format[GetUserByEmailAndPassword]
  implicit val updateUserRequestFormat = Json.format[UpdateUserRequest]
}
