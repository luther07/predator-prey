package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.util.Random

case object Reproduce
case object NaturalDeath
case object PredatorDeath

class Hare extends Actor {
   private val random = new Random()

   def receive = {
      
   }
