package org.xepelin_bank.common.exceptions

class RequestException(message: String, var status: Int) : Exception(message)
