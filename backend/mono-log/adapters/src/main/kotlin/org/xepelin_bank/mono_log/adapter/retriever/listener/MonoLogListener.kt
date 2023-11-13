package org.xepelin_bank.mono_log.adapter.retriever.listener

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Completable
import io.vertx.core.json.JsonObject
import io.vertx.rxjava3.kafka.client.consumer.KafkaConsumer
import org.xepelin_bank.common.extensions.SystemExtension.parseTo
import org.xepelin_bank.common.extensions.message.constants.BrandType
import org.xepelin_bank.common.extensions.message.constants.EventType
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaConsumerClient
import org.xepelin_bank.mono_log.adapter.retriever.MonoLogSubscriberImpl
import org.xepelin_bank.mono_log.adapter.retriever.MonoLogSubscriberOrchestratorImpl
import org.xepelin_bank.mono_log.domain.command.CreateAccountCommand
import org.xepelin_bank.mono_log.domain.kernel.account.AccountId
import org.xepelin_bank.mono_log.domain.retriever.MonoLogSubscriber
import org.xepelin_bank.mono_log.domain.retriever.MonoLogSubscriberOrchestrator
import org.xepelin_bank.mono_log.domain.use_case.MonoLogUseCase
import java.util.UUID

class MonoLogListener @Inject constructor(
    client: KafkaConsumerClient,
    monoLogUseCase: MonoLogUseCase
) : Listener {

    private val consumer: KafkaConsumer<String, JsonObject>
    private val monoLogSubscriber: MonoLogSubscriber
    private val monoLogSubscriberOrchestrator : MonoLogSubscriberOrchestrator

    init {
        this.consumer = client.configureConsumer()
        this.monoLogSubscriber = MonoLogSubscriberImpl(monoLogUseCase)
        this.monoLogSubscriberOrchestrator = MonoLogSubscriberOrchestratorImpl(monoLogUseCase)
    }

    override fun listen(): Completable =
        this.consumer.rxSubscribe(
            setOf(
                Topics.CREATE_MONO_LOG_TOPIC.value,
                Topics.UPDATE_MONO_LOG_TOPIC.value,
                Topics.DELETE_MONO_LOG_TOPIC.value,
                Topics.COMPLETE_MONO_LOG_TOPIC.value
            )
        )
            .andThen {
                this.consumer.handler { record ->
                    val accountId = AccountId(UUID.fromString(record.value().map { it.key }[0]))
                    when (record.topic()) {
                        Topics.CREATE_MONO_LOG_TOPIC.value -> {
                            val createAccountCommand = record.value().getJsonObject(accountId.value().toString())
                                .parseTo(CreateAccountCommand::class.java)
                            this.monoLogSubscriberOrchestrator.consumer(accountId, createAccountCommand).subscribe()
                        }

                        Topics.UPDATE_MONO_LOG_TOPIC.value -> {
                            //TODO("On the next step the app will be able to work with this section")
                        }

                        Topics.DELETE_MONO_LOG_TOPIC.value -> {
                            //TODO("On the next step the app will be able to work with this section")
                        }

                        Topics.COMPLETE_MONO_LOG_TOPIC.value -> {
                            val createAccountCommand = record.value().getJsonObject(accountId.value().toString())
                                .parseTo(CreateAccountCommand::class.java)
                            this.monoLogSubscriber.consumer(
                                accountId,
                                EventType.COMPLETE_EVENT_TYPE,
                                BrandType.ACCOUNT_BRAND_TYPE,
                                createAccountCommand
                            ).subscribe()
                        }
                    }
                }
            }
}
