package samcarr.socialnet

import java.util.Date
import SocialNet.Message

class MessageFormatterSpec extends UnitSpec {
  
  import MessageFormatter._
  
  val nowMs = 100000000
  val oneDayAgo = new Date(nowMs - Duration.OneDay)
  val twoDaysAgo = new Date(nowMs - (Duration.OneDay * 2))
  
  implicit val stoppedClock = new StoppedClock(nowMs)
  
  "MessageFormatter" should "format individual message" in {
    val formatted = formatMessage(Message(Bob, "This is a test", oneDayAgo))
    assert(formatted == "Bob - This is a test (1 days ago)")
  }
  
  it should "format a list of messages" in {
    val messages = List(Message(Alice, "First", oneDayAgo),
                        Message(Bob, "Second", twoDaysAgo))
    val formatted = formatMessages(messages)
    assert(formatted == "Alice - First (1 days ago)\nBob - Second (2 days ago)")
  }
}