package dao

import javax.inject.Inject

import business.models.User
import business.repositories._
import common._
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.concurrent.Execution.Implicits._
import reactivemongo.api.Cursor

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

  override def getById(id: String): Future[Option[User]] = {
    collection
      .find(Json.obj("_id" -> id)).one[User]
  }

  override def getByEmail(email: String): Future[Option[User]] = {
    val selector = Json.obj("email" -> email)
    collection.find(selector).one[User]
  }

  override def getByEmailAndPassword(email: String, password: String): Future[Option[User]] = {
    val selector = Json.obj("$and" -> Json.obj(
        "email" -> email,
        "password" -> password,
        "active" -> true))

    collection.find(selector).one[User]
  }

  override def getAll(query: Option[String]): Future[List[User]] = {
    val selector = Json.obj("$or" -> Json.obj(
      "firstName" -> query,
      "lastName" -> query,
      "active" -> true))

    val cursor: Cursor[User] = collection.find(selector).cursor[User]

    cursor.collect[List]()
  }

  override def insert(user: User): Future[LastError] = {
    collection.insert(user).map {
      case ok if ok.ok =>
        NoError()
      case error => Error(Some(new RuntimeException(error.message)))
    }
  }

  override def update(user: User): Future[LastError] = ???

  override def resetPassword(email: String, newPassword: String): Future[LastError] = {
    val selector = Json.obj("email" -> email)
    val modifier = Json.obj("$set" -> Json.obj("password" -> newPassword))

    collection.update(selector, modifier, multi = true).map {
      case ok if ok.ok =>
        NoError()
      case error => Error(Some(new RuntimeException(error.message)))
    }
  }

  override def delete(id: Int): Future[LastError] = ???
}
