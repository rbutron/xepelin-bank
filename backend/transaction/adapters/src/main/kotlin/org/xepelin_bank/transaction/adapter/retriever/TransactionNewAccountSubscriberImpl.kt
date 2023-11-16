package org.xepelin_bank.transaction.adapter.retriever

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.common.extensions.message.constants.TransactionType
import org.xepelin_bank.transaction.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.transaction.domain.kernel.Transaction
import org.xepelin_bank.transaction.domain.kernel.account.AccountAmount
import org.xepelin_bank.transaction.domain.kernel.account.AccountId
import org.xepelin_bank.transaction.domain.retriever.TransactionNewAccountSubscriber
import org.xepelin_bank.transaction.domain.use_case.TransactionUseCase

class TransactionNewAccountSubscriberImpl(private val transactionUseCase: TransactionUseCase) : TransactionNewAccountSubscriber {
    override fun consumer(
        accountId: AccountId,
        createTransactionBalanceCommand: CreateTransactionBalanceCommand,
        topic: Topics
    ): Completable =
        this.transactionUseCase.publishTransactionAccountCommand(
            Transaction(
                amount = AccountAmount(createTransactionBalanceCommand.amount),
                transactionType = org.xepelin_bank.transaction.domain.kernel.TransactionType(
                    TransactionType.valueOf(
                        createTransactionBalanceCommand.transactionType
                    )
                ),
                accountId = accountId
            ),
            topic
        )
}
