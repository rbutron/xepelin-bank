package org.xepelin_bank.account.domain.event

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountCreatedEvent(
    @JsonProperty("account-name")
    val accountName: String,
    @JsonProperty("account-number")
    val accountNumber: String,
    @JsonProperty("amount")
    val amount: String,
)
