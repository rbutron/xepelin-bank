package org.xepelin_bank.mono_log.domain.kernel

import org.xepelin_bank.common.extensions.PrimitiveVO
import java.util.UUID

class MonoLogBusinessId(
    private val id: UUID
) : PrimitiveVO<UUID>() {
    override fun value(): UUID = id
}