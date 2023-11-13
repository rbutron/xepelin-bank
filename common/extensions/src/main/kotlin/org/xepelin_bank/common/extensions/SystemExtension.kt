package org.xepelin_bank.common.extensions

import io.github.cdimascio.dotenv.Dotenv
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.json.JsonObject
import org.xepelin_bank.common.exceptions.RequestException
import java.lang.Exception
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Optional
import java.util.function.Predicate
import java.util.function.Supplier

object SystemExtension {
    
    private const val envUndefinedError = "Environment variable [[%s]] isn't defined."
    
    fun String.getMandatoryEnv(): String =
        Optional.of(Dotenv.load().get(this))
            .map(String::trim).filter(Predicate.not(String::isEmpty))
            .orElseThrow { NoSuchElementException(String.format(envUndefinedError, this)) }
    
    fun <T> JsonObject.parseTo(type: Class<T>): T =
        try {
            this.mapTo(type)
        } catch (e: IllegalArgumentException) {
            throw RequestException(e.localizedMessage, HttpResponseStatus.BAD_REQUEST.code())
        }
    
    fun String.strAsLocalDate(): LocalDate =
        LocalDate.parse(this)
    
    fun List<Any>.toSQLParam(
        offset: Int = 0
    ): String = List(this.size) { index -> "$${index + 1 + offset}" }.joinToString()
    
    fun List<Any>.isDifferentTo(list: List<Any>): Boolean =
        this.size != list.size
    
    fun formatDateToString(format: String): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern(format))
}
