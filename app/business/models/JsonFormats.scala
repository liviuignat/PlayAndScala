package business.models

import play.api.libs.json.Json

/*
  Kept for reference
  import play.modules.reactivemongo.json.BSONFormats._
*/

/**
 * Created by liviuignat on 23/03/15.
 */
object JsonFormats {
  implicit val userFormat = Json.format[User]
}
