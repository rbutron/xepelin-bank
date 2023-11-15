package org.xepelin_bank.mono_log.adapter.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import org.xepelin_bank.mono_log.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.mono_log.domain.dispatcher.PublishTransactionToCreate
import java.util.UUID

class PublishTransactionToCreateImpl(private val publishCommandOrEvent: KafkaProducerClient) :
    PublishTransactionToCreate {
    override fun publish(key: UUID?, value: CreateTransactionBalanceCommand): Completable =
        this.publishCommandOrEvent.publisher(Topics.NEW_ACCOUNT_TRANSACTION_TOPIC.value, key.toString(), value)
}
