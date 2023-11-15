ALTER TABLE transactions.balance_account
ALTER COLUMN account_id TYPE uuid USING account_id::uuid;
