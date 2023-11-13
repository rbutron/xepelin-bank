package org.xepelin_bank.mono_log.adapter.config

import org.xepelin_bank.common.extensions.SystemExtension.getMandatoryEnv

class MonoLogConfigEnv : MonoLogConfig {
    
    companion object {
        private const val DB_NAME = "POSTGRES_DB"
        private const val DB_PORT = "POSTGRES_PORT"
        private const val DB_HOST = "POSTGRES_HOST"
        private const val DB_FULL = "POSTGRES_FULL_URI"
        private const val DB_PASSWORD = "POSTGRES_PASSWORD"
        private const val DB_USER = "POSTGRES_USER"
    }
    
    override fun getDBPassword(): String = DB_PASSWORD.getMandatoryEnv()
    
    override fun getDBUser(): String = DB_USER.getMandatoryEnv()
    
    override fun getDBName(): String = DB_NAME.getMandatoryEnv()
    
    override fun getDBHost(): String = DB_HOST.getMandatoryEnv()
    
    override fun getDBFull(): String = DB_FULL.getMandatoryEnv()
    
    override fun getDBPort(): Int = DB_PORT.getMandatoryEnv().toInt()

}
