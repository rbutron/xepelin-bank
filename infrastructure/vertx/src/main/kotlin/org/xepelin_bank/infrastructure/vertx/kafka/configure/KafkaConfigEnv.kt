package org.xepelin_bank.infrastructure.vertx.kafka.configure

import org.xepelin_bank.common.extensions.SystemExtension.getMandatoryEnv

class KafkaConfigEnv : KafkaConfig {
    
    companion object {
        private const val BOOTSTRAP_SERVER_KAFKA_VERTX = "BOOTSTRAP_SERVER_KAFKA_VERTX"
        private const val KEY_DESERIALIZER_KAFKA_VERTX = "KEY_DESERIALIZER_KAFKA_VERTX"
        private const val VALUE_DESERIALIZER_KAFKA_VERTX = "VALUE_DESERIALIZER_KAFKA_VERTX"
        private const val KEY_SERIALIZER_KAFKA_VERTX = "KEY_SERIALIZER_KAFKA_VERTX"
        private const val VALUE_SERIALIZER_KAFKA_VERTX = "VALUE_SERIALIZER_KAFKA_VERTX"
        private const val GROUP_KAFKA_VERTX = "GROUP_KAFKA_VERTX"
        private const val AUTO_OFFSET_RESET_KAFKA_VERTX = "AUTO_OFFSET_RESET_KAFKA_VERTX"
        private const val COMMIT_KAFKA_VERTX = "COMMIT_KAFKA_VERTX"
        private const val ACK_KAFKA_VERTX = "ACK_KAFKA_VERTX"
    }
    
    override fun getBootstrapServer(): String = BOOTSTRAP_SERVER_KAFKA_VERTX.getMandatoryEnv()
    
    override fun getKeyDeserializer(): String = KEY_DESERIALIZER_KAFKA_VERTX.getMandatoryEnv()
    
    override fun getValueDeserializer(): String = VALUE_DESERIALIZER_KAFKA_VERTX.getMandatoryEnv()

    override fun getKeySerializer(): String = KEY_SERIALIZER_KAFKA_VERTX.getMandatoryEnv()

    override fun getValueSerializer(): String = VALUE_SERIALIZER_KAFKA_VERTX.getMandatoryEnv()

    override fun getGroup(): String = GROUP_KAFKA_VERTX.getMandatoryEnv()
    
    override fun getAutoOffsetReset(): String = AUTO_OFFSET_RESET_KAFKA_VERTX.getMandatoryEnv()
    
    override fun getCommit(): String = COMMIT_KAFKA_VERTX.getMandatoryEnv()
    
    override fun getAck(): String = ACK_KAFKA_VERTX.getMandatoryEnv()
}
