package business.services

/**
 * Created by liviu.ignat on 3/24/2015.
 */

trait IRandomStringService {
  def randomAlphaNumeric(length: Int): String
  def randomAlpha(length: Int): String
}

class RandomStringService extends IRandomStringService {
  override def randomAlphaNumeric(length: Int): String = {
    val chars = ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')
    randomStringFromCharList(length, chars)
  }

  override def randomAlpha(length: Int): String = {
    val chars = ('a' to 'z') ++ ('A' to 'Z')
    randomStringFromCharList(length, chars)
  }

  def randomStringFromCharList(length: Int, chars: Seq[Char]): String = {
    val sb = new StringBuilder
    for (i <- 1 to length) {
      val randomNum = util.Random.nextInt(chars.length)
      sb.append(chars(randomNum))
    }
    sb.toString
  }
}

