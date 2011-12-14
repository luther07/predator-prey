package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.util.Random

case object Reproduce
case object ReproduceTrue
case object NaturalDeath
case object PredatorDeath
case object Alive
case object AliveTrue
case class ReturnedTime(millisecs: Long)

class Hare(val id: Int) extends Actor {
   import self._
   private val random = new Random()

   def receive = {
      case Alive => self.reply(AliveTrue)
   }
}
