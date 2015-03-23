package business.models

case class User( _id: String,
                 var email: String,
                 var password: String,
                 var firstName: Option[String],
                 var lastName: Option[String],
                 var isActive: Boolean = true)

