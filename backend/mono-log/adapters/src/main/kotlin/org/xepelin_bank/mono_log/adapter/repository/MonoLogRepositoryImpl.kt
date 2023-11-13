package org.xepelin_bank.mono_log.adapter.repository

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.infrastructure.vertx.database.PGClientImpl
import org.xepelin_bank.mono_log.adapter.repository.entity.MonoLogEntity
import org.xepelin_bank.mono_log.adapter.repository.mapper.MonoLogMapper.buildMonoLogEntity

class MonoLogRepositoryImpl @Inject constructor(private val pg: PGClientImpl):MonoLogRepository {

    override fun save(monoLogEntity: MonoLogEntity): Single<MonoLogEntity> =
        pg.saveOrUpdate(
            """
                INSERT INTO mono_log (business_id,
                        event_type,
                        brand,
                        data)
                VALUES ($1, $2, $3, $4)
                RETURNING id, business_id, event_type, brand, data, created_at, updated_at
        """.trimIndent(),
            listOf(
                monoLogEntity.businessId,
                monoLogEntity.eventType,
                monoLogEntity.brand,
                monoLogEntity.data
            )
        ).map(::buildMonoLogEntity)
}
