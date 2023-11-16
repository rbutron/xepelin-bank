package org.xepelin_bank.transaction.domain.use_case

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.common.extensions.message.constants.Topics
import org.xepelin_bank.transaction.domain.kernel.Transaction

interface TransactionUseCase {
    fun createTransaction(transaction: Transaction): Single<Transaction>
    fun publishTransactionAccountCommand(transaction: Transaction, topic: Topics): Completable
    fun publishTransactionExistingAccountCommand(transaction: Transaction): Completable
}
