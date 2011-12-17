package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.util.Random

case object ReproduceHare // purpose: send to world for hare to reproduce
case object ReproduceLynx // purpose: send to world for lynx to reproduce
case object NaturalDeath // purpose: ? 
case object EnergyDeath // Used for killing lynx when energy <= 0
case object PredatorDeath // purpose: ?
case object Alive // (debugging purpose) message world can send to lynx and hare to ask if alive
case object AliveTrue // (debugging purpose) message lynx and hare return to world to indicate alive
case object ReqDOB // purpose: send msg to world requesting what will be the actor's date of birth 
case class DateOfBirth(millisecs: Long) // purpose: message 
case class ReturnedTime(millisecs: Long) // purpose: world-time received from world after requesting time 
case class HareLocation(x: Int, y: Int) // purpose: Hare passes this message to world to report location 
case class LynxLocation(x: Int, y: Int) 

/* id is a simple identifier, which we probably won't need in the end,
   but we may want to add other arguments to this constructor */

class Hare(val id: Int) extends Actor {
   import self._
   private val random = new Random()
   private var lastReproduction : Long = 0
   private var birthday : Long = 0
   private var (xcoord:Int, ycoord:Int) = (0,0)

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
         // sequential work
         // reproduce?
         reproduce(n)
         // die of old age?
         naturaldeath(n)
         // Move
         move()
         // Send location information to world
         self.reply(HareLocation(xcoord, ycoord))
         //println("[h" + id + "] received time from world")
         self.reply(Time) // request time from world                  
      }
      case(_) => self.reply(Time)
   }

   def reproduce(n: Long) {
      if(n > (lastReproduction + WorldConfiguration.hareBirthRate)) {
         lastReproduction = lastReproduction + WorldConfiguration.hareBirthRate
         self.reply(ReproduceHare)             
      }
   }
   def naturaldeath(n: Long) {
      if((n - birthday) > WorldConfiguration.maxHareAge)
         self.reply(NaturalDeath)   
   }

   def move() {
	   xcoord = (math.random * WorldConfiguration.worldWidth).toInt
	   ycoord = (math.random * WorldConfiguration.worldHeight).toInt
           println("[h" + id + "] moved to (" + xcoord.toString() + "," + ycoord.toString() + ")")
   }
}
