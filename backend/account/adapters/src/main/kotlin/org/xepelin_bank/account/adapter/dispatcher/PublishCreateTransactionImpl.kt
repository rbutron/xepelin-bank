package org.xepelin_bank.account.adapter.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.account.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.account.domain.dispatcher.PublishCreateTransaction
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import java.util.UUID

class PublishCreateTransactionImpl(private val publishCommandOrEvent: KafkaProducerClient) : PublishCreateTransaction {
    override fun publish(key: UUID?, value: CreateTransactionBalanceCommand): Completable =
        this.publishCommandOrEvent.publisher(Topics.CREATE_MONO_LOG_TOPIC.value, key.toString(), value)
}
