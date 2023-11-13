package org.xepelin_bank.mono_log.adapter.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import org.xepelin_bank.mono_log.domain.command.CreateAccountCommand
import org.xepelin_bank.mono_log.domain.dispatcher.PublishAccountToCreate
import java.util.UUID

class PublishAccountToCreateImpl (private val publishCommandOrEvent: KafkaProducerClient):
    PublishAccountToCreate {
    override fun publish(key: UUID?, value: CreateAccountCommand): Completable =
        this.publishCommandOrEvent.publisher(Topics.CREATE_ACCOUNT_TOPIC.value, key.toString(), value)
}
