
import com.tzavellas.sse.guice.ScalaModule
import business.repositories._
import business.services._
import dao._

/**
 * Created by liviuignat on 21/03/15.
 */
class Module extends ScalaModule {
  def configure() {
    bind[IUserRepository].to[UserRepository]

    bind[IStringEncriptionService].to[StringEncriptionService]
    bind[IRandomStringService].to[RandomStringService]
    bind[IEmailService].to[EmailService]
  }
}
