package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.util.Random

case object Reproduce
case object NaturalDeath //already defined in hare.scala, maybe causes error

class Lynx extends Actor {
   private val random = new Random()

   def receive = {
      
   }
}
