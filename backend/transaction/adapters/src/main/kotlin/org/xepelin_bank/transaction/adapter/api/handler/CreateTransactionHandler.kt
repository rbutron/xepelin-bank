package org.xepelin_bank.transaction.adapter.api.handler

import io.reactivex.rxjava3.core.Completable
import io.vertx.core.Handler
import io.vertx.rxjava3.ext.web.RoutingContext
import org.xepelin_bank.common.exceptions.logger.LoggerDelegate
import org.xepelin_bank.common.extensions.SystemExtension.parseTo
import org.xepelin_bank.transaction.adapter.api.request.TransactionDTO

class CreateTransactionHandler (
    private val service: (RoutingContext, transactionDTO: TransactionDTO) -> Completable
) : Handler<RoutingContext> {

    private val logger by LoggerDelegate()
    override fun handle(routingContext: RoutingContext) {
        val transactionDTO: TransactionDTO = routingContext.body().asJsonObject().parseTo(TransactionDTO::class.java)
        service(routingContext, transactionDTO).subscribe({},
            { error ->
                logger.error(error.message, error)
                routingContext.fail(error)
            }
        )
    }
}
