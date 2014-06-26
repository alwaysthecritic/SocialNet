package samcarr.socialnet

import org.scalatest._

import SocialNet.User

abstract class UnitSpec extends FlatSpec with Matchers with
  OptionValues with Inside with Inspectors {
  
  val Alice = User("Alice")
  val Bob = User("Bob")
}