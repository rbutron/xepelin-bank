package org.xepelin_bank.mono_log.adapter.repository

import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.mono_log.adapter.repository.entity.MonoLogEntity

interface MonoLogRepository {
    fun save(monoLogEntity: MonoLogEntity): Single<MonoLogEntity>
}
