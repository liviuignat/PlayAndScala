package test.controllers

import java.util.concurrent.TimeUnit

import controllers.requests.user._
import controllers.requests.JsonFormats._
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

      describe("When wanting to insert a second user") {
        var response: Option[Future[Result]] = null
        var result: Result = null
        beforeEach {
          val request = FakeRequest.apply("POST", "/api/auth/create")
            .withJsonBody(Json.obj(
            "email" -> "liviu.test@ignat.email",
            "password" -> "test123",
            "firstName" -> "Liviu Test",
            "lastName" -> "Ignat"))
          response = route(request)

          result = Await.result(response.get, timeout)
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

        describe("When query user") {

          describe("When query user with 'liviu'") {
            var response: Option[Future[Result]] = null
            var result: Result = null
            beforeEach {
              val uri = s"/api/user?q=liviu"
              val request = FakeRequest.apply("GET", uri)
              response = route(request)
              result = Await.result(response.get, timeout)
            }

            it("Should make the request with success") {
              response.isDefined should equal(true)
              result.header.status should equal(OK)
            }

            it("Should have two results") {
              val json: JsArray = contentAsJson(response.get).as[JsArray]
              json.value.size should equal(2)
            }

            it("Should have the two users in the results") {
              val users: List[GetUserResponse] = contentAsJson(response.get).as[List[GetUserResponse]]
              users(0).email should equal("liviu@ignat.email")
              users(1).email should equal("liviu.test@ignat.email")
            }
          }

          describe("When query user with 'test'") {
            var response: Option[Future[Result]] = null
            var result: Result = null
            beforeEach {
              val uri = s"/api/user?q=test"
              val request = FakeRequest.apply("GET", uri)
              response = route(request)
              result = Await.result(response.get, timeout)
            }

            it("Should make the request with success") {
              response.isDefined should equal(true)
              result.header.status should equal(OK)
            }

            it("Should have one result") {
              val json: JsArray = contentAsJson(response.get).as[JsArray]
              json.value.size should equal(1)
            }

            it("Should have the second user in the results") {
              val users: List[GetUserResponse] = contentAsJson(response.get).as[List[GetUserResponse]]
              users(0).email should equal("liviu.test@ignat.email")
            }
          }

          describe("When query is not present") {
            var response: Option[Future[Result]] = null
            var result: Result = null
            beforeEach {
              val uri = s"/api/user"
              val request = FakeRequest.apply("GET", uri)
              response = route(request)
              result = Await.result(response.get, timeout)
            }

            it("Should make the request with success") {
              response.isDefined should equal(true)
              result.header.status should equal(OK)
            }

            it("Should have two results") {
              val json: JsArray = contentAsJson(response.get).as[JsArray]
              json.value.size should equal(2)
            }
          }
        }

        describe("When update user") {
          var response: Option[Future[Result]] = null
          var result: Result = null

          beforeEach {
            val request = FakeRequest.apply("PUT", s"/api/user/$firstUserId")
              .withJsonBody(Json.obj(
              "firstName" -> "Liviu Updated",
              "lastName" -> "Ignat Updated"))
            response = route(request)
            result = Await.result(response.get, timeout)
          }

          it("Should work with success") {
            response.isDefined should equal(true)
            result.header.status should equal(OK)
          }

          describe("When getting the user") {
            var response: Option[Future[Result]] = null
            var result: Result = null
            beforeEach {
              val uri = s"/api/user/$firstUserId"
              val request = FakeRequest.apply("GET", uri)
              response = route(request)
              result = Await.result(response.get, timeout)
            }

            it("Should have the updated first name") {
              val json: JsValue = contentAsJson(response.get)
              val firstName: String = json.\("firstName").as[String]
              firstName should equal("Liviu Updated")
            }

            it("Should have the updated last name") {
              val json: JsValue = contentAsJson(response.get)
              val firstName: String = json.\("lastName").as[String]
              firstName should equal("Ignat Updated")
            }
          }
        }

        describe("When delete user") {
          var response: Option[Future[Result]] = null
          var result: Result = null

          beforeEach {
            val request = FakeRequest.apply("DELETE", s"/api/user/$firstUserId")
            response = route(request)
            result = Await.result(response.get, timeout)
          }

          it("Should work with success") {
            response.isDefined should equal(true)
            result.header.status should equal(OK)
          }

          describe("When getting the user") {
            var response: Option[Future[Result]] = null
            var result: Result = null
            beforeEach {
              val uri = s"/api/user/$firstUserId"
              val request = FakeRequest.apply("GET", uri)
              response = route(request)
              result = Await.result(response.get, timeout)
            }

            it("Should not find that user anymore") {
              response.isDefined should equal(true)
              result.header.status should equal(NOT_FOUND)
            }
          }
        }
      }
    }
  }


}
