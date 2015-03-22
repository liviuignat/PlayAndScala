package dao

import business.models.User
import business.repositories.IUserRepository
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future
import play.api.Play.current

import business.models.JsonFormats._
import business.models._

/**
 * Created by liviuignat on 22/03/15.
 */
class UserRepository extends IUserRepository {

  private def collection = ReactiveMongoPlugin.db.collection[JSONCollection]("users")

  def insert(user: User): Future[User] = {
    collection.insert(user).map {
      case ok if ok.ok =>
        user
      case error => throw new RuntimeException(error.message)
    }
  }
}
