package org.xepelin_bank.infrastructure.vertx.kafka.configure

interface KafkaConfig {
    fun getBootstrapServer(): String
    fun getKeyDeserializer(): String
    fun getValueDeserializer(): String
    fun getKeySerializer(): String
    fun getValueSerializer(): String
    fun getGroup(): String
    fun getAutoOffsetReset(): String
    fun getCommit(): String
    fun getAck(): String
}
