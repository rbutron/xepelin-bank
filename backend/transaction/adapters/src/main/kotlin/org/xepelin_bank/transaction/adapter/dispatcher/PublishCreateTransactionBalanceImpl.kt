package org.xepelin_bank.transaction.adapter.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import org.xepelin_bank.transaction.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.transaction.domain.dispatcher.PublishCreateTransactionBalance
import java.util.UUID

class PublishCreateTransactionBalanceImpl(private val publishCommandOrEvent: KafkaProducerClient) : PublishCreateTransactionBalance {
    override fun publish(key: UUID?, value: CreateTransactionBalanceCommand): Completable =
        publishCommandOrEvent.publisher(Topics.CREATE_MONO_LOG_TOPIC.value, key.toString(), value)
}
