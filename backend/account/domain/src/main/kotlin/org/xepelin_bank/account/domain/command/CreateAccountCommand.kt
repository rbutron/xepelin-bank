package org.xepelin_bank.account.domain.command

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateAccountCommand(
    @JsonProperty("account-name")
    val accountName: String,
    @JsonProperty("account-number")
    val accountNumber: String,
    @JsonProperty("amount")
    val amount: String,
)
