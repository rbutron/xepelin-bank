package org.xepelin_bank.account.adapter.guice

import com.google.inject.Singleton
import com.google.inject.multibindings.Multibinder
import io.vertx.rxjava3.core.Vertx
import org.xepelin_bank.account.adapter.api.router.AccountRoute
import org.xepelin_bank.account.adapter.repository.AccountRepository
import org.xepelin_bank.account.adapter.repository.AccountRepositoryImpl
import org.xepelin_bank.account.adapter.service.Account
import org.xepelin_bank.account.domain.use_case.AccountUseCase
import org.xepelin_bank.infrastructure.vertx.http.BaseRouter

class ModuleCreator(vertx: Vertx) : AbstractModule(vertx) {
    override fun routers() {
        val routerBinder = Multibinder.newSetBinder(binder(), BaseRouter::class.java)
        routerBinder.addBinding().to(AccountRoute::class.java)
    }

    override fun factory() {
        bind(AccountUseCase::class.java).to(Account::class.java).`in`(Singleton::class.java)
    }

    override fun repository() {
        bind(AccountRepository::class.java).to(AccountRepositoryImpl::class.java).`in`(Singleton::class.java)
    }
}
