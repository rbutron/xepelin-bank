package org.xepelin_bank.transaction.adapter.guice

import com.google.inject.multibindings.Multibinder
import io.vertx.rxjava3.core.Vertx
import org.xepelin_bank.transaction.adapter.api.TransactionRoute
import org.xepelin_bank.infrastructure.vertx.http.BaseRouter

class ModuleCreator(vertx: Vertx) : AbstractModule(vertx) {
    override fun routers() {
        val routerBinder = Multibinder.newSetBinder(binder(), BaseRouter::class.java)
        routerBinder.addBinding().to(TransactionRoute::class.java)
    }
    
    override fun factory() {
    }
    
    override fun repository() {
    }
}
