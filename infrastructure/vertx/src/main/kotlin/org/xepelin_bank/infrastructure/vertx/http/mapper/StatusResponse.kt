package org.xepelin_bank.infrastructure.vertx.http.mapper

data class StatusResponse(
    val messages: String,
    val status: Int,
    val body: Any
)
