package org.xepelin_bank.mono_log.adapter.service

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Completable
import io.vertx.core.json.JsonObject
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import org.xepelin_bank.mono_log.adapter.dispatcher.PublishAccountToCreateExistingAccountImpl
import org.xepelin_bank.mono_log.adapter.dispatcher.PublishAccountToCreateNewAccountImpl
import org.xepelin_bank.mono_log.adapter.dispatcher.PublishTransactionToCreateImpl
import org.xepelin_bank.mono_log.adapter.repository.MonoLogRepository
import org.xepelin_bank.mono_log.adapter.repository.entity.MonoLogEntity
import org.xepelin_bank.mono_log.domain.command.CreateAccountCommand
import org.xepelin_bank.mono_log.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.mono_log.domain.dispatcher.PublishAccountToCreateExistingAccount
import org.xepelin_bank.mono_log.domain.dispatcher.PublishAccountToCreateNewAccount
import org.xepelin_bank.mono_log.domain.dispatcher.PublishTransactionToCreate
import org.xepelin_bank.mono_log.domain.kernel.MonoLog
import org.xepelin_bank.mono_log.domain.kernel.account.AccountId
import org.xepelin_bank.mono_log.domain.use_case.MonoLogUseCase

class MonoLog @Inject constructor(
    private val monoLogRepository: MonoLogRepository,
    kafkaClient: KafkaProducerClient
) : MonoLogUseCase {

    private val publishAccountToCreateNewAccount: PublishAccountToCreateNewAccount
    private val publishTransactionToCreate: PublishTransactionToCreate
    private val publishAccountToCreateExistingAccount: PublishAccountToCreateExistingAccount

    init {
        this.publishAccountToCreateNewAccount = PublishAccountToCreateNewAccountImpl(kafkaClient)
        this.publishTransactionToCreate = PublishTransactionToCreateImpl(kafkaClient)
        this.publishAccountToCreateExistingAccount = PublishAccountToCreateExistingAccountImpl(kafkaClient)
    }

    override fun createMonoLog(monoLog: MonoLog): Completable =
        this.monoLogRepository.save(
            MonoLogEntity(
                businessId = monoLog.businessId.value(),
                eventType = monoLog.eventType.value().key,
                brand = monoLog.brand.value().toString(),
                data = JsonObject.mapFrom(monoLog.data),
            )
        ).flatMapCompletable {
            Completable.complete()
        }

    override fun publishAccountCommand(accountId: AccountId, createAccountCommand: CreateAccountCommand): Completable =
        this.publishAccountToCreateNewAccount.publish(accountId.value(), createAccountCommand)

    override fun publishNewAccountTransactionCommand(
        accountId: AccountId,
        createAccountCommand: CreateTransactionBalanceCommand
    ): Completable =
        this.publishTransactionToCreate.publish(accountId.value(), createAccountCommand)

    override fun publishExistingAccountTransactionCommand(
        accountId: AccountId,
        createTransactionBalanceCommand: CreateTransactionBalanceCommand
    ): Completable =
        this.publishAccountToCreateExistingAccount.publish(accountId.value(), createTransactionBalanceCommand)
}
