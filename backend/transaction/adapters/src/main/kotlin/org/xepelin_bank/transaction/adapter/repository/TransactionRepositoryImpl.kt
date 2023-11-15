package org.xepelin_bank.transaction.adapter.repository

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.infrastructure.vertx.database.PGClientImpl
import org.xepelin_bank.transaction.adapter.repository.entity.TransactionEntity
import org.xepelin_bank.transaction.adapter.repository.mapper.TransactionMapper.buildTransactionEntity

class TransactionRepositoryImpl @Inject constructor(private val pg: PGClientImpl) : TransactionRepository {
    override fun save(transactionEntity: TransactionEntity): Single<TransactionEntity> =
        this.pg.saveOrUpdate(
            """
                INSERT INTO transactions.balance_account (account_id,
                        transaction_type,
                        amount)
                VALUES ($1, $2, $3)
                RETURNING id, account_id, transaction_type, amount, created_at, updated_at
        """.trimIndent(),
            listOf(
                transactionEntity.accountId,
                transactionEntity.transactionType,
                transactionEntity.amount,
            )
        ).map(::buildTransactionEntity)

}
