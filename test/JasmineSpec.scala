/**
 * Created by liviu.ignat on 3/23/2015.
 */
import org.scalatest.{Tag, FunSpec}
import scala.collection.mutable

trait JasmineSpec extends FunSpec {

  private val itWord = new ItWord
  private val setup: mutable.Stack[List[() => Unit]] = mutable.Stack()
  private val tearDown: mutable.Stack[List[() => Unit]] = mutable.Stack()

  protected def beforeEach(code: => Unit) {
    val list = (() => code) :: setup.pop()
    setup.push(list)
  }

  protected def afterEach(code: => Unit) {
    val list = (() => code) :: tearDown.pop()
    tearDown.push(list)
  }

  protected override def describe(description: String)(fun: => Unit) {
    setup.push(List())
    tearDown.push(List())
    super.describe(description)(fun)
    tearDown.pop()
    setup.pop()
  }

  protected override val it = observe

  private lazy val observe: ItWord = new ItWord {
    override def apply(specText: String, testTags: Tag*)(testFun: => Unit) {
      val before = setup.reverse.flatMap(_.reverse)
      val after = tearDown.flatMap(_.reverse)

      itWord(specText, testTags: _*) {
        before.foreach(_())
        testFun
        after.foreach(_())
      }
    }
  }
}
