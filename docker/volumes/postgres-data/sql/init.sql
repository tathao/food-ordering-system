DROP SCHEMA IF EXISTS "order" CASCADE;

CREATE SCHEMA IF NOT EXISTS "order"
    AUTHORIZATION postgres;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS order_status;

CREATE TYPE order_status AS ENUM ('PENDING', 'PAID', 'APPROVED', 'CANCELLED', 'CANCELLING');

DROP TABLE IF EXISTS "order".orders CASCADE;

CREATE TABLE "order".orders
(
    id               uuid           NOT NULL,
    customer_id      uuid           NOT NULL,
    restaurant_id    uuid           NOT NULL,
    tracking_id      uuid           NOT NULL,
    total_amount     numeric(10, 2) NOT NULL,
    order_status     order_status   NOT NULL,
    failure_messages character varying COLLATE pg_catalog."default",
    CONSTRAINT orders_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "order".order_product CASCADE;

CREATE TABLE "order".order_product
(
    id               uuid           NOT NULL,
    price            numeric(10, 2) NOT NULL,
    name character varying COLLATE pg_catalog."default",
    CONSTRAINT order_product_pkey PRIMARY KEY (id)
);

INSERT INTO "order".order_product(
	id, price, name)
	VALUES ('8ae4fbf4-463d-4f95-b589-81ff462f01c7', 15.00, 'Pizza'),('3701d628-4c5b-44ca-a320-a672730af6ea', 10.00, 'KFC'),('03a1a44c-97d7-4fa5-898c-85e14b7bb682', 5.00, 'Hamburger');


DROP TABLE IF EXISTS "order".order_items CASCADE;

CREATE TABLE "order".order_items
(
    id         bigint         NOT NULL,
    order_id   uuid           NOT NULL,
    product_id uuid           NOT NULL,
    quantity   integer        NOT NULL,
    amount  numeric(10, 2) NOT NULL,
    CONSTRAINT order_items_pkey PRIMARY KEY (id, order_id)
);

ALTER TABLE "order".order_items
    ADD CONSTRAINT "FK_ORDER_ID" FOREIGN KEY (order_id) REFERENCES "order".orders (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE NOT VALID;

ALTER TABLE "order".order_items
    ADD CONSTRAINT "FK_PROD_ID" FOREIGN KEY (product_id) REFERENCES "order".order_product (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE NOT VALID;

DROP TABLE IF EXISTS "order".order_address CASCADE;

CREATE TABLE "order".order_address
(
    id          uuid                                           NOT NULL,
    order_id    uuid                                           NOT NULL,
    street      character varying COLLATE pg_catalog."default" NOT NULL,
    postal_code character varying COLLATE pg_catalog."default" NOT NULL,
    city        character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT order_address_pkey PRIMARY KEY (id)
);

ALTER TABLE "order".order_address
    ADD CONSTRAINT "FK_ORDER_ID" FOREIGN KEY (order_id) REFERENCES "order".orders (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE NOT VALID;


CREATE SCHEMA IF NOT EXISTS "order_schema"
    AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS "order_schema"."order_table" (
    id Serial PRIMARY KEY,
    prod_id SmallInt NOT NULL,
    quantity SmallInt NOT NULL,
    order_date TIMESTAMP WITH TIME ZONE NOT NULL,
    status Varchar(20),
    total_amount BigInt NOT NULL
);

CREATE DATABASE "keycloak";
CREATE USER keycloak WITH ENCRYPTED PASSWORD 'keycloak';
GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak;
\connect "keycloak";
CREATE SCHEMA IF NOT EXISTS "keycloak"
    AUTHORIZATION keycloak;

CREATE DATABASE "uqcdb";
CREATE USER uqc_admin WITH ENCRYPTED PASSWORD '123qwe123';
GRANT ALL PRIVILEGES ON DATABASE uqcdb TO uqc_admin;
 \connect "uqcdb";
-- CREATE SCHEMA IF NOT EXISTS "uqc"
--     AUTHORIZATION uqc_admin;
DROP SCHEMA IF EXISTS "public";

CREATE DATABASE "elarissdb";
CREATE USER elarissusername WITH ENCRYPTED PASSWORD 'elarisspassword';
CREATE USER rdsadmin WITH ENCRYPTED PASSWORD 'elarisspassword';
GRANT ALL PRIVILEGES ON DATABASE elarissdb TO rdsadmin;
-- \connect "elarissdb";
-- CREATE SCHEMA IF NOT EXISTS "uqc"
--     AUTHORIZATION uqc_admin;
