package org.xepelin_bank.transaction.adapter.service

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.common.extensions.message.constants.BrandType
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import org.xepelin_bank.transaction.adapter.dispatcher.PublishAccountCreatedImpl
import org.xepelin_bank.transaction.adapter.dispatcher.PublishTransactionBalanceCreatedImpl
import org.xepelin_bank.transaction.adapter.repository.TransactionRepository
import org.xepelin_bank.transaction.adapter.repository.entity.TransactionEntity
import org.xepelin_bank.transaction.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.transaction.domain.dispatcher.PublishAccountCreated
import org.xepelin_bank.transaction.domain.dispatcher.PublishTransactionBalanceCreated
import org.xepelin_bank.transaction.domain.event.AccountCreatedEvent
import org.xepelin_bank.transaction.domain.event.TransactionBalanceCreatedCommand
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

    init {
        this.publishTransactionBalanceCreated = PublishTransactionBalanceCreatedImpl(kafkaClient)
        this.publishAccountCreated = PublishAccountCreatedImpl(kafkaClient)
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

    override fun publishTransactionCommand(
        accountId: AccountId,
        createTransactionBalanceCommand: CreateTransactionBalanceCommand
    ): Completable =
        this.createTransaction(
            Transaction(
                amount = AccountAmount(createTransactionBalanceCommand.amount),
                transactionType = TransactionType(
                    org.xepelin_bank.common.extensions.message.constants.TransactionType.valueOf(
                        createTransactionBalanceCommand.transactionType
                    )
                ),
                accountId = accountId
            )
        ).flatMapCompletable {
            this.publishTransactionBalanceCreated.publish(
                accountId.value(),
                TransactionBalanceCreatedCommand(
                    amount = createTransactionBalanceCommand.amount,
                    transactionType = createTransactionBalanceCommand.transactionType,
                    brand = BrandType.TRANSACTION.toString()
                )
            ).andThen(
                publishAccountCreated.publish(
                    accountId.value(),
                    AccountCreatedEvent(
                        amount = createTransactionBalanceCommand.amount,
                        brand = BrandType.TRANSACTION.toString()
                    )
                )
            )
        }
}
