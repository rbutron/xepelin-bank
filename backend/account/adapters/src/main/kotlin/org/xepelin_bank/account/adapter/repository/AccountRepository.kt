package org.xepelin_bank.account.adapter.repository

import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.account.adapter.repository.entity.AccountEntity

interface AccountRepository {
    fun save(accountEntity: AccountEntity): Single<AccountEntity>
    fun getAccountByNumber(accountNumber: String): Single<AccountEntity>
}
