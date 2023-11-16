package org.xepelin_bank.mono_log.domain.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.mono_log.domain.command.CreateTransactionBalanceCommand
import java.util.UUID

interface PublishAccountToCreateExistingAccount {
    fun publish(key: UUID?, value: CreateTransactionBalanceCommand): Completable
}
