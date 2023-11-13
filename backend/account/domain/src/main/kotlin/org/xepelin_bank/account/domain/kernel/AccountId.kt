package org.xepelin_bank.account.domain.kernel

import org.xepelin_bank.common.extensions.PrimitiveVO
import java.util.UUID

data class AccountId(
    private val id: UUID
) : PrimitiveVO<UUID>() {
    override fun value(): UUID = id
}
