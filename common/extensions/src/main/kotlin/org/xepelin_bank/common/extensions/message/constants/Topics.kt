package org.xepelin_bank.common.extensions.message.constants

enum class Topics(val value: String) {
    CREATE_ACCOUNT_TOPIC("create.account"),
    CREATE_MONO_LOG_TOPIC("create.mono-log"),
    UPDATE_MONO_LOG_TOPIC("update.mono-log"),
    DELETE_MONO_LOG_TOPIC("delete.mono-log"),
    COMPLETE_MONO_LOG_TOPIC("complete.mono-log"),
}
