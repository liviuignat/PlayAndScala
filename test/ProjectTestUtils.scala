package tests

import java.util.concurrent.TimeUnit

import play.api.{Play, Application}
import play.api.test.FakeApplication
import play.api.test.Helpers._
import play.api.Play.current
import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver}

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, Future}
import scala.util.Try
import play.api.libs.concurrent.Execution.Implicits._

/**
 * Created by liviuignat on 21/03/15.
 */
object ProjectTestUtils {
  def fakeApplication (): Application = {
    FakeApplication(additionalConfiguration = inMemoryDatabase())
    FakeApplication()
  }

  def runWithTestDatabase[T](block: => T) {
    running(fakeApplication) {
      block
    }
  }

  def dbConnection: DefaultDB = {
    val uri = Play.configuration.getString("mongodb.uri").get
    val dbName = Play.configuration.getString("mongodb.dbName").get

    val driver = new MongoDriver
    val connection: Try[MongoConnection] =
      MongoConnection.parseURI(uri).map { parsedUri =>
        driver.connection(parsedUri)
      }

    connection.get.db(dbName)
  }

  def dropDb(): Boolean = {
    val db = dbConnection;
    val result = Await.result(db.drop().map {
      success => success
    }, FiniteDuration(5, TimeUnit.SECONDS))
    result
  }
}
