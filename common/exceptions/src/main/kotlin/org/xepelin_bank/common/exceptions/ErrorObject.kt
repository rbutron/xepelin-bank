package org.xepelin_bank.common.exceptions

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorObject(val message: Any?, val cause: Any?, @JsonProperty("status_code") val status: Int)
