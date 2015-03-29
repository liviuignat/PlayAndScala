package controllers.requests

import business.models.User
import controllers.requests.auth.CreateUserRequest
import controllers.requests.user.{GetUserResponse, UpdateUserRequest}
import reactivemongo.bson.BSONObjectID

/**
 * Created by liviuignat on 22/03/15.
 */
object Mappings {
  implicit def createUserRequestToUser(req: CreateUserRequest) =
    User(BSONObjectID.generate.stringify, req.email, req.password, req.firstName, req.lastName)

  implicit def updateUserRequestToUser(req: UpdateUserRequest) =
    User(_id = req.id.get,
      email = null,
      password = null,
      firstName = req.firstName,
      lastName = req.lastName)

  implicit def userToGetUserResponse(u: User) =
    GetUserResponse(id = u._id,
      firstName = u.firstName.getOrElse(""),
      lastName = u.lastName.getOrElse(""),
      isActive = u.isActive)
}
