package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.util.Random

case object Time

class World extends Actor {
   private val random = new Random()

   def receive = {
      case Time => println("The time is...")   
   }
}
