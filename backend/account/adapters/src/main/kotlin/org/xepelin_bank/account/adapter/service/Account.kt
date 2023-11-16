package org.xepelin_bank.account.adapter.service

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.account.adapter.dispatcher.PublishAccountCreatedImpl
import org.xepelin_bank.account.adapter.dispatcher.PublishAccountToCreateImpl
import org.xepelin_bank.account.adapter.dispatcher.PublishCreateTransactionImpl
import org.xepelin_bank.account.adapter.dispatcher.PublishUpdateTransactionBalanceImpl
import org.xepelin_bank.account.adapter.repository.AccountRepository
import org.xepelin_bank.account.adapter.repository.entity.AccountAmountEntity
import org.xepelin_bank.account.adapter.repository.entity.AccountEntity
import org.xepelin_bank.account.domain.command.CreateAccountCommand
import org.xepelin_bank.account.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.account.domain.dispatcher.PublishAccountCreated
import org.xepelin_bank.account.domain.dispatcher.PublishAccountToCreate
import org.xepelin_bank.account.domain.dispatcher.PublishCreateTransaction
import org.xepelin_bank.account.domain.dispatcher.PublishUpdateTransactionBalance
import org.xepelin_bank.account.domain.event.AccountCreatedEvent
import org.xepelin_bank.account.domain.event.TransactionBalanceCreatedEvent
import org.xepelin_bank.account.domain.kernel.Account
import org.xepelin_bank.account.domain.kernel.AccountAmount
import org.xepelin_bank.account.domain.kernel.AccountId
import org.xepelin_bank.account.domain.use_case.AccountUseCase
import org.xepelin_bank.common.extensions.message.constants.BrandType
import org.xepelin_bank.common.extensions.message.constants.TransactionType
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient

class Account @Inject constructor(private val accountRepository: AccountRepository, kafkaClient: KafkaProducerClient) :
    AccountUseCase {

    private val publishAccountToCreate: PublishAccountToCreate
    private val publishAccountCreated: PublishAccountCreated
    private val publishCreateTransaction: PublishCreateTransaction
    private val publishUpdateTransactionBalance: PublishUpdateTransactionBalance

    init {
        this.publishAccountToCreate = PublishAccountToCreateImpl(kafkaClient)
        this.publishAccountCreated = PublishAccountCreatedImpl(kafkaClient)
        this.publishCreateTransaction = PublishCreateTransactionImpl(kafkaClient)
        this.publishUpdateTransactionBalance = PublishUpdateTransactionBalanceImpl(kafkaClient)
    }

    override fun publishAccountCommand(accountId: AccountId, account: Account): Completable =
        this.publishAccountToCreate.publish(
            accountId.value(),
            CreateAccountCommand(
                accountNumber = account.accountNumber.value(),
                accountName = account.name.value(),
                amount = account.amount.value(),
                brand = BrandType.ACCOUNT.toString()
            )
        )

    override fun getAccount(accountId: AccountId): Single<AccountAmount> =
        this.accountRepository.getAccountById(accountId.value().toString()).map { account ->
            AccountAmount(
                account.amount
            )
        }

    override fun createAccount(account: Account): Completable =
        this.accountRepository.save(
            AccountEntity(
                id = account.id?.value(),
                accountName = account.name.value(),
                accountNumber = account.accountNumber.value(),
                amount = account.amount.value(),
            )
        ).flatMapCompletable {
            this.publishCreateTransaction.publish(
                account.id?.value(), CreateTransactionBalanceCommand(
                    amount = account.amount.value(),
                    transactionType = TransactionType.DEPOSIT.toString(),
                    brand = BrandType.NEW_ACCOUNT_TRANSACTION.toString(),
                )
            )
        }

    override fun createAccountTransaction(accountId: AccountId, accountCreatedEvent: AccountCreatedEvent): Completable =
        this.publishAccountCreated.publish(
            key = accountId.value(),
            accountCreatedEvent
        )

    override fun updateAmountAccount(
        accountId: AccountId,
        accountAmount: AccountAmount,
        transactionType: org.xepelin_bank.account.domain.kernel.transaction.TransactionType
    ): Completable =
        getAccount(accountId).flatMapCompletable { account ->

            val amount = if (transactionType.value() == TransactionType.DEPOSIT) account.value()
                .toDouble() + accountAmount.value()
                .toDouble() else account.value().toDouble() - accountAmount.value().toDouble()

            this.accountRepository.update(
                AccountAmountEntity(
                    id = accountId.value().toString(),
                    amount = amount.toString()
                )
            ).flatMapCompletable {
                this.publishUpdateTransactionBalance.publish(
                    accountId.value(),
                    TransactionBalanceCreatedEvent(
                        amount = amount.toString(),
                        transactionType = transactionType.value().toString(),
                        brand = BrandType.ACCOUNT.toString()
                    )
                )
            }
        }
}
