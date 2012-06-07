name := "predator-prey"

version := "1.0"

scalaVersion := "2.9.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "se.scalablesolutions.akka" % "akka-actor" % "1.3-RC1"

libraryDependencies += "se.scalablesolutions.akka" % "akka-testkit" % "1.3-RC1"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.6.1" % "test"

libraryDependencies += "junit" % "junit" % "4.6"

libraryDependencies += "com.novocode" % "junit-interface" % "0.6" % "test->default"
