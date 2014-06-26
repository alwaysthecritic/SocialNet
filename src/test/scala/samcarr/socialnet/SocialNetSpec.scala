package samcarr.socialnet

import org.scalatest._

class SocialNetSpec extends UnitSpec {
  
  "SocialNet" should "show zero messages for a user that has never posted" in {
    val socialNet = SocialNet()
    assert(socialNet.read(Bob) === List())
  }
  
  it should "allow a user to post a message and read it back" in {
    val socialNet = SocialNet()
    val updatedNet = socialNet.post(Bob, "A message.")
    assert(updatedNet.read(Bob) == List("A message."))
  }
}