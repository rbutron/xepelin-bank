package org.xepelin_bank.infrastructure.vertx.database

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.vertx.pgclient.PgConnectOptions
import io.vertx.rxjava3.core.Vertx
import io.vertx.rxjava3.pgclient.PgPool
import io.vertx.rxjava3.sqlclient.Row
import io.vertx.rxjava3.sqlclient.SqlClient
import io.vertx.rxjava3.sqlclient.Tuple
import io.vertx.sqlclient.PoolOptions
import org.xepelin_bank.common.exceptions.PersistenceException
import org.xepelin_bank.infrastructure.vertx.database.mappers.CredentialDBMapper

class PGClientImpl @Inject constructor(vertx: Vertx, credentialDBMapper: CredentialDBMapper): PGClient {
    companion object {
        private const val MAX_CONNECTIONS_PER_INSTANCE = 5
    }

    private val jdbc: SqlClient

    init {
        this.jdbc = createClient(vertx, credentialDBMapper)
    }
    
    private fun createClient(vertx: Vertx, credentialDBMapper: CredentialDBMapper): SqlClient {
        val connect = PgConnectOptions().apply {
            port = credentialDBMapper.port
            host = credentialDBMapper.host
            database = credentialDBMapper.database
            user = credentialDBMapper.user
            password = credentialDBMapper.password
        }
        val poolOptions = PoolOptions().apply {
            maxSize = MAX_CONNECTIONS_PER_INSTANCE
        }
        return PgPool.pool(vertx, connect, poolOptions)
    }
    
    override fun get(query: String, params: List<Any>): Observable<Row> {
        val p = Tuple.tuple()
        params.forEach { p.addValue(it) }
        return jdbc.preparedQuery(query).rxExecute(p).flatMapObservable { Observable.fromIterable(it) }
    }

    override fun getOne(query: String, params: List<Any>): Single<Row> {
        val p = Tuple.tuple()
        params.forEach { p.addValue(it) }
        return jdbc.preparedQuery(query).rxExecute(p).map { it.first() }
    }

    override fun saveOrUpdate(query: String, params: List<Any?>): Single<Row> {
        val p = Tuple.tuple()
        params.forEach { p.addValue(it) }
        return jdbc.preparedQuery(query).rxExecute(p).map {
            when (it.size()) {
                0 -> throw PersistenceException("Save or Update operation with empty result")
                1 -> it.iterator().next()
                else -> throw PersistenceException("Save or Update operation with result greater than 1, result size: ${it.size()}")
            }
        }
    }
}
