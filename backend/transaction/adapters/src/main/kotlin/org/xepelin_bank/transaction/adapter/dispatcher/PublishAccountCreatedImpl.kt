package org.xepelin_bank.transaction.adapter.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import org.xepelin_bank.transaction.domain.dispatcher.PublishAccountCreated
import org.xepelin_bank.transaction.domain.event.AccountCreatedEvent
import java.util.UUID

class PublishAccountCreatedImpl(private val publishCommandOrEvent: KafkaProducerClient) : PublishAccountCreated {
    override fun publish(key: UUID?, value: AccountCreatedEvent): Completable =
        publishCommandOrEvent.publisher(Topics.CREATE_ACCOUNT_TOPIC.value, key.toString(), value)
}
