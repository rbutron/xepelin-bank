package org.xepelin_bank.transaction.adapter.api.request

import com.fasterxml.jackson.annotation.JsonProperty

data class TransactionDTO(
    @JsonProperty("transaction-type")
    val transactionType: String,
    val amount: String,
    @JsonProperty("account-id")
    val accountId: String
)
