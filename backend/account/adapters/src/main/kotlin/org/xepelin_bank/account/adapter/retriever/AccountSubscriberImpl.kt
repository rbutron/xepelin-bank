package org.xepelin_bank.account.adapter.retriever

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.account.domain.command.CreateAccountCommand
import org.xepelin_bank.account.domain.kernel.*
import org.xepelin_bank.account.domain.retriever.AccountSubscriber
import org.xepelin_bank.account.domain.use_case.AccountUseCase

class AccountSubscriberImpl(private val accountUseCase: AccountUseCase) : AccountSubscriber {
    override fun consumer(accountId: AccountId, command: CreateAccountCommand): Completable =
        accountUseCase.createAccount(
            Account(
                id = accountId,
                name = AccountName(command.accountName),
                accountNumber = AccountNumber(command.accountNumber),
                amount = AccountAmount(command.amount),
            )
        )
}
