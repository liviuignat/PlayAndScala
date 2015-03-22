
import business.services.{SimpleUUIDGenerator, UUIDGenerator}
import com.tzavellas.sse.guice.ScalaModule

/**
 * Created by liviuignat on 21/03/15.
 */
class Module extends ScalaModule {
  def configure() {
    bind[UUIDGenerator].to[SimpleUUIDGenerator]
  }
}
