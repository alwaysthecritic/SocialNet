package samcarr.socialnet

import java.util.Date

object SocialNet {
  
  case class User(name: String)
  case class Message(user: User, content: String, time: Date)
  
  type UserMessages = Map[User, List[Message]]
  val EmptyUserMessages = Map[User, List[Message]]()
  
  type UserFollows = Map[User, Set[User]]
  val EmptyFollows = Map[User, Set[User]]()
  
  // Abstract the concept of current time, to enable predictable testing.
  class Clock {
    def currentTime() = new Date()
  }
  
  def apply(messages: UserMessages = EmptyUserMessages, follows: UserFollows = EmptyFollows)
           (implicit clock:Clock = new Clock()) =
    new SocialNet(messages, follows)
}

import SocialNet._

/**
 * Immutable social network - update operations return a new instance.
 * Each user's messages should be ordered with newest messages first.
 */
class SocialNet(messages: UserMessages, follows: UserFollows)(implicit clock:Clock) {
  
  def read(user: User) = messages.getOrElse(user, List())
  
  def post(user: User, message: String) = {
    val newMessage = Message(user, message, clock.currentTime())
    SocialNet(messages.updated(user, newMessage :: read(user)), follows)
  }
    
  def follow(user: User, userToFollow: User) =
    SocialNet(messages, follows.updated(user, followedBy(user) + userToFollow))
  
  def followedBy(user: User) = follows.getOrElse(user, Set())
  
  def wall(user: User) = {
    val users = followedBy(user) + user
    val allMessages = users.toList flatMap (read(_))
    val sortedMessages = allMessages sortWith ((a, b) => a.time.after(b.time)) 
    sortedMessages
  }
}