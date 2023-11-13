package org.xepelin_bank.infrastructure.vertx.database

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.vertx.rxjava3.sqlclient.Row

interface PGClient {
    fun get(query: String, params: List<Any>): Observable<Row>
    fun getOne(query: String, params: List<Any>): Single<Row>
    fun saveOrUpdate(query: String, params: List<Any?>): Single<Row>
}
