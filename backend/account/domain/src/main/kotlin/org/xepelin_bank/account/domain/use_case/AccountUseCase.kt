package org.xepelin_bank.account.domain.use_case

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.account.domain.kernel.Account
import org.xepelin_bank.account.domain.kernel.AccountAmount
import org.xepelin_bank.account.domain.kernel.AccountNumber
import java.util.*

interface AccountUseCase {
    fun publishAccountCommand(key: UUID, account: Account): Completable
    fun getAccount(accountNumber: AccountNumber): Single<AccountAmount>
    fun createAccount(account: Account): Completable
}
