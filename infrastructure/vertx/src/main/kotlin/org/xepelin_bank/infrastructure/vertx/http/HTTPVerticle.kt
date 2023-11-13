package org.xepelin_bank.infrastructure.vertx.http

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.inject.Inject
import io.reactivex.rxjava3.core.Completable
import io.vertx.core.Promise
import io.vertx.core.http.HttpHeaders
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.json.jackson.DatabindCodec
import io.vertx.rxjava3.CompletableHelper
import io.vertx.rxjava3.core.AbstractVerticle
import io.vertx.rxjava3.ext.web.Router
import io.vertx.rxjava3.ext.web.handler.BodyHandler
import io.vertx.rxjava3.ext.web.handler.CorsHandler
import org.xepelin_bank.common.exceptions.logger.LoggerDelegate
import org.xepelin_bank.infrastructure.flyway.config.Migration

abstract class HTTPVerticle @Inject constructor(
    private val main: MainRouter,
    private val migration: Migration,
) :
    AbstractVerticle() {

    companion object {
        fun jsonDeserialize() {
            DatabindCodec.mapper().apply {
                registerModule(JavaTimeModule())
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                registerModule(
                    KotlinModule.Builder().withReflectionCacheSize(512)
                        .configure(KotlinFeature.NullToEmptyCollection, false)
                        .configure(KotlinFeature.NullToEmptyMap, false)
                        .configure(KotlinFeature.NullIsSameAsDefault, false)
                        .configure(KotlinFeature.SingletonSupport, true)
                        .configure(KotlinFeature.StrictNullChecks, false)
                        .build()
                )
            }
        }
    }

    private val logger by LoggerDelegate()

    abstract fun port(): Int
    abstract fun path(): String

    override fun start(startFuture: Promise<Void>) {
        Completable.defer {
            migration.migrateDB()
            Completable.complete()
        }.andThen(startServer())
            .doOnError { vertx.rxClose().subscribe() }
            .subscribe(CompletableHelper.toObserver(startFuture))
    }

    private fun startServer(): Completable {
        val router = Router.router(vertx)
        router.route().handler(corsHandler())
        router.route().handler(BodyHandler.create())
        router.route(path().plus("/*")).subRouter(main.create())

        val server = vertx.createHttpServer(options()).requestHandler(router)

        val port = config().getInteger("http.port", port())

        return Completable.fromSingle(server.rxListen(port))
            .doOnError { error ->
                logger.error("Server error: ", error)
                server.rxClose().subscribe()
            }
            .doOnSubscribe { logger.info("server is running at port $port") }
    }

    private fun corsHandler() = CorsHandler.create()
        .allowedMethod(HttpMethod.GET)
        .allowedMethod(HttpMethod.POST)
        .allowedMethod(HttpMethod.PUT)
        .allowedMethod(HttpMethod.DELETE)
        .allowedMethod(HttpMethod.OPTIONS)
        .allowedHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN.toString())
        .allowedHeader(HttpHeaders.AUTHORIZATION.toString())
        .allowedHeader(HttpHeaders.ORIGIN.toString())
        .allowedHeader(HttpHeaders.CONTENT_TYPE.toString())
        .allowCredentials(true)

    private fun options(): HttpServerOptions =
        HttpServerOptions()
            .setCompressionSupported(true)
            .setLogActivity(true)
}
