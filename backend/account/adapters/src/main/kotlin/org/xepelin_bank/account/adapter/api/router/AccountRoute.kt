package org.xepelin_bank.account.adapter.api.router

import com.google.inject.Inject
import io.netty.handler.codec.http.HttpResponseStatus
import io.reactivex.rxjava3.core.Completable
import io.vertx.rxjava3.core.Vertx
import io.vertx.rxjava3.ext.web.Router
import io.vertx.rxjava3.ext.web.RoutingContext
import org.xepelin_bank.account.adapter.api.dto.request.AccountNumberDTO
import org.xepelin_bank.account.adapter.api.dto.request.CreateAccountDTO
import org.xepelin_bank.account.adapter.api.dto.response.AccountIdGeneratedDTO
import org.xepelin_bank.account.adapter.api.dto.response.BalanceAmountDTO
import org.xepelin_bank.account.adapter.api.handler.AccountNumberHandler
import org.xepelin_bank.account.adapter.api.handler.CreateAccountHandler
import org.xepelin_bank.account.domain.kernel.*
import org.xepelin_bank.account.domain.use_case.AccountUseCase
import org.xepelin_bank.common.exceptions.NotFoundException
import org.xepelin_bank.common.extensions.message.constants.BrandType
import org.xepelin_bank.common.extensions.message.success.Message
import org.xepelin_bank.infrastructure.vertx.http.AbstractRouter
import org.xepelin_bank.infrastructure.vertx.http.mapper.StatusResponse
import java.util.UUID

class AccountRoute @Inject constructor(
    private val vertx: Vertx,
    private val accountUseCase: AccountUseCase,
) : AbstractRouter() {
    override fun getPath(): String = "/api"

    override fun create(): Router = Router.router(vertx).apply {
        post("/v1/account")
            .handler(CreateAccountHandler(::createAccount))
        get("/v1/account/:accountNumber/balance")
            .handler(AccountNumberHandler(::getAccount))
    }

    private fun createAccount(routingContext: RoutingContext, createAccountDTO: CreateAccountDTO): Completable =
        UUID.randomUUID().let { key ->
            accountUseCase.publishAccountCommand(
                AccountId(key),
                Account(
                    name = AccountName(createAccountDTO.accountName),
                    accountNumber = AccountNumber(createAccountDTO.accountNumber),
                    amount = createAccountDTO.amount?.let(::AccountAmount) ?: AccountAmount("0")
                )
            )
                .andThen(
                    completeResponseJson(
                        routingContext, StatusResponse(
                            Message.PROCESS_SUCCESS, HttpResponseStatus.OK.code(), AccountIdGeneratedDTO(
                                accountId = key.toString()
                            )
                        )
                    )
                )
        }

    private fun getAccount(routingContext: RoutingContext, accountNumberDTO: AccountNumberDTO): Completable =
        accountUseCase.getAccount(
            AccountNumber(
                accountNumberDTO.accountNumber
            )
        ).concatMapCompletable { accountAmount ->
            completeResponseJson(
                routingContext, StatusResponse(
                    Message.PROCESS_SUCCESS, HttpResponseStatus.OK.code(), BalanceAmountDTO(
                        balance = accountAmount.value()
                    )
                )
            )
        }.onErrorResumeNext {
            Completable.error(NotFoundException("account not found"))
        }
}
