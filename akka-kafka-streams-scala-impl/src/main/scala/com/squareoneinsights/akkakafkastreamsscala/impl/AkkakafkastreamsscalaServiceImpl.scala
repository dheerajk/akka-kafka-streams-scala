package com.squareoneinsights.akkakafkastreamsscala.impl

import com.squareoneinsights.akkakafkastreamsscala.api
import com.squareoneinsights.akkakafkastreamsscala.api.{AkkakafkastreamsscalaService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}

/**
  * Implementation of the AkkakafkastreamsscalaService.
  */
class AkkakafkastreamsscalaServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends AkkakafkastreamsscalaService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the akka-kafka-streams-scala entity for the given ID.
    val ref = persistentEntityRegistry.refFor[AkkakafkastreamsscalaEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the akka-kafka-streams-scala entity for the given ID.
    val ref = persistentEntityRegistry.refFor[AkkakafkastreamsscalaEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }


  override def greetingsTopic(): Topic[api.GreetingMessageChanged] =
    TopicProducer.singleStreamWithOffset {
      fromOffset =>
        persistentEntityRegistry.eventStream(AkkakafkastreamsscalaEvent.Tag, fromOffset)
          .map(ev => (convertEvent(ev), ev.offset))
    }

  private def convertEvent(helloEvent: EventStreamElement[AkkakafkastreamsscalaEvent]): api.GreetingMessageChanged = {
    helloEvent.event match {
      case GreetingMessageChanged(msg) => api.GreetingMessageChanged(helloEvent.entityId, msg)
    }
  }
}
