import java.util.concurrent.TimeUnit

import play.api.Application
import play.api.test.FakeApplication
import play.api.test.Helpers._
import play.modules.reactivemongo.ReactiveMongoPlugin
import reactivemongo.api.{MongoConnection, MongoDriver}

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, Future}
import scala.util.Try
import play.api.libs.concurrent.Execution.Implicits._

/**
 * Created by liviuignat on 21/03/15.
 */
object ProjectTestUtils {
  def runWithTestDatabase[T](block: => T) {
    //val fakeApp = FakeApplication(additionalConfiguration = inMemoryDatabase())
    val fakeApplication = FakeApplication();

    running(fakeApplication) {
      //ProjectRepositoryFake.insertTestDataIfEmpty()
      block
    }
  }

  def clearDbData(): Boolean = {
    val uri = "mongodb://scalaplaymongo:scalaplaymongo123@ds045521.mongolab.com:45521/heroku_app35066918"
    val driver = new MongoDriver
    val connection: Try[MongoConnection] =
      MongoConnection.parseURI(uri).map { parsedUri =>
        driver.connection(parsedUri)
      }

    val db = connection.get.db("heroku_app35066918")

    val result = Await.result(db.drop().map {
      success => success
    }, FiniteDuration(5, TimeUnit.SECONDS))

    result
  }
}
