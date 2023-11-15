package org.xepelin_bank.account.domain.use_case

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.account.domain.event.AccountCreatedEvent
import org.xepelin_bank.account.domain.kernel.Account
import org.xepelin_bank.account.domain.kernel.AccountAmount
import org.xepelin_bank.account.domain.kernel.AccountId
import org.xepelin_bank.account.domain.kernel.AccountNumber

interface AccountUseCase {
    fun publishAccountCommand(accountId: AccountId, account: Account): Completable
    fun getAccount(accountNumber: AccountNumber): Single<AccountAmount>
    fun createAccount(account: Account): Completable
    fun createAccountTransaction(accountId: AccountId, accountCreatedEvent: AccountCreatedEvent): Completable
}
