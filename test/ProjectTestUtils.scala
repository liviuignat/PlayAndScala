import play.api.test.FakeApplication
import play.api.test.Helpers._

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
}
