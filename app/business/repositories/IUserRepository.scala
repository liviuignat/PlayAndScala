package business.repositories

import business.models.User

import scala.concurrent.Future

/**
 * Created by liviuignat on 22/03/15.
 */
trait IUserRepository {
  def insert(user: User): Future[User];
}
