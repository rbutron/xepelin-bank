package org.xepelin_bank.transaction.domain.kernel.account

import org.xepelin_bank.common.extensions.PrimitiveVO

data class AccountAmount(
    private val value: String
) : PrimitiveVO<String>() {
    override fun value(): String = value
}
