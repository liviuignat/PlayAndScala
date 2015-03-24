package business.services

import common._
import scala.concurrent.Future

/**
 * Created by liviu.ignat on 3/24/2015.
 */
trait IEmailService {
  def sendCreatedAccountEmail(to: String): Future[LastError]
  def sendResetPasswordEmail(to: String): Future[LastError]
}

class EmailService extends IEmailService {
  override def sendCreatedAccountEmail(to: String): Future[LastError] = Future.successful(NoError())
  override def sendResetPasswordEmail(to: String): Future[LastError] = Future.successful(NoError())
}
