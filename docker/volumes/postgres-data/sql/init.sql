CREATE DATABASE "ms_order";
CREATE USER ms_order WITH ENCRYPTED PASSWORD '123456';
GRANT ALL PRIVILEGES ON DATABASE ms_order TO ms_order;
ALTER ROLE ms_order
    SUPERUSER
    CREATEDB
    CREATEROLE;
\connect "ms_order";
CREATE SCHEMA IF NOT EXISTS "order"
    AUTHORIZATION ms_order;

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

DROP TABLE IF EXISTS "order".order_items CASCADE;

CREATE TABLE "order".order_items
(
    id         bigint         NOT NULL,
    order_id   uuid           NOT NULL,
    product_id uuid           NOT NULL,
    price      numeric(10,2)  NOT NULL,
    quantity   integer        NOT NULL,
    sub_total  numeric(10, 2) NOT NULL,
    CONSTRAINT order_items_pkey PRIMARY KEY (id, order_id)
);

ALTER TABLE "order".order_items
    ADD CONSTRAINT "FK_ORDER_ID" FOREIGN KEY (order_id) REFERENCES "order".orders (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE NOT VALID;

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

DROP TYPE IF EXISTS saga_status;
CREATE TYPE saga_status AS ENUM ('STARTED','FAILED','SUCCEEDED','PROCESSING','COMPENSATING', 'COMPENSATED');

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('STARTED','COMPLETED','FAILED');

DROP TABLE IF EXISTS "order".payment_outbox;
CREATE TABLE "order".payment_outbox
(
    id uuid NOT NULL ,
    saga_id uuid NOT NULL ,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL ,
    processed_at TIMESTAMP WITH TIME ZONE,
    type character varying COLLATE pg_catalog."default" NOT NULL ,
    payload jsonb NOT NULL ,
    outbox_status outbox_status NOT NULL ,
    saga_status saga_status NOT NULL ,
    order_status order_status NOT NULL ,
    version integer NOT NULL ,
    CONSTRAINT payment_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX "payment_outbox_saga_status"
    ON "order".payment_outbox
        (type, outbox_status, saga_status);

CREATE UNIQUE INDEX "payment_outbox_saga_id"
    ON "order".payment_outbox
        (type, saga_id, saga_status);

DROP TABLE IF EXISTS "order".restaurant_approval_outbox;
CREATE TABLE "order".restaurant_approval_outbox
(
    id uuid NOT NULL ,
    saga_id uuid NOT NULL ,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL ,
    processed_at TIMESTAMP WITH TIME ZONE,
    type character varying COLLATE pg_catalog."default" NOT NULL ,
    payload jsonb NOT NULL ,
    outbox_status outbox_status NOT NULL ,
    saga_status saga_status NOT NULL ,
    order_status order_status NOT NULL ,
    version integer NOT NULL ,
    CONSTRAINT restaurant_approval_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX "restaurant_approval_outbox_saga_status"
    ON "order".restaurant_approval_outbox
        (type, outbox_status, saga_status);

CREATE UNIQUE INDEX "restaurant_approval_outbox_saga_id"
    ON "order".restaurant_approval_outbox
        (type, saga_id, saga_status);

CREATE DATABASE "ms_customer";
CREATE USER ms_customer WITH ENCRYPTED PASSWORD '123456';
GRANT ALL PRIVILEGES ON DATABASE ms_customer TO ms_customer;
ALTER ROLE ms_customer
    SUPERUSER
    CREATEDB
    CREATEROLE;
\connect "ms_customer";
CREATE SCHEMA IF NOT EXISTS "customer"
    AUTHORIZATION ms_customer;

CREATE TABLE IF NOT EXISTS "customer".customer (
    id uuid NOT NULL ,
    user_name character varying COLLATE pg_catalog."default" NOT NULL ,
    first_name character varying COLLATE pg_catalog."default" NOT NULL ,
    last_name character varying COLLATE pg_catalog."default" NOT NULL ,
    amount  numeric(10, 2) NOT NULL,
    active boolean NOT NULL ,
    CONSTRAINT customer_pkey PRIMARY KEY (id)
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
