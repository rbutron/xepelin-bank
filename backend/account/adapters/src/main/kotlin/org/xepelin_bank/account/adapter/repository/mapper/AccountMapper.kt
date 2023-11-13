package org.xepelin_bank.account.adapter.repository.mapper

import io.vertx.rxjava3.sqlclient.Row
import org.xepelin_bank.account.adapter.repository.entity.AccountEntity

object AccountMapper {
    fun buildAccountEntity(row: Row): AccountEntity =
        AccountEntity(
            id = row.getUUID("id"),
            accountName = row.getString("account_name"),
            accountNumber = row.getString("account_number"),
            amount = row.getString("amount"),
            createdAt = row.getOffsetDateTime("created_at"),
            updatedAt = row.getOffsetDateTime("updated_at"),
        )
}
