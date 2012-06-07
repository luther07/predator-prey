import sbt._

class PredatorPrey(info: ProjectInfo) extends DefaultProject(info) {

   override def compileOptions: Seq[CompileOption] = Deprecation :: Unchecked :: Verbose :: Nil

   val junit = "junit" % "junit" % "4.6"

   val akka = "se.scalablesolutions.akka" % "akka-actor" % "1.3-RC1"

   val junitInterface = "com.novocode" % "junit-interface" % "0.6" % "test->default"

   override def mainScalaSourcePath = "src" / "main" / "scala"

   override def mainResourcesPath = "resources"

   override def testScalaSourcePath = "test" / "main" / "scala"

   override def testResourcesPath = "test-resources"

   val scalatest = "org.scalatest" %% "scalatest" % "1.6.1" % "test" 

}
