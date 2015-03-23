import java.util.concurrent.TimeUnit

import org.scalatest.{ShouldMatchers, BeforeAndAfter, BeforeAndAfterAll, FunSpec}
import play.api.Play
import play.api.mvc._
import play.api.libs.json._
import play.api.mvc.AnyContentAsJson
import play.api.test.Helpers._
import play.api.test.{FakeApplication, FakeRequest}

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.FiniteDuration

class UsersControllerSpec extends JasmineSpec with BeforeAndAfter with BeforeAndAfterAll with ShouldMatchers {
  val timeout: FiniteDuration = FiniteDuration(5, TimeUnit.SECONDS)
  val fakeApplication = FakeApplication();
  var firstUserId : String = "";

  Play.start(fakeApplication)
  ProjectTestUtils.clearDbData

  override def afterAll() = {
    Play.stop
  }

  describe("When wanting to insert a first user") {
    var response: Option[Future[Result]] = null
    beforeEach {
      val request = FakeRequest.apply("POST", "/user")
        .withJsonBody(Json.obj(
        "email" -> "liviu@ignat.email",
        "password" -> "test123",
        "firstName" -> "Liviu",
        "lastName" -> "Ignat"))
      response = route(request)
      val result = Await.result(response.get, timeout)
    }

    it("Should be able to the request with success") {
      response.isDefined should equal(true)
    }

    it("Should have the Status Header Created") {
      val result = Await.result(response.get, timeout)
      result.header.status should equal(CREATED)
    }

    it("Should have the Location header defined") {
      val result = Await.result(response.get, timeout)
      assert(result.header.headers.contains("Location") == true)
      firstUserId = result.header.headers.get("Location").get
    }

    it("Should have the Location header with length 24") {
      val result = Await.result(response.get, timeout)
      result.header.headers.get("Location").get.length should equal(24)
    }

    describe("When getting first user") {
      var response: Option[Future[Result]] = null
      beforeEach {
        val uri = s"/user/$firstUserId"
        println(uri)
        val request = FakeRequest.apply("PUT", uri)
        response = route(request)
      }

      it("Should be able make the request with success") {
        response.isDefined should equal(true)
        val result = Await.result(response.get, timeout)
        result.header.status should equal(OK)
      }

      it("Json response should not contain password") {
        val json: JsValue = contentAsJson(response.get)
        val password = json.\("password").toString()
        println(password)
      }
    }

    describe("When wanting to insert a second user") {
      var response: Option[Future[Result]] = null
      beforeEach {
        val request = FakeRequest.apply("POST", "/user")
          .withJsonBody(Json.obj(
          "email" -> "liviu.ignat@someotheremail.com",
          "password" -> "someotheremailtest123",
          "firstName" -> "Liviu Marius",
          "lastName" -> "Ignat Someother"))
        response = route(request)
        val result = Await.result(response.get, timeout)
      }

      it("Should be able make the request with success") {
        response.isDefined should equal(true)

        val result = Await.result(response.get, timeout)
        result.header.status should equal(CREATED)
      }
    }
  }

}
