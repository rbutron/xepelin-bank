package org.xepelin_bank.account.adapter.repository.entity

import java.time.OffsetDateTime
import java.util.UUID

data class AccountEntity(
    val id: UUID?,
    val accountName: String,
    val accountNumber: String,
    val amount: String,
    val createdAt: OffsetDateTime? = null,
    val updatedAt: OffsetDateTime? = null
)
