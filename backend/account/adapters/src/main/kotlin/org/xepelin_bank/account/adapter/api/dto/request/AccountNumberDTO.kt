package org.xepelin_bank.account.adapter.api.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountNumberDTO(
    @JsonProperty("account-number")
    val accountNumber: String,
)
