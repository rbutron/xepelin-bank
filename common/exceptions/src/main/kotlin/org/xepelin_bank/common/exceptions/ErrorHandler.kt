package org.xepelin_bank.common.exceptions

import io.netty.handler.codec.http.HttpResponseStatus
import io.reactivex.rxjava3.exceptions.CompositeException
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.rxjava3.ext.web.RoutingContext
import org.xepelin_bank.common.exceptions.logger.LoggerDelegate

class ErrorHandler : Handler<RoutingContext> {
    
    private val logger by LoggerDelegate()
    
    companion object {
        private const val DEFAULT_CAUSE = "internal.error"
    }
    
    override fun handle(event: RoutingContext) {
        val response = event.response()
        val failure = event.failure()
        
        val error = when {
            RequestException::class.java.isAssignableFrom(failure::class.java) ->
                handleErrorRequestException(failure as RequestException)
            else -> handleException(event)
        }
        
        when {
            (event.failure() is RequestException) ->
                logger.warn(
                    "Bad request processing request method[${event.request().method()}] path [${
                        event.request().path()
                    }] message [${error.cause}]"
                )
            else ->
                logger.error(
                    "Error processing request - ${event.request().method()} ${
                        event.request().path()
                    }- ${event.failure().message}"
                )
        }
        response.setStatusCode(error.status).putHeader("content-type", "application/json; charset=utf-8")
            .rxEnd(Json.encode(error))
            .subscribe()
    }
    
    private fun handleErrorRequestException(e: RequestException): ErrorObject =
        ErrorObject(message = e.message, cause = e.cause, status = e.status)
    
    private fun handleException(e: RoutingContext): ErrorObject {
        val failure = e.failure()
        val status = HttpResponseStatus.INTERNAL_SERVER_ERROR.code()
        
        return if (failure !is CompositeException)
            getError(failure)
        else {
            val messageBuilder = JsonArray()
            val causeBuilder = JsonArray()
            
            failure.exceptions.forEach {
                messageBuilder.add(JsonObject().put("error", it.message))
                causeBuilder.add(JsonObject().put("cause", "internal.error"))
            }
            ErrorObject(message = messageBuilder, cause = causeBuilder, status = status)
        }
    }
    
    private fun getError(failure: Throwable): ErrorObject =
        when (failure) {
            is BusinessException -> ErrorObject(
                message = failure.message, cause = DEFAULT_CAUSE,
                status = HttpResponseStatus.BAD_REQUEST.code()
            )
            is NotFoundException -> ErrorObject(
                message = failure.message, cause = DEFAULT_CAUSE,
                status = HttpResponseStatus.NOT_FOUND.code()
            )
            else -> ErrorObject(
                message = failure.message, cause = DEFAULT_CAUSE,
                status = HttpResponseStatus.INTERNAL_SERVER_ERROR.code()
            )
        }
}
