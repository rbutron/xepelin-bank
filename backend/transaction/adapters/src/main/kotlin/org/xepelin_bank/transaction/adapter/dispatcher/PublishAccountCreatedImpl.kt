package org.xepelin_bank.transaction.adapter.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import org.xepelin_bank.transaction.domain.dispatcher.PublishAccountCreated
import org.xepelin_bank.transaction.domain.event.AccountCreatedEvent
import org.xepelin_bank.transaction.domain.event.TransactionBalanceCreatedEvent
import java.util.UUID

class PublishAccountCreatedImpl(private val publishCommandOrEvent: KafkaProducerClient) : PublishAccountCreated {
    override fun publish(key: UUID?, value: AccountCreatedEvent, topic: Topics): Completable =
        publishCommandOrEvent.publisher(topic.value, key.toString(), value)

    override fun publish(key: UUID?, value: TransactionBalanceCreatedEvent, topic: Topics): Completable =
        publishCommandOrEvent.publisher(topic.value, key.toString(), value)
}
