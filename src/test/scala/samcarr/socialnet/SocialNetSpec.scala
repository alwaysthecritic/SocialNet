package samcarr.socialnet

import org.scalatest._

class SocialNetSpec extends UnitSpec {
  
  val emptyNet = SocialNet()
  
  "SocialNet" should "list zero messages for a user that has never posted" in {
    assert(emptyNet.read(Bob) === List())
  }
  
  it should "allow a user to post a message and read it back" in {
    val updatedNet = emptyNet.post(Bob, "A message.")
    assert(updatedNet.read(Bob) == List("A message."))
  }
  
  it should "list posted messages for a user, newest first" in {
    val updatedNet = emptyNet.post(Bob, "Message 1").post(Bob, "Message 2")
    assert(updatedNet.read(Bob) == List("Message 2", "Message 1"))
  }
}