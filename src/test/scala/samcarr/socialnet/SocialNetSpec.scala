package samcarr.socialnet

import SocialNet._
import org.scalatest._
import java.util.Date
import scala.language.implicitConversions

class SocialNetSpec extends UnitSpec {
  
  val emptyNet = SocialNet()
  
  "SocialNet" should "list zero messages for a user that has never posted" in {
    assert(emptyNet.read(Bob) === List())
  }
  
  it should "allow a user to post a message and read it back" in {
    val net = emptyNet.post(Bob, "A message.")
    assertMessageListsEqual(net.read(Bob), Bob -> "A message.")
  }
  
  it should "list posted messages for a user, newest first" in {
    val net = emptyNet.post(Bob, "Message 1")
                      .post(Bob, "Message 2")
    assertMessageListsEqual(net.read(Bob), Bob -> "Message 2", Bob ->"Message 1")
  }
  
  it should "list followed users' messages on a user's wall, interleaved in correct order" in {
    val emptyNet = timeIncrementingNet()
    val net = emptyNet.follow(Alice, Bob)
                      .post(Alice, "A1")
                      .post(Bob, "B1")
                      .post(Alice, "A2")
    assertMessageListsEqual(net.wall(Alice), Alice -> "A2", Bob -> "B1", Alice -> "A1")
  }
  
  // Compare ignoring time.
  def assertMessageListsEqual(actual: List[Message], expected: Tuple2[User, String]*) = {
    // Compare lists of pairs so we get helpful failure messages out.
    val actualPairs = actual map { case Message(u, m, _) => (u, m) }
    assert(actualPairs == expected.toList)
  }
  
  // The system clock does not have the granularity to result in predictable
  // ordering of messages (we end up with same time on all of them) so we
  // provide a predictable, incrementing clock that can be used instead.
  class IncrementingTimeSource(var time: Long) extends TimeSource {
    override def currentTime() = {
      val current = new Date(time)
      time += 1
      current
    }
  }
  
  def timeIncrementingNet() = {
    implicit val fakeTimeSource = new IncrementingTimeSource(0L)
    SocialNet()
  }
}