package org.xepelin_bank.account.adapter.repository

import com.google.inject.Inject
import io.reactivex.rxjava3.core.Single
import org.xepelin_bank.account.adapter.repository.entity.AccountEntity
import org.xepelin_bank.account.adapter.repository.mapper.AccountMapper.buildAccountEntity
import org.xepelin_bank.infrastructure.vertx.database.PGClientImpl

class AccountRepositoryImpl @Inject constructor(private val pg: PGClientImpl): AccountRepository {
    override fun save(accountEntity: AccountEntity): Single<AccountEntity> =
        pg.saveOrUpdate(
            """
                INSERT INTO ledger.account (id,
                        account_name,
                        account_number,
                        amount)
                VALUES ($1, $2, $3, $4)
                RETURNING id, account_name, account_number, amount, created_at, updated_at
        """.trimIndent(),
            listOf(
                accountEntity.id,
                accountEntity.accountName,
                accountEntity.accountNumber,
                accountEntity.amount
            )
        ).map(::buildAccountEntity)

    override fun getAccountByNumber(accountNumber: String): Single<AccountEntity> =
        pg.getOne(
            """
            SELECT a.id,
            a.account_name,
            a.account_number,
            a.amount,
            a.created_at,
            a.updated_at
            FROM ledger.account a
            WHERE a.id = $1
        """.trimIndent(), listOf(accountNumber)
        ).map(::buildAccountEntity)
}
