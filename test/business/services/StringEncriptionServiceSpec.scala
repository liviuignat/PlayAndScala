package test.business.services

import business.services.StringEncriptionService
import org.scalatest._
import tests._

/**
 * Created by liviuignat on 23/03/15.
 */
class StringEncriptionServiceSpec extends JasmineSpec with BeforeAndAfter with BeforeAndAfterAll with ShouldMatchers {
  describe("When encrypting to md5 'testmewithmd5'") {
    val value = "testmewithmd5"
    val expected = "b4280d7ed23d7046514dab54b80efafe"
    var md5Value = ""

    beforeEach {
      md5Value = new StringEncriptionService encryptMd5(value)
    }

    it("Should return 'b4280d7ed23d7046514dab54b80efafe'") {
      md5Value should equal(expected)
    }
  }
}
