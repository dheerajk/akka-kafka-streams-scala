organization in ThisBuild := "com.squareoneinsights"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lazy val `akka-kafka-streams-scala` = (project in file("."))
  .aggregate(`akka-kafka-streams-scala-api`, `akka-kafka-streams-scala-impl`, `akka-kafka-streams-scala-stream-api`, `akka-kafka-streams-scala-stream-impl`)

lazy val `akka-kafka-streams-scala-api` = (project in file("akka-kafka-streams-scala-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `akka-kafka-streams-scala-impl` = (project in file("akka-kafka-streams-scala-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`akka-kafka-streams-scala-api`)

lazy val `akka-kafka-streams-scala-stream-api` = (project in file("akka-kafka-streams-scala-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `akka-kafka-streams-scala-stream-impl` = (project in file("akka-kafka-streams-scala-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`akka-kafka-streams-scala-stream-api`, `akka-kafka-streams-scala-api`)
