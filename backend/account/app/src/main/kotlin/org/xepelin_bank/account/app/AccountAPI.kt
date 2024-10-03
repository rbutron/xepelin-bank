package org.xepelin_bank.account.app

import com.google.inject.Guice
import io.vertx.rxjava3.core.Vertx
import org.xepelin_bank.account.adapter.guice.ModuleCreator
import org.xepelin_bank.account.adapter.retriever.listener.AccountListener
import org.xepelin_bank.account.app.configure.MainAccountVerticle
import org.xepelin_bank.common.exceptions.logger.LoggerDelegate
import org.xepelin_bank.infrastructure.vertx.http.HTTPVerticle.Companion.jsonDeserialize

object AccountAPI {
    private val logger by LoggerDelegate()
    
    @JvmStatic
    fun main(args: Array<String>) {
        val vertx = Vertx.vertx()
        jsonDeserialize()
        val injector = Guice.createInjector(ModuleCreator(vertx))
        val main = injector.getInstance(MainAccountVerticle::class.java)
        val accountListener = injector.getInstance(AccountListener::class.java)

        vertx.rxDeployVerticle(main).doFinally {
            Runtime.getRuntime().addShutdownHook(Thread {
                logger.warn("Closing application")
                vertx.rxClose().subscribe()
            })
        }.subscribe(::logInfo, ::logError)

        accountListener.listen().subscribe({}, ::logError)
    }

    private fun logError(throwable: Throwable) {
        logger.error("Error handling request:", throwable)
    }

    private fun logInfo(trace: Any) {
        logger.info("{}", trace)
    }
}
