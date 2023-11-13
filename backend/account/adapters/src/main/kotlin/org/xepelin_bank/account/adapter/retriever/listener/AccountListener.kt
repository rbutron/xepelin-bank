package org.xepelin_bank.account.adapter.retriever.listener

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Completable
import io.vertx.core.json.JsonObject
import io.vertx.rxjava3.kafka.client.consumer.KafkaConsumer
import org.xepelin_bank.account.adapter.retriever.AccountSubscriberImpl
import org.xepelin_bank.account.domain.command.CreateAccountCommand
import org.xepelin_bank.account.domain.kernel.AccountId
import org.xepelin_bank.account.domain.retriever.AccountSubscriber
import org.xepelin_bank.account.domain.use_case.AccountUseCase
import org.xepelin_bank.common.extensions.SystemExtension.parseTo
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaConsumerClient
import java.util.UUID

class AccountListener @Inject constructor(
    client: KafkaConsumerClient,
    accountUseCase: AccountUseCase
) : Listener {

    private val consumer: KafkaConsumer<String, JsonObject>
    private val accountSubscriber: AccountSubscriber

    init {
        this.consumer = client.configureConsumer()
        this.accountSubscriber = AccountSubscriberImpl(accountUseCase)
    }

    override fun listen(): Completable =
        this.consumer.rxSubscribe(Topics.CREATE_ACCOUNT_TOPIC.value)
            .andThen {
                this.consumer.handler { record ->
                    when (record.topic()) {
                        Topics.CREATE_ACCOUNT_TOPIC.value -> {
                            val accountId = AccountId(UUID.fromString(record.value().map { it.key }[0]))
                            val createAccountCommand = record.value().getJsonObject(accountId.value().toString())
                                .parseTo(CreateAccountCommand::class.java)
                            this.accountSubscriber.consumer(accountId, createAccountCommand).subscribe()
                        }
                    }
                }
            }
}
