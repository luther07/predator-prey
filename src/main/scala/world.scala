package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.collection.{immutable, mutable}
import akka.actor.{PoisonPill}
import scala.util.Random

case object Time // used by lynx and hare actors to request time from world
case class DeleteHareLocation(x: Int, y: Int)
case class AddHareLocation(x: Int, y: Int)

class World extends Actor {
   import self._
   private val random = new Random()
   private val hares = new mutable.ArrayBuffer[akka.actor.ActorRef]()
   private val lynxs = new mutable.ArrayBuffer[akka.actor.ActorRef]()
   private var beginTime: Long = 0
   private val hareLocations = new mutable.ArrayBuffer[Option[akka.actor.ActorRef]]()

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
      case DeleteHareLocation(x,y) => deletehare(x,y) 
      case AddHareLocation(x,y) => addhare(x,y)
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

   def deletehare(x: Int, y: Int) {
      val xyMap = y * WorldConfiguration.worldWidth + x
      val mySender = None
      hareLocations.insert(xyMap, mySender)
   }

   // can overwrite another hare, then the later hare can get eaten, because the index has its reference
   def addhare(x: Int, y: Int) {
      val xyMap = y * WorldConfiguration.worldWidth + x
      val mySender = self.getSender()
      hareLocations.insert(xyMap, mySender)
   }
}
