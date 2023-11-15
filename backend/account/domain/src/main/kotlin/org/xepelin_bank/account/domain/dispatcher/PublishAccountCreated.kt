package org.xepelin_bank.account.domain.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.account.domain.event.AccountCreatedEvent
import java.util.UUID

interface PublishAccountCreated {
    fun publish(key: UUID?, value: AccountCreatedEvent): Completable
}
