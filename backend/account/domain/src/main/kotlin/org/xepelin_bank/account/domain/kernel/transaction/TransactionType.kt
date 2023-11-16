package org.xepelin_bank.account.domain.kernel.transaction

import org.xepelin_bank.common.extensions.PrimitiveVO
import org.xepelin_bank.common.extensions.message.constants.TransactionType

data class TransactionType (
    private val id: TransactionType
) : PrimitiveVO<TransactionType>() {
    override fun value(): TransactionType = id
}
