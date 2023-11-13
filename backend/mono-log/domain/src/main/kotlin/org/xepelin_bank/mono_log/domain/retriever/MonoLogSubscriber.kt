package org.xepelin_bank.mono_log.domain.retriever

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.common.extensions.message.constants.BrandType
import org.xepelin_bank.common.extensions.message.constants.EventType
import org.xepelin_bank.mono_log.domain.kernel.account.AccountId

interface MonoLogSubscriber {
    fun consumer(accountId: AccountId, eventType: EventType, brandType: BrandType, commandOrEvent: Any): Completable
}
