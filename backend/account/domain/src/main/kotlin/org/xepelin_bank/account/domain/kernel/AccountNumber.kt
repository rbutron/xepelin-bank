package org.xepelin_bank.account.domain.kernel

import org.xepelin_bank.common.extensions.PrimitiveVO

data class AccountNumber(
    private val value: String
) : PrimitiveVO<String>() {
    override fun value(): String = value
}
