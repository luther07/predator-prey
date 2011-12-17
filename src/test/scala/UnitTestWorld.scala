package predatorprey
import org.scalatest.matchers.{ShouldMatchers, MustMatchers}
import org.scalatest.{WordSpec, BeforeAndAfterAll}
import akka.actor.Actor._
import akka.testkit.TestKit
import java.util.concurrent.TimeUnit
import akka.testkit.TestActorRef
import akka.actor.{ActorRef, Actor}
import akka.testkit._
import akka.util.duration._
import util.Random
import akka.actor.{PoisonPill}

class WorldTestActor extends WordSpec with BeforeAndAfterAll with ShouldMatchers with TestKit {

  val worldRef = TestActorRef(new World)
  val TimeMillis: Long = 100

  override protected def beforeAll(): scala.Unit = {
    worldRef.start()
  }

  override protected def afterAll(): scala.Unit = {
    worldRef.stop()
  }

  "A WorldTestActor" should {
    "Handle and respond to these messages" in {

/* we don't know what the value wrapped in ReturnedTime will be */
      within (1 seconds) {
        worldRef ! Time
        expectMsg(ReturnedTime(TimeMillis))
/* we don't know what the value wrapped in DateOfBirth will be */
        worldRef ! ReqDOB
        expectMsg(DateOfBirth(TimeMillis))
/* as stand-alone test, world will not send any messages in response, because Reproduce increment will require passage of time */
        worldRef ! ReproduceHare
        expectMsg()
/* as stand-alone test, world will not send any message in response, because Reproduce increment will require passage of time */
        worldRef ! ReproduceLynx
        expectMsg()
        worldRef ! NaturalDeath
        expectMsg(PoisonPill)
/* null, never responds */
        worldRef ! DeleteHareLocation(0,0)
        expectMsg()
/* null, never responds */
        worldRef ! AddHareLocation(0,0)
        expectMsg()
        worldRef ! EnergyDeath
        expectMsg(PoisonPill)
/* null, to receive a return message would require the existence of hares in the world, which do not exist in a simple atomic test */ 
        worldRef ! LynxLocation(0,0)
        expectMsg()
/* this tests the final case in world's receive method to see if it handles arbitrary messages */
        worldRef ! TimeMillis
        expectMsg(Time)
      }
    }
  }
}
