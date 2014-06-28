package samcarr.socialnet

import java.util.Date

/**
 * Abstract the concept of current time, to enable predictable testing.
 */
class Clock {
  def currentTime() = new Date()
}
