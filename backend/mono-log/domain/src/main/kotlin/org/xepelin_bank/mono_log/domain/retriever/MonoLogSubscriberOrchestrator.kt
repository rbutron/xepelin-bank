package org.xepelin_bank.mono_log.domain.retriever

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.mono_log.domain.kernel.account.AccountId

interface MonoLogSubscriberOrchestrator {
    fun consumer(accountId: AccountId, commandOrEvent: Any): Completable
}
