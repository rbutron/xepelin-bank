package org.xepelin_bank.account.adapter.retriever.listener

import io.reactivex.rxjava3.core.Completable

interface Listener {
    fun listen(): Completable
}
