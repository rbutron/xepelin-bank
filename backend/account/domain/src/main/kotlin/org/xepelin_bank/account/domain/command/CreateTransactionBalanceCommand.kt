package org.xepelin_bank.account.domain.command

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateTransactionBalanceCommand(
    val amount: String,
    @JsonProperty("transaction-type")
    val transactionType: String,
    val brand: String,
)
