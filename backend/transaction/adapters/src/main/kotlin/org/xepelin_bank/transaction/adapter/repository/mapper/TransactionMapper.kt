package org.xepelin_bank.transaction.adapter.repository.mapper

import io.vertx.rxjava3.sqlclient.Row
import org.xepelin_bank.transaction.adapter.repository.entity.TransactionEntity

object TransactionMapper {
    fun buildTransactionEntity(row: Row): TransactionEntity =
        TransactionEntity(
            id = row.getUUID("id"),
            transactionType = row.getString("transaction_type"),
            accountId = row.getUUID("account_id"),
            amount = row.getString("amount"),
            createdAt = row.getOffsetDateTime("created_at"),
            updatedAt = row.getOffsetDateTime("updated_at"),
        )
}
