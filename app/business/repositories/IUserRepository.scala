package business.repositories

import business.models.User

import scala.concurrent.Future

/**
 * Created by liviuignat on 22/03/15.
 */
trait IUserRepository {
  def getById(id: String): Future[Option[User]];

  def getAll(query: FindUsers): Future[List[User]]

  def insert(user: User): Future[User];

  def update(user: User): Future[User];

  def delete(id: Int): Unit;
}


case class FindUsers(userName: String)
