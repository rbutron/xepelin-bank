package org.xepelin_bank.account.domain.kernel

data class Account(
    val id: AccountId? = null,
    val name: AccountName,
    val accountNumber: AccountNumber,
    val amount: AccountAmount,
)
