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
  
  val Usage = """|Welcome to SocialNet.
                 |Post a message:       <user> -> <message>
                 |List user's messages: <user>
                 |Follow another user:  <user> follows <other>
                 |Show user's wall:     <user> wall""".stripMargin
                   
  val Post    = """^(\S+) -> (.+)$""".r
  val Read    = """^(\S+)$""".r
  val Follows = """^(\S+) follows (\S+)$""".r
  val Wall    = """^(\S+) wall$""".r
  
  def session(net:SocialNet): SocialNet = {
    println(Usage)
    readInput(net)
  }
  
  @tailrec
  private final def readInput(net: SocialNet): SocialNet = {
    implicit val currentNet = net
    val command = StdIn.readLine("social> ")
    
    val updatedNet = command match {
      case Post(name, message) => post(User(name), message)
      case Read(name) => read(User(name))
      case Follows(name, nameToFollow) => follow(User(name), User(nameToFollow))
      case Wall(name) => wall(User(name))
      case _ => println("Command not understood."); net
    }
    readInput(updatedNet)
  }
  
  private def post(user: User, message: String)(implicit net: SocialNet) =
    net.post(user, message)
  
  private def read(user: User)(implicit net: SocialNet) = {
    val messages = net.read(user)
    println(MessageFormatter.formatMessages(messages))
    net
  }
  
  private def follow(user: User, userToFollow: User)(implicit net: SocialNet) = 
    net.follow(user, userToFollow)
    
  private def wall(user: User)(implicit net: SocialNet) = {
    val messages = net.wall(user)
    println(MessageFormatter.formatMessages(messages))
    net
  }
}