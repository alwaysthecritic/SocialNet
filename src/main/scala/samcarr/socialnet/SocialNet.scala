package samcarr.socialnet

import java.util.Date

object SocialNet {
  
  case class User(name: String)
  
  case class Message(content: String, time: Date) {
    // Ignore date in equality.
    override def equals(other: Any) = true
  }
  
  type UserMessages = Map[User, List[Message]]
  val EmptyUserMessages = Map[User, List[Message]]()
  
  type UserFollows = Map[User, Set[User]]
  val EmptyFollows = Map[User, Set[User]]()
  
  def apply(messages: UserMessages = EmptyUserMessages, follows: UserFollows = EmptyFollows) =
    new SocialNet(messages, follows)
}

import SocialNet._

/**
 * Immutable social network - 'update' operations return a new instance.
 * Each user's messages should be ordered with newest messages first.
 */
class SocialNet(messages: UserMessages, follows: UserFollows) {
  
  def read(user: User) = messages.getOrElse(user, List())
  
  def post(user: User, message: String) =
    SocialNet(messages.updated(user, Message(message, new Date()) :: read(user)), follows)
    
  def follow(user: User, userToFollow: User) =
    SocialNet(messages, follows.updated(user, followedBy(user) + userToFollow))
  
  def followedBy(user: User) = follows.getOrElse(user, Set())
  
  def wall(user: User) = {
    val users = followedBy(user) + user
    val allMessages = users.toList flatMap (read(_))
    val sortedMessages = allMessages sortWith ((a, b) => a.time.before(b.time))
    sortedMessages
  }
}