package predatorprey

import akka.actor.Actor
import akka.actor.Actor._
import akka.actor.{PoisonPill}
import scala.util.Random

/* 
  Initial structure from Programmin Scala: 
  // code-examples/Concurrency/sleepingbarber/barbershop-simulator.scala
  This is just a start. We still need to architect this appliation.
*/

object PredatorPreySimulator {
  private val random = new Random()
  //maybe pass in some arguments to World actor?
  val world = actorOf(new World)

  def main(args: Array[String]) {
    println("[!] starting predator-prey simulation")
    world.start()

  System.exit(0)
  }
}
