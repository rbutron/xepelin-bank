package org.xepelin_bank.mono_log.adapter.repository.entity

import io.vertx.core.json.JsonObject
import java.time.OffsetDateTime
import java.util.UUID

data class MonoLogEntity(
    val id: UUID? = null,
    val businessId: UUID,
    val eventType: String,
    val brand: String,
    val data: JsonObject,
    val createdAt: OffsetDateTime? = null,
    val updatedAt: OffsetDateTime? = null
)
