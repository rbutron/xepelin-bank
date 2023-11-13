package org.xepelin_bank.transaction.adapter.api

import com.google.inject.Inject
import io.vertx.rxjava3.core.Vertx
import io.vertx.rxjava3.ext.web.Router
import org.xepelin_bank.infrastructure.vertx.http.AbstractRouter

class TransactionRoute @Inject constructor(
    private val vertx: Vertx,
) : AbstractRouter() {
    override fun getPath(): String = "/api"

    override fun create(): Router = Router.router(vertx).apply {
        get("/test")
    }
}