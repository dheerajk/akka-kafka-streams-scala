package com.squareoneinsights.akkakafkastreamsscalastream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.squareoneinsights.akkakafkastreamsscalastream.api.AkkakafkastreamsscalaStreamService
import com.squareoneinsights.akkakafkastreamsscala.api.AkkakafkastreamsscalaService
import com.softwaremill.macwire._

class AkkakafkastreamsscalaStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new AkkakafkastreamsscalaStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new AkkakafkastreamsscalaStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[AkkakafkastreamsscalaStreamService])
}

abstract class AkkakafkastreamsscalaStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[AkkakafkastreamsscalaStreamService](wire[AkkakafkastreamsscalaStreamServiceImpl])

  // Bind the AkkakafkastreamsscalaService client
  lazy val akkakafkastreamsscalaService = serviceClient.implement[AkkakafkastreamsscalaService]
}
