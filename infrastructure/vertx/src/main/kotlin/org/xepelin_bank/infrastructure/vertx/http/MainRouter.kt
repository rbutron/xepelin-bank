package org.xepelin_bank.infrastructure.vertx.http

import com.google.inject.Inject
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.json.Json
import io.vertx.rxjava3.core.Vertx
import io.vertx.rxjava3.ext.web.Router
import io.vertx.rxjava3.ext.web.RoutingContext
import io.vertx.rxjava3.ext.web.handler.BodyHandler
import io.vertx.rxjava3.ext.web.handler.LoggerHandler
import org.xepelin_bank.common.exceptions.ErrorHandler
import org.xepelin_bank.common.extensions.message.success.Message.PROCESS_SUCCESS
import org.xepelin_bank.infrastructure.vertx.http.mapper.StatusResponse

class MainRouter @Inject constructor(private val vertx: Vertx, private val subRouters: Set<BaseRouter>) :
    AbstractRouter() {
    override fun getPath(): String = ""
    
    override fun create(): Router {
        val router = Router.router(vertx)
        router.apply {
            routeWithRegex("(?!/health-check).*").handler(LoggerHandler.create())
            route().handler(BodyHandler.create())
            router.route("/*").failureHandler(ErrorHandler())
            
            subRouters.forEach { route(it.getPath().plus("/*")).subRouter(it.create()) }
        }
        router.get("/health-check") {
            this.response().rxEnd(Json.encode(StatusResponse(PROCESS_SUCCESS, HttpResponseStatus.OK.code(), "OK")))
                .subscribe()
        }
        return router
    }
    
    private fun Router.get(path: String, ctx: RoutingContext.() -> Unit) = get(path)
        .handler { it.ctx() }
}
