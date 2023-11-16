package org.xepelin_bank.common.extensions.message.constants

enum class Topics(val value: String) {
    CREATE_ACCOUNT_TOPIC("create.account"),
    EXISTING_ACCOUNT_TOPIC("existing.account"),

    CREATE_MONO_LOG_TOPIC("create.mono-log"),
    UPDATE_MONO_LOG_TOPIC("update.mono-log"),
    DELETE_MONO_LOG_TOPIC("delete.mono-log"),
    COMPLETE_MONO_LOG_TOPIC("complete.mono-log"),

    NEW_ACCOUNT_TRANSACTION_TOPIC("new.account.transaction"),
    EXISTING_ACCOUNT_TRANSACTION_TOPIC("existing.account.transaction")
}
