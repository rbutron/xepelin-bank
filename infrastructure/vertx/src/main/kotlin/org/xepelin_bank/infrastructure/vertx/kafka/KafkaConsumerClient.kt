package org.xepelin_bank.infrastructure.vertx.kafka

import io.vertx.core.json.JsonObject
import io.vertx.rxjava3.kafka.client.consumer.KafkaConsumer

interface KafkaConsumerClient {
    fun configureConsumer(): KafkaConsumer<String, JsonObject>
}
