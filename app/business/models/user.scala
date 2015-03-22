package business.models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json.BSONFormats._

case class User( var _id: Option[BSONObjectID],
                 email: String,
                 password: String,
                 firstName: Option[String],
                 lastName: Option[String])

object JsonFormats {
  implicit val userFormat = Json.format[User]
}
