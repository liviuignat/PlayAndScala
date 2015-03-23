package business.models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json.BSONFormats._

case class User( _id: String,
                 var email: String,
                 var password: String,
                 var firstName: Option[String],
                 var lastName: Option[String],
                 var isActive: Boolean = true)

object JsonFormats {
  implicit val userFormat = Json.format[User]
}
