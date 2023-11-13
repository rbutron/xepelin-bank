package org.xepelin_bank.mono_log.domain.kernel

data class MonoLog(
    val id: MonoLogId? = null,
    val businessId: MonoLogBusinessId,
    val eventType: MonoLogEventType,
    val brand: MonoLogBrandType,
    val data: Any,
)
