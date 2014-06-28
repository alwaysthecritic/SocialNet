package samcarr.socialnet

import java.util.Date

/**
 * "Even a stopped clock tells the right time twice a day." - Withnail and I
 */
class StoppedClock(var time: Long) extends Clock {
  val date = new Date(time)
  override def currentTime() = date
}