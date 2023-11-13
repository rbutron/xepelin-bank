package org.xepelin_bank.common.extensions.message.constants

enum class EventType(val key: String) {
    CREATE_EVENT_TYPE("CREATED"),
    UPDATE_EVENT_TYPE("UPDATED"),
    DELETE_EVENT_TYPE("DELETED"),
    COMPLETE_EVENT_TYPE("COMPLETED"),
}
