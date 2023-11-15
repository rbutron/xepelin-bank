package org.xepelin_bank.transaction.adapter.guice

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import io.vertx.rxjava3.core.Vertx
import org.xepelin_bank.infrastructure.flyway.config.FlywayMigration
import org.xepelin_bank.infrastructure.flyway.config.Migration
import org.xepelin_bank.infrastructure.flyway.config.mappers.DataBaseConfigMapper
import org.xepelin_bank.infrastructure.vertx.database.PGClient
import org.xepelin_bank.infrastructure.vertx.database.PGClientImpl
import org.xepelin_bank.infrastructure.vertx.database.mappers.CredentialDBMapper
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaConsumerClient
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import org.xepelin_bank.infrastructure.vertx.kafka.configure.KafkaConfig
import org.xepelin_bank.infrastructure.vertx.kafka.configure.KafkaConfigEnv
import org.xepelin_bank.infrastructure.vertx.kafka.impl.KafkaConsumerClientImpl
import org.xepelin_bank.infrastructure.vertx.kafka.impl.KafkaProducerClientImpl
import org.xepelin_bank.transaction.adapter.config.TransactionConfig
import org.xepelin_bank.transaction.adapter.config.TransactionConfigEnv

abstract class AbstractModule(private val vertx: Vertx) : AbstractModule() {

    private val config: TransactionConfig = TransactionConfigEnv()

    abstract fun routers()

    abstract fun factory()

    abstract fun repository()

    final override fun configure() {
        bind(Vertx::class.java).toInstance(vertx)

        bind(PGClient::class.java).to(PGClientImpl::class.java).`in`(Singleton::class.java)
        bind(Migration::class.java).to(FlywayMigration::class.java).`in`(Singleton::class.java)

        bind(KafkaProducerClient::class.java).to(KafkaProducerClientImpl::class.java).`in`(Singleton::class.java)
        bind(KafkaConsumerClient::class.java).to(KafkaConsumerClientImpl::class.java).`in`(Singleton::class.java)

        routers()
        repository()
        factory()
    }

    @Provides
    private fun getMigrationMapper(): DataBaseConfigMapper =
        DataBaseConfigMapper(this.config.getDBFull(), this.config.getDBUser(), this.config.getDBPassword())

    @Provides
    private fun getKafkaConfig(): KafkaConfig = KafkaConfigEnv()

    @Provides
    private fun credentialDB(): CredentialDBMapper = CredentialDBMapper(
        this.config.getDBPort(),
        this.config.getDBHost(),
        this.config.getDBName(),
        this.config.getDBUser(),
        this.config.getDBPassword()
    )
}
