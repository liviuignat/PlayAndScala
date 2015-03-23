package dao

import javax.inject.Inject

import business.models.User
import business.repositories.{FindUsers, IUserRepository}
import business.services.IStringEncriptionService
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future
import play.api.Play.current

import business.models.JsonFormats._

/**
 * Created by liviuignat on 22/03/15.
 */
class UserRepository @Inject() (encriptionService: IStringEncriptionService) extends IUserRepository {

  private def collection = ReactiveMongoPlugin.db.collection[JSONCollection]("users")

  def getById(id: String): Future[Option[User]] = {
    collection
      .find(Json.obj("_id" -> id)).one[User]
  }

  override def getAll(query: FindUsers): Future[List[User]] = ???

  def insert(user: User): Future[User] = {
    user.password = encriptionService.encryptMd5(user.password)
    collection.insert(user).map {
      case ok if ok.ok =>
        user
      case error => throw new RuntimeException(error.message)
    }
  }

  def update(user: User): Future[User] = ???

  override def delete(id: Int): Unit = ???
}
