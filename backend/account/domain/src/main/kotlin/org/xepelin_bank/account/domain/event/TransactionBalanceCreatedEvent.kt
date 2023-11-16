package org.xepelin_bank.account.domain.event

import com.fasterxml.jackson.annotation.JsonProperty

data class TransactionBalanceCreatedEvent(
    val amount: String,
    @JsonProperty("transaction-type")
    val transactionType: String,
    val brand: String,
)
