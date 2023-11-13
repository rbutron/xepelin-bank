package org.xepelin_bank.infrastructure.flyway.config

import com.google.inject.Inject
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.output.MigrateResult
import org.xepelin_bank.infrastructure.flyway.config.mappers.DataBaseConfigMapper

class FlywayMigration @Inject constructor(private val dataBaseMapper: DataBaseConfigMapper) : Migration {
    override fun migrateDB(): MigrateResult {
        val flyway = Flyway.configure()
            .dataSource(dataBaseMapper.url, dataBaseMapper.user, dataBaseMapper.password)
            .mixed(true)
            .load()
        return flyway.migrate()
    }
}
