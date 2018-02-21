package com.squareoneinsights.akkakafkastreamsscala.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.squareoneinsights.akkakafkastreamsscala.api.AkkakafkastreamsscalaService
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.softwaremill.macwire._

class AkkakafkastreamsscalaLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new AkkakafkastreamsscalaApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new AkkakafkastreamsscalaApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[AkkakafkastreamsscalaService])
}

abstract class AkkakafkastreamsscalaApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[AkkakafkastreamsscalaService](wire[AkkakafkastreamsscalaServiceImpl])

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = AkkakafkastreamsscalaSerializerRegistry

  // Register the akka-kafka-streams-scala persistent entity
  persistentEntityRegistry.register(wire[AkkakafkastreamsscalaEntity])
}
