package org.xepelin_bank.transaction.adapter.retriever

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.transaction.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.transaction.domain.kernel.account.AccountId
import org.xepelin_bank.transaction.domain.retriever.TransactionSubscriber
import org.xepelin_bank.transaction.domain.use_case.TransactionUseCase

class TransactionSubscriberImpl(private val transactionUseCase: TransactionUseCase) : TransactionSubscriber {
    override fun consumer(
        accountId: AccountId,
        createTransactionBalanceCommand: CreateTransactionBalanceCommand
    ): Completable =
        this.transactionUseCase.publishTransactionCommand(accountId, createTransactionBalanceCommand)
}
