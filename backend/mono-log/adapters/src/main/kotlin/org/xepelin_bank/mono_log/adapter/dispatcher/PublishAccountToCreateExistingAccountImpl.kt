package org.xepelin_bank.mono_log.adapter.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import org.xepelin_bank.mono_log.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.mono_log.domain.dispatcher.PublishAccountToCreateExistingAccount
import java.util.UUID

class PublishAccountToCreateExistingAccountImpl(private val publishCommandOrEvent: KafkaProducerClient) : PublishAccountToCreateExistingAccount {
    override fun publish(key: UUID?, value: CreateTransactionBalanceCommand): Completable =
        publishCommandOrEvent.publisher(Topics.EXISTING_ACCOUNT_TRANSACTION_TOPIC.value, key.toString(), value)
}
