package org.xepelin_bank.common.exceptions

class NotFoundException : Throwable {
    constructor(message: String, throwable: Throwable, status: Int = 404) : super(message, throwable)
    constructor(message: String) : super(message)
}
