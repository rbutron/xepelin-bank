package org.xepelin_bank.account.adapter.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.account.domain.dispatcher.PublishUpdateTransactionBalance
import org.xepelin_bank.account.domain.event.TransactionBalanceCreatedEvent
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import java.util.UUID

class PublishUpdateTransactionBalanceImpl(private val publishCommandOrEvent: KafkaProducerClient) :
    PublishUpdateTransactionBalance {
    override fun publish(key: UUID?, value: TransactionBalanceCreatedEvent): Completable =
        this.publishCommandOrEvent.publisher(Topics.UPDATE_MONO_LOG_TOPIC.value, key.toString(), value)
}
