package samcarr.socialnet

import java.util.Date
import Duration._

class DurationSpec extends UnitSpec {
  
  "Ago" should "handle the future (1 second or more)" in {
    assert((-OneSecond).ago == "In the future")
    assert((-OneMinute).ago == "In the future")
  }
  
  "Ago" should "handle the present (with a 1 second margin of error either way)" in {
    assert((-OneSecond + 1).ago == "Right now")
    assert(0.ago == "Right now")
    assert((OneSecond - 1).ago == "Right now")
  }
  
  "Ago" should "handle the past" in {
    assert(OneSecond.ago == "1 seconds ago")
    assert((OneSecond * 59).ago == "59 seconds ago")
    
    assert(OneMinute.ago == "1 minutes ago")
    assert((OneMinute * 59).ago == "59 minutes ago")
    
    assert(OneHour.ago == "1 hours ago")
    assert((OneHour * 23).ago == "23 hours ago")
    
    assert(OneDay.ago == "1 days ago")
    assert((OneDay * 6).ago == "6 days ago")
    
    assert(OneWeek.ago == "1 weeks ago")
    assert((OneWeek * 3).ago == "3 weeks ago")
    
    assert(OneMonth.ago == "1 months ago")
    assert((OneMonth * 11).ago == "11 months ago")
    
    assert(OneYear.ago == "1 years ago")
    assert((10 * OneYear).ago == "10 years ago")
  }
}