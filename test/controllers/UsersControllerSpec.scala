import java.util.concurrent.TimeUnit

import org.scalatest.FunSpec
import play.api.libs.json._
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Await
import scala.concurrent.duration.FiniteDuration

class UsersControllerSpec extends FunSpec {
  val timeout: FiniteDuration = FiniteDuration(5, TimeUnit.SECONDS)

  describe("When wanting to insert a user") {

    it("Should be able to do it without any error") {
      ProjectTestUtils.runWithTestDatabase {
        ProjectTestUtils.clearDbData()

        val request = FakeRequest.apply("POST", "/user").withJsonBody(Json.obj(
          "email" -> "liviu@ignat.email",
          "password" -> "test123",
          "firstName" -> "Liviu",
          "lastName" -> "Ignat"))

        val response = route(request)
        assert(response.isDefined == true)

        val result = Await.result(response.get, timeout)
        assert(result.header.status == CREATED)
      }
    }
  }
}
