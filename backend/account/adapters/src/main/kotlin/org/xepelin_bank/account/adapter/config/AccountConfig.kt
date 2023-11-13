package org.xepelin_bank.account.adapter.config

interface AccountConfig {
    fun getDBPassword(): String
    fun getDBUser(): String
    fun getDBName(): String
    fun getDBHost(): String
    fun getDBFull(): String
    fun getDBPort(): Int
}
