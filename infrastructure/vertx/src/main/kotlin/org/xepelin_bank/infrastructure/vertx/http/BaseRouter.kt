package org.xepelin_bank.infrastructure.vertx.http

import io.vertx.rxjava3.ext.web.Router

interface BaseRouter {
    fun getPath(): String
    fun create(): Router
}
