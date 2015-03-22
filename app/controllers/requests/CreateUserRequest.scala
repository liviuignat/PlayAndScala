package controllers.requests

import play.api.libs.json.Json

/**
 * Created by liviuignat on 22/03/15.
 */
case class CreateUserRequest( email: String,
                 password: String,
                 firstName: Option[String],
                 lastName: Option[String])
