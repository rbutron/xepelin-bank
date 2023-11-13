package org.xepelin_bank.mono_log.adapter.retriever.listener

import io.reactivex.rxjava3.core.Completable

interface Listener {
    fun listen(): Completable
}
