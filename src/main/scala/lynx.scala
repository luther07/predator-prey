package predatorprey
import akka.actor.Actor
import akka.actor.Actor._
import scala.util.Random

//case object Reproduce already defined in hare.scala, will cause error
//case object NaturalDeath already defined in hare.scala, will causes error

class Lynx(val id: Int) extends Actor {
   private val random = new Random()

   def receive = {
      case Reproduce => println("lynx reproduced")
      case Alive => println("[" + id + "] I'm alive")
   }
}
