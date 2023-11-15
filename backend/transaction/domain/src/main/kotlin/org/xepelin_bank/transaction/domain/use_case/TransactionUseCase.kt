package org.xepelin_bank.transaction.domain.use_case

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.transaction.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.transaction.domain.kernel.Transaction
import org.xepelin_bank.transaction.domain.kernel.account.AccountId

interface TransactionUseCase {
    fun createTransaction(transaction: Transaction): Single<Transaction>
    fun publishTransactionCommand(accountId: AccountId, createTransactionBalanceCommand: CreateTransactionBalanceCommand): Completable
}
