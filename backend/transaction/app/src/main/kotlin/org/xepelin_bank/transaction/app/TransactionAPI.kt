package org.xepelin_bank.transaction.app

import com.google.inject.Guice
import io.vertx.rxjava3.core.Vertx
import org.xepelin_bank.transaction.app.configure.MainTransactionVerticle
import org.xepelin_bank.common.exceptions.logger.LoggerDelegate
import org.xepelin_bank.infrastructure.vertx.http.HTTPVerticle.Companion.jsonDeserialize
import org.xepelin_bank.transaction.adapter.guice.ModuleCreator

object TransactionAPI {
    private val logger by LoggerDelegate()
    
    @JvmStatic
    fun main(args: Array<String>) {
        val vertx = Vertx.vertx()
        jsonDeserialize()
        val injector = Guice.createInjector(ModuleCreator(vertx))
        val main = injector.getInstance(MainTransactionVerticle::class.java)
        vertx.rxDeployVerticle(main).subscribe(TransactionAPI::logInfo, TransactionAPI::logError)
        Runtime.getRuntime().addShutdownHook(Thread {
            logger.info("Closing application")
            vertx.rxClose().subscribe()
        })
    }

    private fun logError(throwable: Throwable) {
        logger.error("Error handling request:", throwable)
    }

    private fun logInfo(trace: Any) {
        logger.info("{}", trace)
    }
}
