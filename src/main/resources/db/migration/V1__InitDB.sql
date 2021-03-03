create schema if not exists setronica;
create extension if not exists "uuid-ossp";
create sequence setronica.hibernate_sequence;

create table if not exists PRODUCT (
    id UUID default uuid_generate_v4 () primary key,
    created TIMESTAMPTZ not null,
    modified TIMESTAMPTZ not null);

create table if not exists PRODUCT_PRODUCT_INFO(
    product_id UUID ,
    product_info_id bigint);

create table if not exists PRODUCT_INFO (
    id bigint primary key,
    language varchar(2)  not null,
    name VARCHAR(150) not null,
    description VARCHAR(3000),
    created TIMESTAMPTZ not null,
    modified TIMESTAMPTZ not null);

create table if not exists PRICE_INFO (
    id bigint primary key,
    currency varchar(3)  not null,
    price NUMERIC(19,2) not null CHECK (price>0),
    created TIMESTAMPTZ not null,
    modified TIMESTAMPTZ not null);

create table if not exists PRODUCT_PRICE_INFO(
    product_id UUID ,
    price_info_id bigint);
