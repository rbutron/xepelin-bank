package org.xepelin_bank.account.adapter.retriever

import io.reactivex.rxjava3.core.Completable
import io.vertx.core.json.JsonObject
import org.xepelin_bank.account.domain.command.CreateAccountCommand
import org.xepelin_bank.account.domain.event.AccountCreatedEvent
import org.xepelin_bank.account.domain.event.TransactionBalanceCreatedEvent
import org.xepelin_bank.account.domain.kernel.Account
import org.xepelin_bank.account.domain.kernel.AccountId
import org.xepelin_bank.account.domain.kernel.AccountName
import org.xepelin_bank.account.domain.kernel.AccountNumber
import org.xepelin_bank.account.domain.kernel.AccountAmount
import org.xepelin_bank.account.domain.retriever.AccountSubscriber
import org.xepelin_bank.account.domain.use_case.AccountUseCase
import org.xepelin_bank.common.extensions.message.constants.BrandType
import org.xepelin_bank.common.extensions.message.constants.TransactionType

class AccountSubscriberImpl(private val accountUseCase: AccountUseCase) : AccountSubscriber {
    override fun consumer(accountId: AccountId, commandOrEvent: Any): Completable =
        (commandOrEvent as JsonObject).let { ce ->
            when (BrandType.valueOf(ce.getString("brand"))) {
                BrandType.ACCOUNT -> {
                    val createAccountCommand = ce.mapTo(CreateAccountCommand::class.java)
                    this.accountUseCase.createAccount(
                        Account(
                            id = accountId,
                            name = AccountName(createAccountCommand.accountName),
                            accountNumber = AccountNumber(createAccountCommand.accountNumber),
                            amount = AccountAmount(createAccountCommand.amount),
                        )
                    )
                }

                BrandType.NEW_ACCOUNT_TRANSACTION -> {
                    val accountCreatedEvent = ce.mapTo(AccountCreatedEvent::class.java)
                    this.accountUseCase.createAccountTransaction(
                        accountId,
                        accountCreatedEvent
                    )
                }

                BrandType.EXISTING_ACCOUNT_TRANSACTION -> {
                    val accountCreatedEvent = ce.mapTo(TransactionBalanceCreatedEvent::class.java)
                    this.accountUseCase.updateAmountAccount(
                        accountId,
                        AccountAmount(accountCreatedEvent.amount),
                        org.xepelin_bank.account.domain.kernel.transaction.TransactionType(
                            TransactionType.valueOf(
                                accountCreatedEvent.transactionType
                            )
                        )
                    )
                }
            }
        }
}
