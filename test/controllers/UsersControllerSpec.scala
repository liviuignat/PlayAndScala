package test.controllers

import java.util.concurrent.TimeUnit

import org.scalatest._
import play.api.Play
import play.api.mvc._
import play.api.libs.json._
import play.api.mvc.AnyContentAsJson
import play.api.test.Helpers._
import play.api.test._
import tests._

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.FiniteDuration

class UsersControllerSpec extends JasmineSpec with BeforeAndAfter with BeforeAndAfterAll with ShouldMatchers {
  val timeout: FiniteDuration = FiniteDuration(5, TimeUnit.SECONDS)
  var firstUserId : String = "";

  override def beforeAll() = {
    Play.start(ProjectTestUtils.fakeApplication)
  }

  override def afterAll() = {
    Play.stop
  }

  describe("UsersControllerSpec") {
    beforeEach {
      ProjectTestUtils.dropDb()
    }

    describe("When wanting to insert a first user") {
      var response: Option[Future[Result]] = null
      var result: Result = null
      beforeEach {
        val request = FakeRequest.apply("POST", "/api/auth/create")
          .withJsonBody(Json.obj(
          "email" -> "liviu@ignat.email",
          "password" -> "test123",
          "firstName" -> "Liviu",
          "lastName" -> "Ignat"))
        response = route(request)

        result = Await.result(response.get, timeout)
        firstUserId = result.header.headers.get("Location").get
      }

      describe("When getting first user") {
        var response: Option[Future[Result]] = null
        var result: Result = null
        beforeEach {
          val uri = s"/api/user/$firstUserId"
          val request = FakeRequest.apply("GET", uri)
          response = route(request)
          result = Await.result(response.get, timeout)
        }

        it("Should have a defined response") {
          response.isDefined should equal(true)
        }

        it("Should be able make the request with success") {
          result.header.status should equal(OK)
        }

        it("Should not have response password undefined") {
          val json: JsValue = contentAsJson(response.get)
          val password = json.\("password")

          assert(password.getClass == (new JsUndefined("")).getClass)
        }
      }
    }
  }


}
