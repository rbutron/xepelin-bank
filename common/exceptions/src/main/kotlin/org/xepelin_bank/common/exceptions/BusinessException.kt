package org.xepelin_bank.common.exceptions

class BusinessException : Throwable {
    constructor(message: String, throwable: Throwable, status: Int = 400) : super(message, throwable)
    constructor(message: String) : super(message)
}
