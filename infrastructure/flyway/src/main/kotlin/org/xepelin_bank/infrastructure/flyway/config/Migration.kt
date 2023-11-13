package org.xepelin_bank.infrastructure.flyway.config

interface Migration {
    fun migrateDB(): Any
}
