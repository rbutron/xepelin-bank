package org.xepelin_bank.transaction.domain.kernel.account

import org.xepelin_bank.common.extensions.PrimitiveVO
import java.util.UUID

data class AccountId(
    private val id: UUID
) : PrimitiveVO<UUID>() {
    override fun value(): UUID = id
}
