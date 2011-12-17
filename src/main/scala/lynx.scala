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
   var energy : Int = WorldConfiguration.defaultEnergy
   var age : Int = 0
   

   private var (lynxX:Int, lynxY:Int) = (0,0)
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
         move()
         useEnergy()
         tryToEatHare(n)
         changeAge()
         checkForDeath()
         reproduce(n)
         // query, die of old age? Implement function.
         naturaldeath(n)
         // Move. Implement function.
         self.reply(LynxLocation(lynxX, lynxY))
         //println("[l" + id + "] received time from world")
         self.reply(Time)
      }
      case (_) => self.reply(Time)
   }
   
   def useEnergy() {
       energy = energy - 1
   }
   
   def tryToEatHare(n : Long) {
       
   }
   
   def changeAge(){
       age = age + 1
   }
   
   
   def checkForDeath(){
       if(energy == 0){
           self.reply(EnergyDeath)
       }
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
      lynxX = (math.random * WorldConfiguration.worldWidth).toInt
      lynxY = (math.random * WorldConfiguration.worldHeight).toInt
      println("[l" + id + "] moved to (" + xcoord.toString() + "," + ycoord.toString() + ")")
   }
}
