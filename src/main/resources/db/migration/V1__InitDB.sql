create schema if not exists setronica;
create extension if not exists "uuid-ossp";
create sequence setronica.hibernate_sequence;

CREATE TYPE lang AS ENUM ('RUS','ENG','BEL','ZHO','KOR','SPA','ITA','UKR');
CREATE TYPE curr AS ENUM ('RUB','USD','EUR','GBP','CNY','JPY','UAH','CAD');
create table if not exists PRODUCT (
    id UUID default uuid_generate_v4 () primary key,
    created TIMESTAMPTZ,
    modified TIMESTAMPTZ);

create table if not exists PRODUCT_PRODUCT_INFO(
    product_id UUID ,
    product_info_id bigint);

create table if not exists PRODUCT_INFO (
    id bigint primary key,
--    product_id UUID references PRODUCT(id) ON DELETE CASCADE,
    language varchar(3)  not null,
    name VARCHAR(150) not null,
    description VARCHAR(3000),
    created TIMESTAMPTZ,
    modified TIMESTAMPTZ);
--    UNIQUE (product_id,language)
--    );

create table if not exists PRICE_INFO (
    id bigint primary key,
--    product_id UUID references PRODUCT(id) ON DELETE CASCADE,
    currency varchar(3)  not null,
    price NUMERIC(19,2) CHECK (price>0),
    created TIMESTAMPTZ,
    modified TIMESTAMPTZ);
--    UNIQUE (product_id,currency)
--    );

create table if not exists PRODUCT_PRICE_INFO(
    product_id UUID ,
    price_info_id bigint);
