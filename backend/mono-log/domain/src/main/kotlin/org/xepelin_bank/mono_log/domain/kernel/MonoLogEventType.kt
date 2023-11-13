package org.xepelin_bank.mono_log.domain.kernel

import org.xepelin_bank.common.extensions.PrimitiveVO
import org.xepelin_bank.common.extensions.message.constants.EventType

data class MonoLogEventType (
    private val id: EventType
) : PrimitiveVO<EventType>() {
    override fun value(): EventType = id
}
