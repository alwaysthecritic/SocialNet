package samcarr.socialnet

import org.scalatest._
import SocialNet._

abstract class UnitSpec extends FlatSpec with Matchers with
  OptionValues with Inside with Inspectors {
  
  val Bob = User("Bob")
}