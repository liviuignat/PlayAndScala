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
import reactivemongo.bson.BSONRegex

import scala.concurrent.Future
import play.api.Play.current

import business.models.JsonFormats._

import scala.util.matching.Regex

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
      .find(Json.obj("_id" -> id,"isActive" -> true)).one[User]
  }

  override def getByEmail(email: String): Future[Option[User]] = {
    val selector =  Json.obj("email" -> email, "isActive" -> true)
    collection.find(selector).one[User]
  }

  override def getByEmailAndPassword(email: String, password: String): Future[Option[User]] = {
    val selector =  Json.obj("email" -> email, "password" -> password, "isActive" -> true)
    collection.find(selector).one[User]
  }

  override def getAll(query: Option[String]): Future[List[User]] = {

    val selector = query match {
      case None => Json.obj()
      case Some("") => Json.obj()
      case Some(query) => {
        val regex = Json.obj("$regex" -> (".*" + query + ".*"), "$options" -> "-i")
        Json.obj("$or" -> Seq(
          Json.obj("firstName" -> regex),
          Json.obj("lastName" -> regex)
        ))
      }
    };

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

  override def update(user: User): Future[LastError] = {
    val selector = Json.obj("_id" -> user._id)
    val jsonToUpdate = Json.obj(
      "firstName" -> user.firstName,
      "lastName" -> user.lastName)
    val modifier = Json.obj("$set" -> jsonToUpdate)

    collection.update(selector, modifier, multi = true).map {
      case ok if ok.ok =>
        NoError()
      case error => Error(Some(new RuntimeException(error.message)))
    }
  }

  override def resetPassword(email: String, newPassword: String): Future[LastError] = {
    val selector = Json.obj("email" -> email)
    val modifier = Json.obj("$set" -> Json.obj("password" -> newPassword))

    collection.update(selector, modifier, multi = true).map {
      case ok if ok.ok =>
        NoError()
      case error => Error(Some(new RuntimeException(error.message)))
    }
  }

  override def delete(id: String): Future[LastError] = {
    val selector = Json.obj("_id" -> id)

    collection.remove(selector).map {
      case ok if ok.ok =>
        NoError()
      case error => Error(Some(new RuntimeException(error.message)))
    }
  }
}
