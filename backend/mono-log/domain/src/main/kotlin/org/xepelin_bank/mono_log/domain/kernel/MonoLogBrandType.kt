package org.xepelin_bank.mono_log.domain.kernel

import org.xepelin_bank.common.extensions.PrimitiveVO
import org.xepelin_bank.common.extensions.message.constants.BrandType

data class MonoLogBrandType (
    private val id: BrandType
) : PrimitiveVO<BrandType>() {
    override fun value(): BrandType = id
}
