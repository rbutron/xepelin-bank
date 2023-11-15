package org.xepelin_bank.account.adapter.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.account.domain.dispatcher.PublishAccountCreated
import org.xepelin_bank.account.domain.event.AccountCreatedEvent
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import java.util.UUID

class PublishAccountCreatedImpl (private val publishCommandOrEvent: KafkaProducerClient): PublishAccountCreated {
    override fun publish(key: UUID?, value: AccountCreatedEvent): Completable =
        this.publishCommandOrEvent.publisher(Topics.COMPLETE_MONO_LOG_TOPIC.value, key.toString(), value)
}
