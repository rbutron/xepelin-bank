package org.xepelin_bank.common.exceptions

import java.lang.Exception

open class DatabaseException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, error: Throwable) : super(message, error)
}
