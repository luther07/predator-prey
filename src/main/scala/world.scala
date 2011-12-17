package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.collection.{immutable, mutable}
import akka.actor.{PoisonPill}
import scala.util.Random

case object Time // used by lynx and hare actors to request time from world

class World extends Actor {
   import self._
   private val random = new Random()
   private val hares = new mutable.ArrayBuffer[akka.actor.ActorRef]()
   private val lynxs = new mutable.ArrayBuffer[akka.actor.ActorRef]()
   private var beginTime: Long = 0

/* We need a data structure for storing location of hares.
   Maybe a hashmap with a key that is a tuple like
   (x,y). This is a major TODO
   ******
   Then hares will constantly be reporting their location, via a 
   "case class HareLocation(x,y).
    */

   override def preStart {
      generateHares(WorldConfiguration.initialHares)
      generateLynxs(WorldConfiguration.initialLynx)
      
      beginTime = System.currentTimeMillis()
   }      

   def receive = {
      //Here we want to send a message back to the caller, with the value from evaluating getTime()
      case Time => self.reply(ReturnedTime(getTime()))
      case ReqDOB => self.reply(DateOfBirth(getTime()))
      case ReproduceHare => hareReproduce()
      case ReproduceLynx => lynxReproduce()
      case NaturalDeath => self.reply(PoisonPill)
      case EnergyDeath => self.reply(PoisonPill)
      case HareLocation(x,y) => println("update location data") //TODO implement
      case _ => println("[w] world: no action")
   }

   def generateHares(n: Int) {
      for(i <- 1 to n) {
         val hare = actorOf(new Hare(i))
         hare.start()
         hares += hare
      }
      println("[!!] generated " + hares.size + " hares")
   }

   def generateLynxs(n: Int) {
      for(i <- 1 to n) {
         val lynx = actorOf(new Lynx(i))
         lynx.start()
         lynxs += lynx
      }
      println("[!!] generated " + hares.size + " lynxes")
   }

   def getTime(): Long =  {
      val relativeTime = (System.currentTimeMillis() - beginTime)
      relativeTime
   }

   def shutdownLynx(n: Int) {
      lynxs(n) ! PoisonPill
   }

   def lynxReproduce() {
      val lynx = actorOf(new Lynx(0))
      lynx.start()
      lynxs += lynx
      println("[w] World reports that a lynx reproduced")
   }

   def hareReproduce() {
      val hare = actorOf(new Hare(0))
      hare.start()
      hares += hare
      println("[w] World reports that a hare reproduced")
   }

   def shutdownHare(n: Int) {
      hares(n) ! PoisonPill
   }

   def isAliveHare() {
      hares.map(_ ! Alive)
   }
}
