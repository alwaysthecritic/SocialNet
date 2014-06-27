package samcarr.socialnet

import java.util.Date

object Duration {
  // Approximations for month and year are deemed good enough for this purpose.
  val OneSecond = 1000L
  val OneMinute = OneSecond * 60
  val OneHour = OneMinute * 60
  val OneDay = OneHour * 24
  val OneWeek = OneDay * 7
  val OneYear = OneDay * 365
  val OneMonth = OneYear / 12
  
  implicit class Ago(ms: Long) {
    def ago(): String = {    
      if (ms <= -OneSecond) "In the future"
      else if (ms < OneSecond) "Right now"
      else if (ms < OneMinute) s"${ms / OneSecond} seconds ago"
      else if (ms < OneHour) s"${ms / OneMinute} minutes ago"
      else if (ms < OneDay) s"${ms / OneHour} hours ago"
      else if (ms < OneWeek) s"${ms / OneDay} days ago"
      else if (ms < OneMonth) s"${ms / OneWeek} weeks ago"
      else if (ms < OneYear) s"${ms / OneMonth} months ago"
      else s"${ms / OneYear} years ago"
    }
  }
}
