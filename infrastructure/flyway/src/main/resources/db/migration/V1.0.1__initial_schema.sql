CREATE SCHEMA IF NOT EXISTS ledger;
CREATE SCHEMA IF NOT EXISTS transactions;
CREATE TYPE transaction_type AS ENUM  ('DEPOSIT', 'WITHDRAWAL');
CREATE TYPE event_type AS ENUM  ('CREATED', 'UPDATED', 'DELETED', 'COMPLETED');
CREATE TYPE brand_type AS ENUM  ('ACCOUNT');
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE ledger.account
(
    id                uuid                     DEFAULT uuid_generate_v4() NOT NULL,
    account_name      VARCHAR                                             NOT NULL,
    account_number    VARCHAR                                             NOT NULL,
    amount            VARCHAR                                             NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()                     NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT now()                     NOT NULL,
    CONSTRAINT ledger_account_pk PRIMARY KEY (id, account_number)
);

CREATE TABLE transactions.balance_account
(
    id                uuid                     DEFAULT uuid_generate_v4() NOT NULL,
    amount            VARCHAR                                             NOT NULL,
    transaction_type  transaction_type                                    NOT NULL,
    account_id        VARCHAR                                             NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()                     NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT now()                     NOT NULL,
    CONSTRAINT transaction_balance_account_pk PRIMARY KEY (id)
);

CREATE TABLE mono_log
(
    id                uuid                     DEFAULT uuid_generate_v4() NOT NULL,
    business_id       uuid                                                NOT NULL,
    event_type        event_type                                          NOT NULL,
    brand             brand_type                                          NOT NULL,
    data              JSONB                                               NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()                     NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT now()                     NOT NULL,
    CONSTRAINT mono_log_pk PRIMARY KEY (id)
);
