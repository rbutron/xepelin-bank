package org.xepelin_bank.account.adapter.api.handler

import io.reactivex.rxjava3.core.Completable
import io.vertx.core.Handler
import io.vertx.rxjava3.ext.web.RoutingContext
import org.xepelin_bank.account.adapter.api.dto.request.CreateAccountDTO
import org.xepelin_bank.common.exceptions.logger.LoggerDelegate
import org.xepelin_bank.common.extensions.SystemExtension.parseTo

class CreateAccountHandler(
    private val service: (RoutingContext, createAccountDTO: CreateAccountDTO) -> Completable
) : Handler<RoutingContext> {

    private val logger by LoggerDelegate()

    override fun handle(routingContext: RoutingContext) {
        val createAccountDTO: CreateAccountDTO = routingContext.body().asJsonObject().parseTo(CreateAccountDTO::class.java)
        service(routingContext, createAccountDTO).subscribe({},
        { error ->
            logger.error(error.message, error)
            routingContext.fail(error)
        }
        )
    }
}
