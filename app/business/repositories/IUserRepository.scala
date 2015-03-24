package business.repositories

import business.models.User
import common._

import scala.concurrent.Future

/**
 * Created by liviuignat on 22/03/15.
 */
trait IUserRepository {
  def getById(id: String): Future[Option[User]]

  def getByEmail(email: String): Future[Option[User]]

  def getByEmailAndPassword(email: String, password: String): Future[Option[User]]

  def getAll(query: FindUsers): Future[List[User]]

  def insert(user: User): Future[LastError]

  def update(user: User): Future[LastError]

  def resetPassword(email: String, newPassword: String): Future[LastError]

  def delete(id: Int): Future[LastError]
}

case class FindUsers( email: Option[String],
                      firstName: Option[String],
                      lastName: Option[String])
