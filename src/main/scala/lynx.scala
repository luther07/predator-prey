package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.util.Random

class Lynx(val id: Int) extends Actor {
   import self._
   private val random = new Random()
   private var lastReproduction : Long = 0

   override def preStart {
      PredatorPreySimulator.world ! ReqDOB
   }

   def receive = {
      case DateOfBirth(n) => {
         val birthday = n
         self.reply(Time)
      }
      case Alive => {
         self.reply(AliveTrue)
         self.reply(Time)
      }
      case ReturnedTime(n) => {
         // other sequential work before asking for the time again
         // query, can reproduce? Implement function.
         // query, die of old age? Implement function.
         // Move. Implement function.
         //println("[l" + id + "] received time from world")
         reproduce(n)
         self.reply(Time)
      }
   }

   def reproduce(n: Long) {
      if(n > (lastReproduction + WorldConfiguration.lynxBirthRate)) {
         lastReproduction = lastReproduction + WorldConfiguration.lynxBirthRate
         self.reply(ReproduceLynx)
      }
   }
}
