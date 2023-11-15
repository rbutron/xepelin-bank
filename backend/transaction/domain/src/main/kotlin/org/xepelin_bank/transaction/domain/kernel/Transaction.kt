package org.xepelin_bank.transaction.domain.kernel

import org.xepelin_bank.transaction.domain.kernel.account.AccountAmount
import org.xepelin_bank.transaction.domain.kernel.account.AccountId

data class Transaction (
    val id: TransactionId? = null,
    val amount: AccountAmount,
    val transactionType: TransactionType,
    val accountId: AccountId
)
