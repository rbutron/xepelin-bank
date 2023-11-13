package org.xepelin_bank.mono_log.adapter.guice

import com.google.inject.Singleton
import com.google.inject.multibindings.Multibinder
import io.vertx.rxjava3.core.Vertx
import org.xepelin_bank.infrastructure.vertx.http.BaseRouter
import org.xepelin_bank.mono_log.adapter.repository.MonoLogRepository
import org.xepelin_bank.mono_log.adapter.repository.MonoLogRepositoryImpl
import org.xepelin_bank.mono_log.adapter.service.MonoLog
import org.xepelin_bank.mono_log.domain.use_case.MonoLogUseCase

class ModuleCreator(vertx: Vertx) : AbstractModule(vertx) {
    override fun routers() {
        Multibinder.newSetBinder(binder(), BaseRouter::class.java)
    }
    
    override fun factory() {
        bind(MonoLogUseCase::class.java).to(MonoLog::class.java).`in`(Singleton::class.java)
    }
    
    override fun repository() {
        bind(MonoLogRepository::class.java).to(MonoLogRepositoryImpl::class.java).`in`(Singleton::class.java)
    }
}
