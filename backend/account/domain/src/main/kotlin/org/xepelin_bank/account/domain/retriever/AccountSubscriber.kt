package org.xepelin_bank.account.domain.retriever

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.account.domain.command.CreateAccountCommand
import org.xepelin_bank.account.domain.kernel.AccountId

interface AccountSubscriber {
    fun consumer(accountId: AccountId, command: CreateAccountCommand): Completable
}
