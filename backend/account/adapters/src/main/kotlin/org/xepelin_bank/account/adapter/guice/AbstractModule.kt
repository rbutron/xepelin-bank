package org.xepelin_bank.account.adapter.guice

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import io.vertx.rxjava3.core.Vertx
import org.xepelin_bank.account.adapter.config.AccountConfig
import org.xepelin_bank.account.adapter.config.AccountConfigEnv
import org.xepelin_bank.infrastructure.flyway.config.FlywayMigration
import org.xepelin_bank.infrastructure.flyway.config.Migration
import org.xepelin_bank.infrastructure.flyway.config.mappers.DataBaseConfigMapper
import org.xepelin_bank.infrastructure.vertx.database.PGClient
import org.xepelin_bank.infrastructure.vertx.database.PGClientImpl
import org.xepelin_bank.infrastructure.vertx.database.mappers.CredentialDBMapper
import org.xepelin_bank.infrastructure.vertx.kafka.configure.KafkaConfig
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaConsumerClient
import org.xepelin_bank.infrastructure.vertx.kafka.configure.KafkaConfigEnv
import org.xepelin_bank.infrastructure.vertx.kafka.impl.KafkaConsumerClientImpl
import org.xepelin_bank.infrastructure.vertx.kafka.impl.KafkaProducerClientImpl

abstract class AbstractModule(private val vertx: Vertx) : AbstractModule() {

    private val accountConfig: AccountConfig = AccountConfigEnv()

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
    private fun getDataBaseConfigMapper(): DataBaseConfigMapper =
        DataBaseConfigMapper(
            this.accountConfig.getDBFull(),
            this.accountConfig.getDBUser(),
            this.accountConfig.getDBPassword()
        )

    @Provides
    private fun getKafkaConfig(): KafkaConfig = KafkaConfigEnv()

    @Provides
    private fun getCredentialDB(): CredentialDBMapper = CredentialDBMapper(
        this.accountConfig.getDBPort(),
        this.accountConfig.getDBHost(),
        this.accountConfig.getDBName(),
        this.accountConfig.getDBUser(),
        this.accountConfig.getDBPassword()
    )
}
