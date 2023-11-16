package org.xepelin_bank.account.adapter.repository

import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.account.adapter.repository.entity.AccountAmountEntity
import org.xepelin_bank.account.adapter.repository.entity.AccountEntity

interface AccountRepository {
    fun save(accountEntity: AccountEntity): Single<AccountEntity>
    fun getAccountById(accountNumber: String): Single<AccountEntity>
    fun update(accountAmountEntity: AccountAmountEntity): Single<AccountEntity>
}
