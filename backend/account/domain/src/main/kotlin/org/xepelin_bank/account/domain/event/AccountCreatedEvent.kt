package org.xepelin_bank.account.domain.event

data class AccountCreatedEvent(
    val amount: String,
    val brand: String
)
