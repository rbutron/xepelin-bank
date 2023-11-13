package org.xepelin_bank.account.adapter.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.account.domain.command.CreateAccountCommand
import org.xepelin_bank.account.domain.dispatcher.PublishAccountToCreate
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import java.util.UUID

class PublishAccountToCreateImpl (private val publishCommandOrEvent: KafkaProducerClient):
    PublishAccountToCreate {
    override fun publish(key: UUID, value: CreateAccountCommand): Completable =
        this.publishCommandOrEvent.publisher(Topics.CREATE_MONO_LOG_TOPIC.value, key.toString(), value)
}
