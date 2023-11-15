package org.xepelin_bank.transaction.domain.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.transaction.domain.event.AccountCreatedEvent
import java.util.*

interface PublishAccountCreated {
    fun publish(key: UUID?, value: AccountCreatedEvent): Completable
}