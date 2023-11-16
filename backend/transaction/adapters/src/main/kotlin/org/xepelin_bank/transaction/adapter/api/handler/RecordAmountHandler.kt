package org.xepelin_bank.transaction.adapter.api.handler

import io.vertx.core.json.JsonObject
import io.vertx.rxjava3.kafka.client.consumer.KafkaConsumerRecord
import org.xepelin_bank.common.exceptions.logger.LoggerDelegate
import org.xepelin_bank.common.extensions.SystemExtension.parseTo
import org.xepelin_bank.transaction.domain.command.CreateTransactionBalanceCommand
import org.xepelin_bank.transaction.domain.kernel.account.AccountId
import java.util.UUID

class RecordAmountHandler(record: KafkaConsumerRecord<String, JsonObject>) {

    private val logger by LoggerDelegate()
    init {
        val accountId = AccountId(UUID.fromString(record.value().map { it.key }.first()))
        val createTransactionBalanceCommand = record.value().getJsonObject(accountId.value().toString())
            .parseTo(CreateTransactionBalanceCommand::class.java)
        if (createTransactionBalanceCommand.amount.toDouble() > 10_000.0)
            logger.info("The transaction was ${createTransactionBalanceCommand.amount} US\$ of the account-id: ${accountId.value()}")
    }
}
