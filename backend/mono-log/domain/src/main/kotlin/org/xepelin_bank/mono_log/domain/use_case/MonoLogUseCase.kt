package org.xepelin_bank.mono_log.domain.use_case

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.mono_log.domain.command.CreateAccountCommand
import org.xepelin_bank.mono_log.domain.kernel.MonoLog
import org.xepelin_bank.mono_log.domain.kernel.account.AccountId

interface MonoLogUseCase {
    fun createMonoLog(monoLog: MonoLog): Completable
    fun publishAccountCommand(accountId: AccountId, createAccountCommand: CreateAccountCommand): Completable
}
