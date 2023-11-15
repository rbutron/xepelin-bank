package org.xepelin_bank.account.domain.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.account.domain.command.CreateTransactionBalanceCommand
import java.util.UUID

interface PublishCreateTransaction {
    fun publish(key: UUID?, value: CreateTransactionBalanceCommand): Completable
}
