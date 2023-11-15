package org.xepelin_bank.transaction.adapter.repository.entity

import java.time.OffsetDateTime
import java.util.UUID

data class TransactionEntity(
    val id: UUID? = null,
    val transactionType: String,
    val accountId: UUID,
    val amount: String,
    val createdAt: OffsetDateTime? = null,
    val updatedAt: OffsetDateTime? = null
)
