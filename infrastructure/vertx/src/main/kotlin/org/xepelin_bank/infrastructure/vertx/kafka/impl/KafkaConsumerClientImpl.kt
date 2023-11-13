package org.xepelin_bank.infrastructure.vertx.kafka.impl

import com.google.inject.Inject
import io.vertx.core.json.JsonObject
import io.vertx.rxjava3.core.Vertx
import io.vertx.rxjava3.kafka.client.consumer.KafkaConsumer
import org.xepelin_bank.infrastructure.vertx.kafka.KafkaConsumerClient
import org.xepelin_bank.infrastructure.vertx.kafka.configure.KafkaConfig

class KafkaConsumerClientImpl @Inject constructor(private val vertx: Vertx, private val  config: KafkaConfig) : KafkaConsumerClient {
    override fun configureConsumer(): KafkaConsumer<String, JsonObject> =
        KafkaConsumer.create(
            vertx,
            mapOf(
                "bootstrap.servers" to config.getBootstrapServer(),
                "key.deserializer" to config.getKeyDeserializer(),
                "value.deserializer" to config.getValueDeserializer(),
                "group.id" to config.getGroup(),
                "auto.offset.reset" to config.getAutoOffsetReset(),
                "enable.auto.commit" to config.getCommit()
            )
        )
}
