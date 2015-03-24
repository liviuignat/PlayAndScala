package common

/**
 * Created by liviu.ignat on 3/24/2015.
 */
trait LastError {
  def ok(): Boolean
}

case class NoError() extends LastError {
  def ok() = true
}

case class Error(throwable: Option[Throwable]) extends LastError {
  def ok() = false
}
