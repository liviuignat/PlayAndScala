package controllers.requests

import play.api.libs.json.Json

/**
 * Created by liviuignat on 22/03/15.
 */
case class UpdateUserRequest(
                var id: Option[String],
                var firstName: Option[String],
                var lastName: Option[String])
