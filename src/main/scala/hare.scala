package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.util.Random

case object ReproduceTrue // purpose: send to world in order to reproduce
case object NaturalDeath // purpose: ? 
case object PredatorDeath // purpose: ?
case object Alive // (debugging purpose) message world can send to lynx and hare to ask if alive
case object AliveTrue // (debugging purpose) message lynx and hare return to world to indicate alive
case class ReturnedTime(millisecs: Long) // purpose: world-time received from world after requesting time 

/* id is a simple identifier, which we probably won't need in the end,
   but we may want to add other arguments to this constructor */

class Hare(val id: Int) extends Actor {
   import self._
   private val random = new Random()
   private var lastReproduction = 0
   private val reproductionRateMillis: Long = 30000

   def receive = {
      case Alive => self.reply(AliveTrue)
      case ReturnedTime(n) => {
         // other sequential work before asking for the time again
         // query, can reproduce? Implement function.
         // query, die of old age? Implement function.
         // Move. Implement function.
         self.reply(Time) // request time from world                  
      }
   }
}
