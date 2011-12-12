package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.collection.{immutable, mutable}
import akka.actor.{PoisonPill}
import scala.util.Random

case object Time

class World extends Actor {
   private val random = new Random()
   private val hares = new mutable.ArrayBuffer[akka.actor.ActorRef]()
   private val lynxs = new mutable.ArrayBuffer[akka.actor.ActorRef]()

   override def preStart {
      generateHares(20)
      generateLynxs(20)   
   }      

   def receive = {
      case Time => println("The time is...")   
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

   def shutdownHare(n: Int) {
      hares(n) ! PoisonPill
   }

   def isAliveHare() {
      hares.map(_ ! Alive)
   }
}
