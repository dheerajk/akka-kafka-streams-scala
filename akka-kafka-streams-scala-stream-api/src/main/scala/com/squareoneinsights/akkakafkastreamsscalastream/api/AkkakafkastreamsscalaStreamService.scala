package com.squareoneinsights.akkakafkastreamsscalastream.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

/**
  * The akka-kafka-streams-scala stream interface.
  *
  * This describes everything that Lagom needs to know about how to serve and
  * consume the AkkakafkastreamsscalaStream service.
  */
trait AkkakafkastreamsscalaStreamService extends Service {

  def stream: ServiceCall[Source[String, NotUsed], Source[String, NotUsed]]

  override final def descriptor = {
    import Service._

    named("akka-kafka-streams-scala-stream")
      .withCalls(
        namedCall("stream", stream)
      ).withAutoAcl(true)
  }
}

