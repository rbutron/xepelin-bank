package org.xepelin_bank.infrastructure.vertx.database.mappers

data class CredentialDBMapper(
    val port: Int,
    val host: String,
    val database: String,
    val user: String,
    val password: String
)
