package org.xepelin_bank.account.domain.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.account.domain.event.TransactionBalanceCreatedEvent
import java.util.UUID

interface PublishUpdateTransactionBalance {
    fun publish(key: UUID?, value: TransactionBalanceCreatedEvent): Completable
}
