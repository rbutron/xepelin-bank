package org.xepelin_bank.mono_log.adapter.retriever

import io.reactivex.rxjava3.core.Completable
import io.vertx.core.json.JsonObject
import org.xepelin_bank.common.extensions.message.constants.BrandType
import org.xepelin_bank.common.extensions.message.constants.EventType
import org.xepelin_bank.mono_log.domain.kernel.MonoLog
import org.xepelin_bank.mono_log.domain.kernel.MonoLogBrandType
import org.xepelin_bank.mono_log.domain.kernel.MonoLogBusinessId
import org.xepelin_bank.mono_log.domain.kernel.MonoLogEventType
import org.xepelin_bank.mono_log.domain.kernel.account.AccountId
import org.xepelin_bank.mono_log.domain.retriever.MonoLogSubscriber
import org.xepelin_bank.mono_log.domain.use_case.MonoLogUseCase

class MonoLogSubscriberImpl(private val monoLogUseCase: MonoLogUseCase) : MonoLogSubscriber {
    override fun consumer(accountId: AccountId, commandOrEvent: Any): Completable =
        (commandOrEvent as JsonObject).getString("brand").let { brand ->
            this.monoLogUseCase.createMonoLog(
                MonoLog(
                    businessId = MonoLogBusinessId(accountId.value()),
                    eventType = MonoLogEventType(EventType.COMPLETE_EVENT_TYPE),
                    brand = MonoLogBrandType(BrandType.valueOf(brand)),
                    data = commandOrEvent
                )
            )
        }
}
