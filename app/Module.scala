
import business.repositories.IUserRepository
import business.services.{SimpleUUIDGenerator, UUIDGenerator}
import com.tzavellas.sse.guice.ScalaModule
import dao.UserRepository

/**
 * Created by liviuignat on 21/03/15.
 */
class Module extends ScalaModule {
  def configure() {
    bind[IUserRepository].to[UserRepository]
    bind[UUIDGenerator].to[SimpleUUIDGenerator]
  }
}
