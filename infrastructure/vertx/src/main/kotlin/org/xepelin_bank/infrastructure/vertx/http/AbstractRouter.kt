package org.xepelin_bank.infrastructure.vertx.http

import io.reactivex.rxjava3.core.Completable
import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.Json
import io.vertx.rxjava3.ext.web.RoutingContext

abstract class AbstractRouter : BaseRouter {
    
    companion object {
        const val CONTENT_TYPE_VALUE = "application/json; charset=utf-8"
    }
    
    fun completeResponseJson(ctx: RoutingContext, result: Any): Completable =
        ctx.response()
            .putHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .rxEnd(Json.encode(result))
}
