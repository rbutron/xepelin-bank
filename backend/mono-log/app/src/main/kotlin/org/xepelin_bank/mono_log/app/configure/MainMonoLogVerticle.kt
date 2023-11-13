package org.xepelin_bank.mono_log.app.configure

import com.google.inject.Inject
import org.xepelin_bank.infrastructure.flyway.config.Migration
import org.xepelin_bank.infrastructure.vertx.http.HTTPVerticle
import org.xepelin_bank.infrastructure.vertx.http.MainRouter

class MainMonoLogVerticle @Inject constructor(main: MainRouter, migration: Migration) :
    HTTPVerticle(main, migration) {
    override fun port(): Int = 9020
    
    override fun path(): String = "/mono-log"
}
