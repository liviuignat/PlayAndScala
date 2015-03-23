package controllers.requests

/**
 * Created by liviuignat on 22/03/15.
 */
case class CreateUserRequest( email: String,
                 password: String,
                 firstName: Option[String],
                 lastName: Option[String])
