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

   def hareReproduce() {
      val hare = actorOf(new Hare(0)
      hare.start()
      hares += hare
   }

   def shutdownHare(n: Int) {
      hares(n) ! PoisonPill
   }

   def isAliveHare() {
      hares.map(_ ! Alive)
   }
}
