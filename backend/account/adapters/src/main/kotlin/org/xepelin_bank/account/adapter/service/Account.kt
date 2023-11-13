package org.xepelin_bank.account.adapter.service

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.account.adapter.dispatcher.AccountCreatedImpl
import org.xepelin_bank.account.adapter.dispatcher.PublishAccountToCreateImpl
import org.xepelin_bank.account.adapter.repository.AccountRepository
import org.xepelin_bank.account.adapter.repository.entity.AccountEntity
import org.xepelin_bank.account.domain.command.CreateAccountCommand
import org.xepelin_bank.account.domain.dispatcher.AccountCreated
import org.xepelin_bank.account.domain.dispatcher.PublishAccountToCreate
import org.xepelin_bank.account.domain.event.AccountCreatedEvent
import org.xepelin_bank.account.domain.kernel.Account
import org.xepelin_bank.account.domain.kernel.AccountAmount
import org.xepelin_bank.account.domain.kernel.AccountNumber
import org.xepelin_bank.account.domain.use_case.AccountUseCase
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import java.util.*

class Account @Inject constructor(private val accountRepository: AccountRepository, kafkaClient: KafkaProducerClient) :
    AccountUseCase {

    private val publishAccountToCreate: PublishAccountToCreate
    private val accountCreated: AccountCreated

    init {
        this.publishAccountToCreate = PublishAccountToCreateImpl(kafkaClient)
        this.accountCreated = AccountCreatedImpl(kafkaClient)
    }

    override fun publishAccountCommand(key: UUID, account: Account): Completable =
        this.publishAccountToCreate.publish(
            key,
            CreateAccountCommand(
                accountNumber = account.accountNumber.value(),
                accountName = account.name.value(),
                amount = account.amount.value()
            )
        )

    override fun getAccount(accountNumber: AccountNumber): Single<AccountAmount> =
        accountRepository.getAccountByNumber(accountNumber.value()).map { account ->
            AccountAmount(
                account.amount
            )
        }

    override fun createAccount(account: Account): Completable =
        accountRepository.save(
            AccountEntity(
                id = account.id?.value(),
                accountName = account.name.value(),
                accountNumber = account.accountNumber.value(),
                amount = account.amount.value(),
            )
        ).flatMapCompletable {
            Completable.defer {
                this.accountCreated.publish(
                    key = it.id,
                    AccountCreatedEvent(
                        accountNumber = account.accountNumber.value(),
                        accountName = account.name.value(),
                        amount = account.amount.value()
                    )
                )
            }
        }
}
