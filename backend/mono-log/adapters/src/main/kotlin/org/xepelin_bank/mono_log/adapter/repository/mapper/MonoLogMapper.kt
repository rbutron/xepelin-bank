package org.xepelin_bank.mono_log.adapter.repository.mapper

import io.vertx.rxjava3.sqlclient.Row
import org.xepelin_bank.mono_log.adapter.repository.entity.MonoLogEntity

object MonoLogMapper{
    fun buildMonoLogEntity(row: Row): MonoLogEntity =
        MonoLogEntity(
            id = row.getUUID("id"),
            businessId = row.getUUID("business_id"),
            eventType = row.getString("event_type"),
            brand = row.getString("brand"),
            data = row.getJsonObject("data"),
            createdAt = row.getOffsetDateTime("created_at"),
            updatedAt = row.getOffsetDateTime("updated_at"),
        )
}
