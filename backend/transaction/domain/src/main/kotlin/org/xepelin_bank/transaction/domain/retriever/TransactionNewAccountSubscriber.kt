package org.xepelin_bank.transaction.domain.retriever

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.transaction.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.transaction.domain.kernel.account.AccountId

interface TransactionNewAccountSubscriber {
    fun consumer(accountId: AccountId, createTransactionBalanceCommand: CreateTransactionBalanceCommand, topic: Topics): Completable
}
