package com.squareoneinsights.akkakafkastreamsscala.impl

import com.lightbend.lagom.scaladsl.server.LocalServiceLocator
import com.lightbend.lagom.scaladsl.testkit.ServiceTest
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}
import com.squareoneinsights.akkakafkastreamsscala.api._

class AkkakafkastreamsscalaServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

  private val server = ServiceTest.startServer(
    ServiceTest.defaultSetup
      .withCassandra()
  ) { ctx =>
    new AkkakafkastreamsscalaApplication(ctx) with LocalServiceLocator
  }

  val client = server.serviceClient.implement[AkkakafkastreamsscalaService]

  override protected def afterAll() = server.stop()

  "akka-kafka-streams-scala service" should {

    "say hello" in {
      client.hello("Alice").invoke().map { answer =>
        answer should ===("Hello, Alice!")
      }
    }

    "allow responding with a custom message" in {
      for {
        _ <- client.useGreeting("Bob").invoke(GreetingMessage("Hi"))
        answer <- client.hello("Bob").invoke()
      } yield {
        answer should ===("Hi, Bob!")
      }
    }
  }
}
