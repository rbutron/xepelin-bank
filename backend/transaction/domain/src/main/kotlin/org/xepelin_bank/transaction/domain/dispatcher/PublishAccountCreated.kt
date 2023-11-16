package org.xepelin_bank.transaction.domain.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.transaction.domain.event.AccountCreatedEvent
import org.xepelin_bank.transaction.domain.event.TransactionBalanceCreatedEvent
import java.util.UUID

interface PublishAccountCreated {
    fun publish(key: UUID?, value: AccountCreatedEvent, topic: Topics): Completable
    fun publish(key: UUID?, value: TransactionBalanceCreatedEvent, topic: Topics): Completable
}
