package org.xepelin_bank.transaction.domain.event

import com.fasterxml.jackson.annotation.JsonProperty

data class TransactionBalanceCreatedCommand(
    val amount: String,
    @JsonProperty("transaction-type")
    val transactionType: String,
    val brand: String,
)
