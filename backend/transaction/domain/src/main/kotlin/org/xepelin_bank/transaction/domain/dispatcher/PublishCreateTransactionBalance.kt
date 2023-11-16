package org.xepelin_bank.transaction.domain.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.transaction.domain.command.CreateTransactionBalanceCommand
import java.util.UUID

interface PublishCreateTransactionBalance {
    fun publish(key: UUID?, value: CreateTransactionBalanceCommand): Completable
}
