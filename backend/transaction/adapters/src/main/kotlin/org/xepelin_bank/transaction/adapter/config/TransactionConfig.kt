package org.xepelin_bank.transaction.adapter.config

interface TransactionConfig {
    fun getDBPassword(): String
    fun getDBUser(): String
    fun getDBName(): String
    fun getDBHost(): String
    fun getDBFull(): String
    fun getDBPort(): Int
}
