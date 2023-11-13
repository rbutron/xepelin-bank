package org.xepelin_bank.infrastructure.flyway.config.mappers

data class DataBaseConfigMapper(
    val url: String,
    val user: String,
    val password: String
)
