import org.scalatest.FunSpec


/**
 * Created by liviuignat on 20/03/15.
 */
class DefaultSpec extends FunSpec {
  describe("When a value is given") {
    val value = true
    val expectedValue = false

    it("Should equal the expected value") {
      assert(expectedValue == value)
    }
  }
}
