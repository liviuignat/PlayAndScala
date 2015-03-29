package business.services

import javax.inject.Singleton

/**
 * Created by liviuignat on 23/03/15.
 */

trait IStringEncriptionService {
  def encryptMd5(value: String): String
}

@Singleton
class StringEncriptionService extends IStringEncriptionService {
  override def encryptMd5(value: String): String = {
    val m = java.security.MessageDigest.getInstance("MD5")
    val b = value.getBytes("UTF-8")
    m.update(b, 0, b.length)
    m.digest().map(0xFF & _).map("%02x".format(_)).mkString
  }
}
