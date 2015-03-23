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
        val request = FakeRequest.apply("POST", "/user")
          .withJsonBody(Json.obj(
          "email" -> "liviu@ignat.email",
          "password" -> "test123",
          "firstName" -> "Liviu",
          "lastName" -> "Ignat"))
        response = route(request)

        result = Await.result(response.get, timeout)
        firstUserId = result.header.headers.get("Location").get
      }

      it("Should be able to the request with success") {
        response.isDefined should equal(true)
      }

      it("Should have the Status Header Created") {
        result.header.status should equal(CREATED)
      }

      it("Should have the Location header defined") {
        assert(result.header.headers.contains("Location") == true)
      }

      it("Should have the Location header with length 24") {
        result.header.headers.get("Location").get.length should equal(24)
      }

      describe("When getting first user") {
        var response: Option[Future[Result]] = null
        var result: Result = null
        beforeEach {
          val uri = s"/user/$firstUserId"
          val request = FakeRequest.apply("PUT", uri)
          response = route(request)
          result = Await.result(response.get, timeout)
        }

        it("Should have a defined response") {
          response.isDefined should equal(true)
        }

        it("Should be able make the request with success") {
          result.header.status should equal(OK)
        }

        it("Json response should not contain password") {
          val json: JsValue = contentAsJson(response.get)
          val password = json.\("password")

          assert(password.getClass == (new JsUndefined("")).getClass)
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


}
