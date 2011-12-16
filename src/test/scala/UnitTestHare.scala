package predatorprey
import akka.testkit.TestActorRef
import akka.testkit._
import akka.util.duration._

class HareTestActor extends TestKit {

  val hareRef = TestActorRef[Hare].start()
  val hareActor = hareRef.underlyingActor

  within (1 second) {
    hareRef ! Alive
    expectMsg(AliveTrue)
    expectMsg(Time)
  }
}
