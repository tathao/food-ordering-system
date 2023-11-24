CREATE database if not exists restaurant_db;
GRANT ALL ON product_db.* TO 'haont'@'%';

USE restaurant_db;
CREATE table if not exists restaurant (
	id binary(16) PRIMARY KEY,
	name varchar(100) NOT NULL,
	active boolean NOT NULL
);

CREATE table if not exists stock (
	id binary(16) PRIMARY KEY,
	restaurant_id binary(16) NOT NULL,
	name varchar(100)  NOT NULL,
	quantity int NOT NULL,
	available boolean NOT NULL,
	FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

CREATE table if not exists order_approval (
	id binary(16) PRIMARY KEY,
	restaurant_id binary(16) NOT NULL,
	approoval_status enum('APPROVED', 'REJECTED') NOT NULL,
	FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

CREATE table if not exists order_detail (
	id binary(16) PRIMARY KEY,
	order_id binary(16) UNIQUE,
	order_status enum('PENDING','PAID','APPROVED','CANCELLING','CANCELLED'),
	FOREIGN KEY (order_id) REFERENCES order_approval(id)
);

CREATE table if not exists product (
	id binary(16) NOT NULL,
	order_detail_id binary(16) NOT NULL,
	name varchar(100) NOT NULL,
	quantity int NOT NULL,
	FOREIGN KEY (order_detail_id) REFERENCES order_detail(id)
);


INSERT INTO restaurant_db.restaurant(id, name, active)
	VALUES(UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb45'),'Rosetta', true),
	(UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb24'),'Stonehenge', false);

INSERT INTO restaurant_db.stock(id, restaurant_id, name, quantity, available)
	VALUES(UUID_TO_BIN('03a1a44c-97d7-4fa5-898c-85e14b7bb682'), UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb45'), 'Hamburger', 1000, true),
	(UUID_TO_BIN('8ae4fbf4-463d-4f95-b589-81ff462f01c7'), UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb45'), 'Pizza', 300, true),
	(UUID_TO_BIN('3701d628-4c5b-44ca-a320-a672730af6ea'), UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb45'), 'KFC', 858, true)


-- CREATE database if not exists keycloak;
-- GRANT ALL ON keycloak.* TO 'haont'@'%';
-- USE keycloak;
