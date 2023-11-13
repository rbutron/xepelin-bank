package org.xepelin_bank.account.adapter.api.handler

import io.reactivex.rxjava3.core.Completable
import io.vertx.core.Handler
import io.vertx.rxjava3.ext.web.RoutingContext
import org.xepelin_bank.account.adapter.api.dto.request.AccountNumberDTO
import org.xepelin_bank.common.exceptions.logger.LoggerDelegate

class AccountNumberHandler(
    private val service: (RoutingContext, accountNumberDTO: AccountNumberDTO) -> Completable
) : Handler<RoutingContext> {

    private val logger by LoggerDelegate()

    override fun handle(routingContext: RoutingContext) {
        val accountNumberDTO = AccountNumberDTO(
            accountNumber = routingContext.pathParam("accountNumber")
        )
        service(routingContext, accountNumberDTO).subscribe({},
            { error ->
                logger.error(error.message, error)
                routingContext.fail(error)
            }
        )
    }
}
