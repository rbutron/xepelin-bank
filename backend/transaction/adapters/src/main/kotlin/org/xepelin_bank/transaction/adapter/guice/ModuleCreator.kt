package org.xepelin_bank.transaction.adapter.guice

import com.google.inject.Singleton
import com.google.inject.multibindings.Multibinder
import io.vertx.rxjava3.core.Vertx
import org.xepelin_bank.transaction.adapter.api.TransactionRoute
import org.xepelin_bank.infrastructure.vertx.http.BaseRouter
import org.xepelin_bank.transaction.adapter.repository.TransactionRepository
import org.xepelin_bank.transaction.adapter.repository.TransactionRepositoryImpl
import org.xepelin_bank.transaction.adapter.service.Transaction
import org.xepelin_bank.transaction.domain.use_case.TransactionUseCase

class ModuleCreator(vertx: Vertx) : AbstractModule(vertx) {
    override fun routers() {
        val routerBinder = Multibinder.newSetBinder(binder(), BaseRouter::class.java)
        routerBinder.addBinding().to(TransactionRoute::class.java)
    }
    
    override fun factory() {
        bind(TransactionUseCase::class.java).to(Transaction::class.java).`in`(Singleton::class.java)
    }
    
    override fun repository() {
        bind(TransactionRepository::class.java).to(TransactionRepositoryImpl::class.java).`in`(Singleton::class.java)
    }
}
