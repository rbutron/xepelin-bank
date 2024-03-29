package org.xepelin_bank.transaction.domain.event

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountCreatedEvent(
    val amount: String,
    val brand: String,
)
