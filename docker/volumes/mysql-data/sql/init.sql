CREATE database if not exists restaurant_db;
GRANT ALL ON restaurant_db.* TO 'haont'@'%';

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
    price decimal(10,2) NOT NULL,
	available boolean NOT NULL,
	FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

CREATE table if not exists order_approval (
	id binary(16) PRIMARY KEY,
    order_id binary(16) NOT NULL,
	restaurant_id binary(16) NOT NULL,
	approval_status enum('APPROVED', 'REJECTED') NOT NULL,
	FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

INSERT INTO restaurant_db.restaurant(id, name, active)
	VALUES(UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb45'),'Rosetta', true),
	(UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb24'),'Stonehenge', false);

INSERT INTO restaurant_db.stock(id, restaurant_id, name, price ,quantity, available)
	VALUES(UUID_TO_BIN('03a1a44c-97d7-4fa5-898c-85e14b7bb682'), UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb45'), 'Hamburger',50.00 ,1000, true),
	(UUID_TO_BIN('8ae4fbf4-463d-4f95-b589-81ff462f01c7'), UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb45'), 'Pizza',50.00 ,300, true),
	(UUID_TO_BIN('3701d628-4c5b-44ca-a320-a672730af6ea'), UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb45'), 'KFC', 50.00 ,858, true),
    (UUID_TO_BIN('308901d1-aaeb-445c-9be2-922badb782f8'), UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb45'), 'Spaghetti', 10.00 ,10, true),
    (UUID_TO_BIN('fbee1f8b-501c-443c-bf37-ebcb572f4cb9'), UUID_TO_BIN('d215b5f8-0249-4dc5-89a3-51fd148cfb45'), 'Beefsteak',30.00, 40, false)


-- CREATE database if not exists keycloak;
-- GRANT ALL ON keycloak.* TO 'haont'@'%';
-- USE keycloak;
