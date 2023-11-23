CREATE database if not exists product_db;
GRANT ALL ON product_db.* TO 'haont'@'%';

USE product_db;
CREATE table if not exists product (
	id int NOT NULL AUTO_INCREMENT,
	name varchar(100) NOT NULL,
	price BIGINT NOT NULL,
	quantity int NOT NULL,
	PRIMARY KEY (id)
);

CREATE database if not exists payment_db;
GRANT ALL ON payment_db.* TO 'haont'@'%';

USE payment_db;
CREATE table if not exists payments (
	id binary(16) PRIMARY KEY,
	customer_id binary(16)  NOT NULL,
	order_id binary(16) NOT NULL,
	amount BIGINT NOT NULL,
	payment_status varchar(20) NOT NULL,
	created_at TIMESTAMP NOT NULL
);

CREATE table if not exists credit_entry (
	id binary(16) PRIMARY KEY,
	customer_id binary(16)  NOT NULL,
	total_credit_amount BIGINT NOT NULL
);

CREATE table if not exists credit_history (
	id binary(16) PRIMARY KEY,
	customer_id binary(16)  NOT NULL,
	amount BIGINT NOT NULL,
	transaction_type varchar(20) NOT NULL
);

INSERT INTO payment_db.credit_entry(id, customer_id, total_credit_amount)
	VALUES (UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb21'), UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb41'), 500.00);

INSERT INTO payment_db.credit_history(id, customer_id, amount, transaction_type)
	VALUES (UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb23'), UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb41'), 100.00, 'CREDIT');
INSERT INTO payment_db.credit_history(id, customer_id, amount, transaction_type)
	VALUES (UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb24'), UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb41'), 600.00, 'CREDIT');
INSERT INTO payment_db.credit_history(id, customer_id, amount, transaction_type)
	VALUES (UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb25'), UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb41'), 200.00, 'DEBIT');

-- CREATE database if not exists keycloak;
-- GRANT ALL ON keycloak.* TO 'haont'@'%';
-- USE keycloak;
