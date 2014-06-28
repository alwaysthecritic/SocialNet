package samcarr.socialnet

import java.util.Date

/**
 * The system clock does not have the granularity to result in predictable
 * ordering of messages in tests (we may end up with the same time on
 * all of them) so we provide an alternative predictable clock that always
 * moves on my one millisecond each time it's asked.
 */
class IncrementingClock(var time: Long) extends Clock {
  override def currentTime() = {
    val current = new Date(time)
    time += 1
    current
  }
}