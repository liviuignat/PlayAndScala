name := "scalaplaymongo"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.4"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
  "io.spray" %%  "spray-json" % "1.3.1",
  "com.google.inject" % "guice" % "3.0",
  "com.tzavellas" % "sse-guice" % "0.7.1",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23",
  "javax.inject" % "javax.inject" % "1",
  "org.mockito" % "mockito-core" % "1.10.17" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "com.novocode" % "junit-interface" % "0.9" % "test")
