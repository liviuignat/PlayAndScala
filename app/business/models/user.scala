package business.models

import play.api.libs.json.Json

case class User( email: String,
                 password: String,
                 firstName: String,
                 lastName: String)

object JsonFormats {

  // Generates Writes and Reads for Feed and User thanks to Json Macros
  implicit val userFormat = Json.format[User]
}
