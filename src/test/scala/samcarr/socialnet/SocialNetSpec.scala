package samcarr.socialnet

import SocialNet._
import org.scalatest._
import java.util.Date
import scala.language.implicitConversions

class SocialNetSpec extends UnitSpec {
  
  val emptyNet = SocialNet()
  
  // Utility methods to allow us to use strings as messages in our tests.
  def messages(messages: String*) = messages.toList map { stringToMessage(_) }
  implicit def stringToMessage(message: String) = Message(message, new Date)
  
  "SocialNet" should "list zero messages for a user that has never posted" in {
    assert(emptyNet.read(Bob) === List())
  }
  
  it should "allow a user to post a message and read it back" in {
    val net = emptyNet.post(Bob, "A message.")
    assertMessageListsEqual(net.read(Bob), messages("A message."))
  }
  
  it should "list posted messages for a user, newest first" in {
    val net = emptyNet.post(Bob, "Message 1")
                      .post(Bob, "Message 2")
    assertMessageListsEqual(net.read(Bob), messages("Message 2", "Message 1"))
  }
  
  it should "list followed users' messages on a user's wall, interleaved in correct order" in {
    val net = emptyNet.follow(Alice, Bob)
                      .post(Alice, "A1")
                      .post(Bob, "B1")
                      .post(Alice, "A2")
    assertMessageListsEqual(net.wall(Alice), messages("A2", "B1", "A1"))
  }
  
  def assertMessageListsEqual(actual: List[Message], expected: List[Message]) =
    (actual.length === expected.length) &&
    (actual.zip(expected) forall { case (Message(m1, _), Message(m2, _)) => m1 === m2 })
    
}