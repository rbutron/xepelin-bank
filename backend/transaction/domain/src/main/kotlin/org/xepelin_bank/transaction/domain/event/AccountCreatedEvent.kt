package org.xepelin_bank.transaction.domain.event

data class AccountCreatedEvent(
    val amount: String,
    val brand: String
)
