package org.xepelin_bank.transaction.app

import com.google.inject.Guice
import io.vertx.rxjava3.core.Vertx
import org.xepelin_bank.transaction.app.configure.MainTransactionVerticle
import org.xepelin_bank.common.exceptions.logger.LoggerDelegate
import org.xepelin_bank.infrastructure.vertx.http.HTTPVerticle.Companion.jsonDeserialize
import org.xepelin_bank.transaction.adapter.guice.ModuleCreator
import org.xepelin_bank.transaction.adapter.retriever.listener.TransactionListener

object TransactionAPI {
    private val logger by LoggerDelegate()
    
    @JvmStatic
    fun main(args: Array<String>) {
        val vertx = Vertx.vertx()
        jsonDeserialize()
        val injector = Guice.createInjector(ModuleCreator(vertx))
        val main = injector.getInstance(MainTransactionVerticle::class.java)
        val transactionListener = injector.getInstance(TransactionListener::class.java)

        vertx.rxDeployVerticle(main).doFinally {
            Runtime.getRuntime().addShutdownHook(Thread {
                logger.warn("Closing application")
                vertx.rxClose().subscribe()
            })
        }.subscribe(::logInfo, ::logError)

        transactionListener.listen().subscribe({}, ::logError)
    }

    private fun logError(throwable: Throwable) {
        logger.error("Error handling request:", throwable)
    }

    private fun logInfo(trace: Any) {
        logger.info("{}", trace)
    }
}
