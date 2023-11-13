package org.xepelin_bank.mono_log.adapter.config

interface MonoLogConfig {
    fun getDBPassword(): String
    fun getDBUser(): String
    fun getDBName(): String
    fun getDBHost(): String
    fun getDBFull(): String
    fun getDBPort(): Int
}
