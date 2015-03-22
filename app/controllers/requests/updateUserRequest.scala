package controllers.requests

import play.api.libs.json.Json

/**
 * Created by liviuignat on 22/03/15.
 */
case class UpdateUserRequest(
                _id: Option[String],
                firstName: Option[String],
                lastName: Option[String])
