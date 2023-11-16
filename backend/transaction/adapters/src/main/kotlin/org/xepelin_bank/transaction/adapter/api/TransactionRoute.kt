package org.xepelin_bank.transaction.adapter.api

import com.google.inject.Inject
import io.netty.handler.codec.http.HttpResponseStatus
import io.reactivex.rxjava3.core.Completable
import io.vertx.rxjava3.core.Vertx
import io.vertx.rxjava3.ext.web.Router
import io.vertx.rxjava3.ext.web.RoutingContext
import org.xepelin_bank.common.extensions.message.success.Message
import org.xepelin_bank.infrastructure.vertx.http.AbstractRouter
import org.xepelin_bank.infrastructure.vertx.http.mapper.StatusResponse
import org.xepelin_bank.transaction.adapter.api.handler.CreateTransactionHandler
import org.xepelin_bank.transaction.adapter.api.request.TransactionDTO
import org.xepelin_bank.transaction.domain.kernel.Transaction
import org.xepelin_bank.transaction.domain.kernel.TransactionType
import org.xepelin_bank.transaction.domain.kernel.account.AccountAmount
import org.xepelin_bank.transaction.domain.kernel.account.AccountId
import org.xepelin_bank.transaction.domain.use_case.TransactionUseCase
import java.util.UUID

class TransactionRoute @Inject constructor(
    private val vertx: Vertx,
    private val transactionUseCase: TransactionUseCase
) : AbstractRouter() {
    override fun getPath(): String = "/api"

    override fun create(): Router = Router.router(vertx).apply {
        post("/v1/create")
            .handler(CreateTransactionHandler(::createAccount))
    }

    private fun createAccount(routingContext: RoutingContext, transactionDTO: TransactionDTO): Completable =
        this.transactionUseCase.publishTransactionExistingAccountCommand(
            Transaction(
                amount = AccountAmount(transactionDTO.amount),
                transactionType = TransactionType(
                    org.xepelin_bank.common.extensions.message.constants.TransactionType.valueOf(
                        transactionDTO.transactionType
                    )
                ),
                accountId = AccountId(UUID.fromString(transactionDTO.accountId))
            )
        )
            .andThen(
                completeResponseJson(
                    routingContext, StatusResponse(
                        Message.PROCESS_SUCCESS, HttpResponseStatus.OK.code(), mapOf<String, String>()
                    )
                )
            )
}
