package org.xepelin_bank.account.adapter.api.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateAccountDTO(
    @JsonProperty("account-name")
    val accountName: String,
    @JsonProperty("account-number")
    val accountNumber: String,
    val amount: String?,
)
