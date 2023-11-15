package org.xepelin_bank.transaction.adapter.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import org.xepelin_bank.transaction.domain.dispatcher.PublishTransactionBalanceCreated
import org.xepelin_bank.transaction.domain.event.TransactionBalanceCreatedCommand
import java.util.UUID

class PublishTransactionBalanceCreatedImpl(private val publishCommandOrEvent: KafkaProducerClient) : PublishTransactionBalanceCreated {
    override fun publish(key: UUID?, value: TransactionBalanceCreatedCommand): Completable =
        this.publishCommandOrEvent.publisher(Topics.COMPLETE_MONO_LOG_TOPIC.value, key.toString(), value)
}
