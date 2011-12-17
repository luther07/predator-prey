package predatorprey
import akka.testkit.TestActorRef
import akka.testkit._
import akka.util.duration._

class LynxTestActor extends TestKit {
val MillisTime: Long = 100

  val lynxRef = TestActorRef[Hare].start()
  val lynxActor = lynxRef.underlyingActor

  within (1 second) {
    lynxRef ! DateOfBirth(MillisTime)
    expectMsg(Time)
  }

  within (1 second) {
    lynxRef ! Alive
    expectMsg(AliveTrue)
    expectMsg(Time)
  }

  within (1 second) {
    lynxRef ! ReturnedTime(MillisTime)
    expectMsg(Time)
  }

  within (1 second) {
    lynxRef ! EatHareEnergy
    expectMsg(Time)
  }
}
