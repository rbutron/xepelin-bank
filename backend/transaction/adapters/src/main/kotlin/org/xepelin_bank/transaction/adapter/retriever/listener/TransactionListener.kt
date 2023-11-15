package org.xepelin_bank.transaction.adapter.retriever.listener

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Completable
import io.vertx.core.json.JsonObject
import io.vertx.rxjava3.kafka.client.consumer.KafkaConsumer
import org.xepelin_bank.common.extensions.SystemExtension.parseTo
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaConsumerClient
import org.xepelin_bank.transaction.adapter.retriever.TransactionSubscriberImpl
import org.xepelin_bank.transaction.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.transaction.domain.kernel.account.AccountId
import org.xepelin_bank.transaction.domain.retriever.TransactionSubscriber
import org.xepelin_bank.transaction.domain.use_case.TransactionUseCase
import java.util.UUID

class TransactionListener @Inject constructor(
    client: KafkaConsumerClient,
    transactionUseCase: TransactionUseCase
) : Listener {
    private val consumer: KafkaConsumer<String, JsonObject>
    private val transactionSubscriber: TransactionSubscriber

    init {
        this.consumer = client.configureConsumer()
        this.transactionSubscriber = TransactionSubscriberImpl(transactionUseCase)
    }

    override fun listen(): Completable =
        this.consumer.rxSubscribe(
            setOf(
                Topics.NEW_ACCOUNT_TRANSACTION_TOPIC.value,
                Topics.EXISTING_ACCOUNT_TRANSACTION_TOPIC.value
            )
        ).andThen {

            this.consumer.handler { record ->
                when (record.topic()) {
                    Topics.NEW_ACCOUNT_TRANSACTION_TOPIC.value -> {
                        val accountId = AccountId(UUID.fromString(record.value().map { it.key }[0]))
                        val createTransactionBalanceCommand = record.value().getJsonObject(accountId.value().toString())
                            .parseTo(CreateTransactionBalanceCommand::class.java)
                        this.transactionSubscriber.consumer(accountId, createTransactionBalanceCommand).subscribe()
                    }
                    Topics.EXISTING_ACCOUNT_TRANSACTION_TOPIC.value -> {

                    }
                }

            }
        }
}
