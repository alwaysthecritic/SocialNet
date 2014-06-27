package samcarr.socialnet

import SocialNet._
import scala.io.StdIn
import scala.annotation.tailrec

class Cli {
  
  val Post    = """^(\S+) -> (.+)$""".r
  val Read    = """^(\S+)$""".r
  val Follows = """^(\S+) follows (\S+)$""".r
  val Wall    = """^(\S+) wall$""".r
  
  @tailrec
  final def session(implicit net: SocialNet): SocialNet = {
    val command = StdIn.readLine("social> ")
    
    // At the moment there is no way to exit other than ctrl-C.
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
    val posts = net.read(user)
    println(posts.mkString("\n"))
    net
  }
  
  def follow(user: User, userToFollow: User)(implicit net: SocialNet) = 
    net.follow(user, userToFollow)
    
  def wall(user: User)(implicit net: SocialNet) = {
    val posts = net.wall(user)
    println(posts.mkString("\n"))
    net
  }
}