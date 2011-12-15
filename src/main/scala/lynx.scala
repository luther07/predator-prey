package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.util.Random

class Lynx(val id: Int) extends Actor {
   import self._
   private val random = new Random()

   def receive = {
      case Alive => self.reply(AliveTrue)
      case ReturnedTime(n) => {
         // other sequential work before asking for the time again
         // query, can reproduce? Implement function.
         // query, die of old age? Implement function.
         // Move. Implement function.
         self.reply(Time)
      }
   }
}
