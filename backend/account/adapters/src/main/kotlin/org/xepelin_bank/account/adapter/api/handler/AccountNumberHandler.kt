package org.xepelin_bank.account.adapter.api.handler

import io.reactivex.rxjava3.core.Completable
import io.vertx.core.Handler
import io.vertx.rxjava3.ext.web.RoutingContext
import org.xepelin_bank.account.adapter.api.dto.request.AccountIdDTO
import org.xepelin_bank.common.exceptions.logger.LoggerDelegate

class AccountNumberHandler(
    private val service: (RoutingContext, accountIdDTO: AccountIdDTO) -> Completable
) : Handler<RoutingContext> {

    private val logger by LoggerDelegate()

    override fun handle(routingContext: RoutingContext) {
        val accountIdDTO = AccountIdDTO(
            accountId = routingContext.pathParam("accountId")
        )
        service(routingContext, accountIdDTO).subscribe({},
            { error ->
                logger.error(error.message, error)
                routingContext.fail(error)
            }
        )
    }
}
