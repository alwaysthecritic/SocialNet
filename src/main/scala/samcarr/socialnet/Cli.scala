package samcarr.socialnet

import SocialNet._
import Duration.Ago
import scala.io.StdIn
import scala.annotation.tailrec
import java.util.Date

/**
 * Command line interface.
 * At the moment there is no way to exit other than ctrl-C.
 */
class Cli(implicit clock:Clock = new Clock()) {
  
  val Post    = """^(\S+) -> (.+)$""".r
  val Read    = """^(\S+)$""".r
  val Follows = """^(\S+) follows (\S+)$""".r
  val Wall    = """^(\S+) wall$""".r
  
  @tailrec
  final def session(net: SocialNet): SocialNet = {
    implicit val currentNet = net
    val command = StdIn.readLine("social> ")
    
    val updatedNet = command match {
      case Post(name, message) => post(User(name), message)
      case Read(name) => read(User(name))
      case Follows(name, nameToFollow) => follow(User(name), User(nameToFollow))
      case Wall(name) => wall(User(name))
      case _ => println("Command not understood."); net
    }
    session(updatedNet)
  }
  
  def post(user: User, message: String)(implicit net: SocialNet) =
    net.post(user, message)
  
  def read(user: User)(implicit net: SocialNet) = {
    val messages = net.read(user)
    println(formatMessages(messages))
    net
  }
  
  def follow(user: User, userToFollow: User)(implicit net: SocialNet) = 
    net.follow(user, userToFollow)
    
  def wall(user: User)(implicit net: SocialNet) = {
    val messages = net.wall(user)
    println(formatMessages(messages))
    net
  }
  
  def formatMessages(messages: List[Message]) =
    messages map (formatMessage) mkString("\n")
  
  def formatMessage(message: Message) = {
    val ago = (clock.currentTime().getTime() - message.time.getTime()).ago
    s"${message.user.name} - ${message.content} ($ago)"
  }
}