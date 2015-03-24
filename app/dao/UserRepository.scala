package dao

import javax.inject.Inject

import business.models.User
import business.repositories.{FindUsers, IUserRepository}
import business.services.IStringEncriptionService
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.concurrent.Execution.Implicits._
import reactivemongo.api.Cursor
import reactivemongo.api.indexes.{Index, IndexType}

import scala.concurrent.Future
import play.api.Play.current

import business.models.JsonFormats._

/**
 * Created by liviuignat on 22/03/15.
 */
class UserRepository @Inject() () extends IUserRepository {

  private def collection = ReactiveMongoPlugin.db
    .collection[JSONCollection]("app_users")
/*
  Causes some problems on reactive mongo - for sure some permissions

  collection.indexesManager.ensure(Index(List(
    "email" -> IndexType.Ascending), unique = false))
  collection.indexesManager.ensure(Index(List(
    "firstName" -> IndexType.Ascending), unique = false))
  collection.indexesManager.ensure(Index(List(
    "lastName" -> IndexType.Ascending), unique = false))
*/

  def getById(id: String): Future[Option[User]] = {
    collection
      .find(Json.obj("_id" -> id)).one[User]
  }

  override def getByEmail(email: String): Future[Option[User]] = {
    collection
      .find(Json.obj("email" -> email)).one[User]
  }

  def getByEmailAndPassword(email: String, password: String): Future[Option[User]] = {
    collection
      .find(Json.obj("email" -> email, "password" -> password, "active" -> true)).one[User]
  }

  def getAll(query: FindUsers): Future[List[User]] = {
    val cursor: Cursor[User] = collection.
      find(Json.obj("active" -> true)).
      cursor[User]

    cursor.collect[List]()
  }

  def insert(user: User): Future[User] = {
    collection.insert(user).map {
      case ok if ok.ok =>
        user
      case error => throw new RuntimeException(error.message)
    }
  }

  def update(user: User): Future[User] = ???

  override def resetPassword(email: String): Future[Unit] = ???

  def delete(id: Int): Future[Unit] = ???
}
