import com.google.inject.Guice
import play.api._

/**
 * Set up the Guice injector and provide the mechanism for return objects from the dependency graph.
 */
object Global extends GlobalSettings {
  private lazy val injector = {
//    Play.isProd match {
//      case true => Guice.createInjector(new Module)
//      case false => Guice.createInjector(new Module)
//    }
    Guice.createInjector(new Module)
  }

  override def getControllerInstance[A](controllerClass: Class[A]) = {
    injector.getInstance(controllerClass)
  }
}
