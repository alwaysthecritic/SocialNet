package samcarr.socialnet

object SocialNet {
  
  case class User(name: String)
  
  type UserMessages = Map[User, List[String]]
  val EmptyUserMessages = Map[User, List[String]]()
  
  def apply(messages: UserMessages = EmptyUserMessages) = new SocialNet(messages)
}

import SocialNet._

/**
 * Immutable social network - 'update' operations return a new instance.
 * Each user's messages should be ordered with newest messages first.
 */
class SocialNet(messages: UserMessages) {
  
  def read(user: User) = messages.get(user) getOrElse List()
  
  def post(user: User, message: String) =
    SocialNet(messages.updated(user, message :: read(user)))
}