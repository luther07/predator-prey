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

class HareTestActor extends WordSpec with BeforeAndAfterAll with ShouldMatchers with TestKit {

  val hareRef = TestActorRef(new Hare)
  val TimeMillis: Long = 100

  override protected def beforeAll() :scala.Unit = {
    hareRef.start()
  }

  override protected def afterAll() :scala.Unit = {
    hareRef.stop()
  }

  "A HareTestActor" should {
    "Handle and respond to these messages" in {
      within (1 second) {
        hareRef ! DateOfBirth(TimeMillis)
        expectMsg(Time)
        hareRef ! Alive
        expectMsg(AliveTrue)
        expectMsg(Time)
        hareRef ! ReturnedTime(TimeMillis)
        expectMsg(Time)
/* this tests the final case in hare's receive method to see if it catches arbitrary messages */
        hareRef ! TimeMillis
        expectMsg(Time)
      }
    }
  }
}
