package org.xepelin_bank.mono_log.app

import com.google.inject.Guice
import io.vertx.rxjava3.core.Vertx
import org.xepelin_bank.mono_log.adapter.guice.ModuleCreator
import org.xepelin_bank.mono_log.app.configure.MainMonoLogVerticle
import org.xepelin_bank.common.exceptions.logger.LoggerDelegate
import org.xepelin_bank.infrastructure.vertx.http.HTTPVerticle.Companion.jsonDeserialize
import org.xepelin_bank.mono_log.adapter.retriever.listener.MonoLogListener

object MonoLogAPI {
    private val logger by LoggerDelegate()
    
    @JvmStatic
    fun main(args: Array<String>) {
        val vertx = Vertx.vertx()
        jsonDeserialize()
        val injector = Guice.createInjector(ModuleCreator(vertx))
        val main = injector.getInstance(MainMonoLogVerticle::class.java)
        val monoLogListener = injector.getInstance(MonoLogListener::class.java)

        vertx.rxDeployVerticle(main).subscribe(MonoLogAPI::logInfo, MonoLogAPI::logError)

        monoLogListener.listen().subscribe()

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
