package org.xepelin_bank.mono_log.domain.dispatcher

import io.reactivex.rxjava3.core.Completable
import org.xepelin_bank.mono_log.domain.command.CreateAccountCommand
import java.util.UUID

interface PublishAccountToCreate {
    fun publish(key: UUID?, value: CreateAccountCommand): Completable
}
