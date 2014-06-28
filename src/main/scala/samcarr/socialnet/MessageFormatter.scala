package samcarr.socialnet

import SocialNet._
import Duration.Ago

object MessageFormatter {
  
  def formatMessages(messages: List[Message])(implicit clock:Clock) =
    messages map (formatMessage) mkString("\n")
  
  def formatMessage(message: Message)(implicit clock:Clock) = {
    val ago = (clock.currentTime().getTime() - message.time.getTime()).ago
    s"${message.user.name} - ${message.content} ($ago)"
  }
}