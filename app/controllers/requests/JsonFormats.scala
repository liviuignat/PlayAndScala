package controllers.requests

import play.api.libs.json.Json

/**
 * Created by liviuignat on 22/03/15.
 */
object JsonFormats {
  implicit val createUserRequestFormat = Json.format[CreateUserRequest]
  implicit val updateUserRequestFormat = Json.format[UpdateUserRequest]
}