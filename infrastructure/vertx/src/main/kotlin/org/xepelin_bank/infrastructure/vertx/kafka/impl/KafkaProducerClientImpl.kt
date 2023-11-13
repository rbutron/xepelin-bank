package org.xepelin_bank.infrastructure.vertx.kafka.impl

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Completable
import io.vertx.core.json.JsonObject
import io.vertx.rxjava3.core.Vertx
import io.vertx.rxjava3.kafka.client.producer.KafkaProducer
import io.vertx.rxjava3.kafka.client.producer.KafkaProducerRecord
import org.xepelin_bank.common.exceptions.logger.LoggerDelegate
import org.xepelin_bank.infrastructure.vertx.kafka.configure.KafkaConfig
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaProducerClient

class KafkaProducerClientImpl @Inject constructor(vertx: Vertx, config: KafkaConfig) : KafkaProducerClient {

    private val logger by LoggerDelegate()

    private val producer: KafkaProducer<String, JsonObject>

    init {
        this.producer = buildProducer(vertx, config)
    }
    
    private fun buildProducer(vertx: Vertx, config: KafkaConfig): KafkaProducer<String, JsonObject> =
        KafkaProducer.create(
            vertx,
            mapOf(
                "bootstrap.servers" to config.getBootstrapServer(),
                "key.serializer" to config.getKeySerializer(),
                "value.serializer" to config.getValueSerializer(),
                "acks" to config.getAck()
            )
        )

    override fun <T>publisher(topic: String, key: String, value: T): Completable =
        producer.rxSend(KafkaProducerRecord.create(topic, JsonObject.of(key, JsonObject.mapFrom(value))))
            .doOnError { error ->
                logger.error("Error when Kafka send the message", error)
            }
            .concatMapCompletable {
                Completable.complete()
            }
}
