package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.util.Random

class Lynx(val id: Int) extends Actor {
   import self._
   private val random = new Random()
   private var lastReproduction : Long = 0
   private var birthday : Long = 0
   var (xcoord : Int, ycoord : Int) = (0,0)

   override def preStart {
      PredatorPreySimulator.world ! ReqDOB
   }

   def receive = {
      case DateOfBirth(n) => {
         birthday = n
         self.reply(Time)
      }
      case Alive => {
         self.reply(AliveTrue)
         self.reply(Time)
      }
      case ReturnedTime(n) => {
         // other sequential work before asking for the time again
         // query, can reproduce? Implement function.
         reproduce(n)
         // query, die of old age? Implement function.
         naturaldeath(n)
         // Move. Implement function.
         move()
         //println("[l" + id + "] received time from world")
         self.reply(Time)
      }
      case (_) => self.reply(Time)
   }

   def reproduce(n: Long) {
      if(n > (lastReproduction + WorldConfiguration.lynxBirthRate)) {
         lastReproduction = lastReproduction + WorldConfiguration.lynxBirthRate
         self.reply(ReproduceLynx)
      }
   }

   def naturaldeath(n: Long) {
      if((n - birthday) > WorldConfiguration.maxLynxAge)
         self.reply(NaturalDeath)         
   }

   def move() {
      xcoord = (math.random * WorldConfiguration.worldWidth).toInt
      ycoord = (math.random * WorldConfiguration.worldHeight).toInt
      println("[l" + id + "] moved to (" + xcoord.toString() + "," + ycoord.toString() + ")")
   }
}
