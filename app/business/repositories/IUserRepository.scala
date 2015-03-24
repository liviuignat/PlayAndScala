package business.repositories

import business.models.User

import scala.concurrent.Future

/**
 * Created by liviuignat on 22/03/15.
 */
trait IUserRepository {
  def getById(id: String): Future[Option[User]]

  def getByEmail(email: String): Future[Option[User]]

  def getByEmailAndPassword(email: String, password: String): Future[Option[User]]

  def getAll(query: FindUsers): Future[List[User]]

  def insert(user: User): Future[User]

  def update(user: User): Future[User]

  def resetPassword(email: String): Future[Unit]

  def delete(id: Int): Future[Unit]
}

case class FindUsers( email: Option[String],
                      firstName: Option[String],
                      lastName: Option[String])
