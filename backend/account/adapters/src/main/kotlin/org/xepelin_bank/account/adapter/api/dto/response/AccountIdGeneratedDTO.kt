package org.xepelin_bank.account.adapter.api.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountIdGeneratedDTO(
    @JsonProperty("account-id")
    val accountId: String,
)