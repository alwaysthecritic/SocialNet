name := "SocialNet"

version := "1.0"

scalaVersion := "2.11.1"

scalacOptions := Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

// set the main class for the main 'run' task
// change Compile to Test to set it for 'test:run'
mainClass in (Compile, run) := Some("samcarr.socialnet.Main")

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test"