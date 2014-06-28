package samcarr.socialnet

import scala.language.implicitConversions

import SocialNet._

class SocialNetSpec extends UnitSpec {
  
  // Use an incrementing clock so we don't suffer from time granularity issues.
  implicit val incrementingClock = new IncrementingClock(0L)
  
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
    val net = emptyNet.follow(Alice, Bob)
                      .post(Alice, "A1")
                      .post(Bob, "B1")
                      .post(Alice, "A2")
    assertMessageListsEqual(net.wall(Alice), Alice -> "A2", Bob -> "B1", Alice -> "A1")
  }
  
  it should "report on who follows who" in {
    val net = emptyNet.follow(Alice, Bob)
                      .follow(Alice, Carol)
    assert(net.followedBy(Alice) == Set(Bob, Carol))
  }
  
  // Compare ignoring time.
  def assertMessageListsEqual(actual: List[Message], expected: Tuple2[User, String]*) = {
    // Compare whole lists of pairs so we get helpful failure messages out.
    val actualPairs = actual map { case Message(u, m, _) => (u, m) }
    assert(actualPairs == expected.toList)
  }
}