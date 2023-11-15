package org.xepelin_bank.mono_log.domain.command

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateAccountCommand(
    @JsonProperty("account-name")
    val accountName: String,
    @JsonProperty("account-number")
    val accountNumber: String,
    val amount: String,
    val brand: String
)
