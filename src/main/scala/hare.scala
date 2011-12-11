package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.util.Random

case object Reproduce
case object NaturalDeath
case object PredatorDeath
case object Alive

class Hare(val id: Int) extends Actor {
   private val random = new Random()

   def receive = {
      case Reproduce => println("hare reproduced")
      case Alive => println("[h" + id + "] I'm alive")
   }
}
