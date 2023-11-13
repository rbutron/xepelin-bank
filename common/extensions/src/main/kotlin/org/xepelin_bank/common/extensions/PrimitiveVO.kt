package org.xepelin_bank.common.extensions

abstract class PrimitiveVO<T> {
    abstract fun value(): T?
    
    override fun toString(): String {
        return value().toString()
    }
}
