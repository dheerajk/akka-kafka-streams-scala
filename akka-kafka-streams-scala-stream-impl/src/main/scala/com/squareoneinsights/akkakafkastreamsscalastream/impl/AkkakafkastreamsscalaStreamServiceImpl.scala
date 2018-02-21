package com.squareoneinsights.akkakafkastreamsscalastream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.squareoneinsights.akkakafkastreamsscalastream.api.AkkakafkastreamsscalaStreamService
import com.squareoneinsights.akkakafkastreamsscala.api.AkkakafkastreamsscalaService

import scala.concurrent.Future

/**
  * Implementation of the AkkakafkastreamsscalaStreamService.
  */
class AkkakafkastreamsscalaStreamServiceImpl(akkakafkastreamsscalaService: AkkakafkastreamsscalaService) extends AkkakafkastreamsscalaStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(akkakafkastreamsscalaService.hello(_).invoke()))
  }
}
