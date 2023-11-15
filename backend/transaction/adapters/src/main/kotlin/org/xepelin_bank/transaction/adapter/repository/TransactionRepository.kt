package org.xepelin_bank.transaction.adapter.repository

import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.transaction.adapter.repository.entity.TransactionEntity

interface TransactionRepository {
    fun save(transactionEntity: TransactionEntity): Single<TransactionEntity>
}
