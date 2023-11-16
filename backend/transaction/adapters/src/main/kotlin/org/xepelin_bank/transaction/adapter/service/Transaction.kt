package org.xepelin_bank.transaction.adapter.service

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.common.exceptions.BusinessException
import org.xepelin_bank.common.extensions.message.constants.BrandType
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import org.xepelin_bank.transaction.adapter.dispatcher.PublishAccountCreatedImpl
import org.xepelin_bank.transaction.adapter.dispatcher.PublishCreateTransactionBalanceImpl
import org.xepelin_bank.transaction.adapter.dispatcher.PublishTransactionBalanceCreatedImpl
import org.xepelin_bank.transaction.adapter.repository.TransactionRepository
import org.xepelin_bank.transaction.adapter.repository.entity.TransactionEntity
import org.xepelin_bank.transaction.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.transaction.domain.dispatcher.PublishAccountCreated
import org.xepelin_bank.transaction.domain.dispatcher.PublishCreateTransactionBalance
import org.xepelin_bank.transaction.domain.dispatcher.PublishTransactionBalanceCreated
import org.xepelin_bank.transaction.domain.event.AccountCreatedEvent
import org.xepelin_bank.transaction.domain.event.TransactionBalanceCreatedEvent
import org.xepelin_bank.transaction.domain.kernel.Transaction
import org.xepelin_bank.transaction.domain.kernel.TransactionId
import org.xepelin_bank.transaction.domain.kernel.TransactionType
import org.xepelin_bank.transaction.domain.kernel.account.AccountAmount
import org.xepelin_bank.transaction.domain.kernel.account.AccountId
import org.xepelin_bank.transaction.domain.use_case.TransactionUseCase

class Transaction @Inject constructor(
    private val transactionRepository: TransactionRepository,
    kafkaClient: KafkaProducerClient
) : TransactionUseCase {

    private val publishTransactionBalanceCreated: PublishTransactionBalanceCreated
    private val publishAccountCreated: PublishAccountCreated
    private val publishCreateTransactionBalance: PublishCreateTransactionBalance

    init {
        this.publishTransactionBalanceCreated = PublishTransactionBalanceCreatedImpl(kafkaClient)
        this.publishAccountCreated = PublishAccountCreatedImpl(kafkaClient)
        this.publishCreateTransactionBalance = PublishCreateTransactionBalanceImpl(kafkaClient)
    }

    override fun createTransaction(transaction: Transaction): Single<Transaction> =
        transactionRepository.save(
            TransactionEntity(
                transactionType = transaction.transactionType.value().toString(),
                accountId = transaction.accountId.value(),
                amount = transaction.amount.value(),
            )
        ).map { transactionEntity ->
            Transaction(
                id = transactionEntity.id?.let(::TransactionId),
                amount = AccountAmount(transactionEntity.amount),
                transactionType = TransactionType(
                    org.xepelin_bank.common.extensions.message.constants.TransactionType.valueOf(
                        transactionEntity.transactionType
                    )
                ),
                accountId = AccountId(transactionEntity.accountId),
            )
        }


    override fun publishTransactionAccountCommand(transaction: Transaction, topic: Topics): Completable =
        this.createTransaction(
            transaction
        ).flatMapCompletable {
            when (topic) {
                Topics.NEW_ACCOUNT_TRANSACTION_TOPIC -> this.buildPublishTransactionAccountEvent(
                    transaction,
                    BrandType.NEW_ACCOUNT_TRANSACTION.toString()
                )

                Topics.EXISTING_ACCOUNT_TRANSACTION_TOPIC -> this.buildPublishTransactionExistingAccountEvent(
                    transaction,
                    BrandType.EXISTING_ACCOUNT_TRANSACTION.toString()
                )

                else -> Completable.error(BusinessException("${topic.value} Topic not Found"))
            }
        }

    override fun publishTransactionExistingAccountCommand(transaction: Transaction): Completable =
        this.publishCreateTransactionBalance.publish(
            transaction.accountId.value(),
            CreateTransactionBalanceCommand(
                amount = transaction.amount.value(),
                transactionType = transaction.transactionType.value().toString(),
                brand = BrandType.EXISTING_ACCOUNT_TRANSACTION.toString()
            )
        )

    private fun buildPublishTransactionAccountEvent(transaction: Transaction, brand: String): Completable =
        this.publishTransactionBalanceCreated.publish(
            transaction.accountId.value(),
            TransactionBalanceCreatedEvent(
                amount = transaction.amount.value(),
                transactionType = transaction.transactionType.value().toString(),
                brand
            )
        ).andThen(
            publishAccountCreated.publish(
                transaction.accountId.value(),
                AccountCreatedEvent(
                    amount = transaction.amount.value(),
                    brand
                ),
                Topics.CREATE_ACCOUNT_TOPIC
            )
        )

    private fun buildPublishTransactionExistingAccountEvent(transaction: Transaction, brand: String): Completable =
        this.publishTransactionBalanceCreated.publish(
            transaction.accountId.value(),
            TransactionBalanceCreatedEvent(
                amount = transaction.amount.value(),
                transactionType = transaction.transactionType.value().toString(),
                brand
            )
        ).andThen(
            publishAccountCreated.publish(
                transaction.accountId.value(),
                TransactionBalanceCreatedEvent(
                    amount = transaction.amount.value(),
                    transactionType = transaction.transactionType.value().toString(),
                    brand
                ),
                Topics.EXISTING_ACCOUNT_TOPIC
            )
        )
}
