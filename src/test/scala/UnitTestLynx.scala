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

class LynxTestActor extends WordSpec with BeforeAndAfterAll with ShouldMatchers with TestKit {

  val lynxRef = TestActorRef(new Lynx)
  val MillisTime: Long = 100

  override protected def beforeAll(): scala.Unit = {
    lynxRef.start()
  }

  override protected def afterAll(): scala.Unit = {
    lynxRef.stop
  }

  "A LynxTestActor" should {
    "Handle and respond to these messages" in { 

      within (1 second) {
        lynxRef ! DateOfBirth(MillisTime)
        expectMsg(Time)
        lynxRef ! Alive
        expectMsg(AliveTrue)
        expectMsg(Time)
        lynxRef ! ReturnedTime(MillisTime)
        expectMsg(Time)
        lynxRef ! EatHareEnergy
        expectMsg(Time)
        lynxRef ! MillisTime
        expectMsg(Time)
      }
    }
  }
}
