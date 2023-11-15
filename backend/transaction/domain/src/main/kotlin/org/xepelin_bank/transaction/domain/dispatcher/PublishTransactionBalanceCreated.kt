package org.xepelin_bank.transaction.domain.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.transaction.domain.event.TransactionBalanceCreatedCommand
import java.util.UUID

interface PublishTransactionBalanceCreated{
    fun publish(key: UUID?, value: TransactionBalanceCreatedCommand): Completable

}
