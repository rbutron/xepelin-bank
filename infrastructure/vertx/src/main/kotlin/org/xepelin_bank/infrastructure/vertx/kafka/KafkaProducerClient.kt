package org.xepelin_bank.infrastructure.vertx.kafka

import io.reactivex.rxjava3.core.Completable

interface KafkaProducerClient {
    fun <T>publisher(topic: String, key: String, value: T): Completable
}
