package controllers.requests

import business.models.User
import reactivemongo.bson.BSONObjectID

/**
 * Created by liviuignat on 22/03/15.
 */
object Mappings {
  implicit def createUserRequestToUser(req: CreateUserRequest) =
    User(Some(BSONObjectID.generate), req.email, req.password, req.firstName, req.lastName)
}
