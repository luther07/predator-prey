package predatorprey

import akka.actor.Actor
import akka.actor.Actor._
import scala.collection.{immutable, mutable}
import scala.util.Random

/* 
  Initial structure from Programmin Scala: 
  // code-examples/Concurrency/sleepingbarber/barbershop-simulator.scala
  This is just a start. We still need to architect this appliation.
*/

object PredatorPreySimulator {
  private val random = new Random()
  private val hares = new mutable.ArrayBuffer[akka.actor.ActorRef]()
  private val lynxs = new mutable.ArrayBuffer[akka.actor.ActorRef]()
  private val world = actorOf(new World)

  def generateHares {
    for (i <- 1 to 20) {
      val hare = actorOf(new Hare(i))
      hare.start()
      hares += hare
    }

    println("[!] generated " + hares.size + " hares")
  }
  def generateLynxs {
    for (i <- 1 to 20) {
      val lynx = actorOf(new Lynx(i))
      lynx.start()
      lynxs += lynx
    }

    println("[!] generated " + lynxs.size + " lynxs")
  }

  def main(args: Array[String]) {
    println("[!] starting predator-prey simulation")
    world.start()

  generateHares
  generateLynxs

  System.exit(0)
  }
}
