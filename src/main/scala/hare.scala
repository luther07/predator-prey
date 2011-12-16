package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.util.Random

case object ReproduceHare // purpose: send to world for hare to reproduce
case object ReproduceLynx // purpose: send to world for lynx to reproduce
case object NaturalDeath // purpose: ? 
case object PredatorDeath // purpose: ?
case object Alive // (debugging purpose) message world can send to lynx and hare to ask if alive
case object AliveTrue // (debugging purpose) message lynx and hare return to world to indicate alive
case object ReqDOB // purpose: send msg to world requesting what will be the actor's date of birth 
case class DateOfBirth(millisecs: Long) // purpose: message 
case class ReturnedTime(millisecs: Long) // purpose: world-time received from world after requesting time 
case class HareLocation(x: Int, y: Int) // purpose: Hare passes this message to world to report location 

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
         // other sequential work before asking for the time again
         // query, can reproduce? Implement function below.
         reproduce(n)
         // query, die of old age? Implement function below.
         naturaldeath(n)
         // Move. Implement function below.
         move()
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
