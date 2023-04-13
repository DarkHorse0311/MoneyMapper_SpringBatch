DROP TABLE money_transactions IF EXISTS;

CREATE TABLE money_transactions (
    transaction_id BIGINT NOT NULL PRIMARY KEY,
    transaction_date DATE,
    transaction_user VARCHAR(40),
    description VARCHAR(40),
    amount DOUBLE,
    tag VARCHAR(40),
    category VARCHAR(40)
)