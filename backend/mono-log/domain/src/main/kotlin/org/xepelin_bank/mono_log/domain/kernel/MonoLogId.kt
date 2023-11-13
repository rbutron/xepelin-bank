package org.xepelin_bank.mono_log.domain.kernel

import org.xepelin_bank.common.extensions.PrimitiveVO
import java.util.UUID

data class MonoLogId(
    private val id: UUID
) : PrimitiveVO<UUID>() {
    override fun value(): UUID = id
}
