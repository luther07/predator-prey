package predatorprey
import akka.testkit.TestActorRef
import akka.testkit._
import akka.util.duration._
import akka.actor.{PoisonPill}

class WorldTestActor extends TestKit {

  val worldRef = TestActorRef[World].start()
  val worldActor = worldRef.underlyingActor
  val TimeMillis: Long = 100

/* we don't know what the value wrapped in ReturnedTime will be */
  within (1 seconds) {
    worldRef ! Time
    expectMsg(ReturnedTime(TimeMillis))
  }

/* we don't know what the value wrapped in DateOfBirth will be */
  within (1 seconds) {
    worldRef ! ReqDOB
    expectMsg(DateOfBirth(TimeMillis))
  }

/* as stand-alone test, world will not send any messages in response, because Reproduce increment will require passage of time */
  within (1 seconds) {
    worldRef ! ReproduceHare
    expectMsg()
  }

/* as stand-alone test, world will not send any message in response, because Reproduce increment will require passage of time */
  within (1 seconds) {
    worldRef ! ReproduceLynx
    expectMsg()
  }

  within (1 seconds) {
    worldRef ! NaturalDeath
    expectMsg(PoisonPill)
  }

/* null, never responds */
  within (1 seconds) {
    worldRef ! DeleteHareLocation(0,0)
    expectMsg()
  }

/* null, never responds */
  within (1 seconds) {
    worldRef ! AddHareLocation(0,0)
    expectMsg()
  }

  within (1 seconds) {
    worldRef ! EnergyDeath
    expectMsg(PoisonPill)
  }

/* null, to receive a return message would require the existence of hares in the world, which do not exist in a simple atomic test */ 
  within (1 seconds) {
    worldRef ! LynxLocation(0,0)
    expectMsg()
  }

/* this tests the final case in world's receive method to see if it handles arbitrary messages */
  within (1 seconds) {
    worldRef ! TimeMillis
    expectMsg(Time)
  }

}
