create schema if not exists products_smirnov_pa;

create table products_smirnov_pa.products
(
    integer generated always as identity primary key,
    name varchar(255) not null,
    numeric      not null,
    count integer      not null
    );

create table products_smirnov_pa.carts
(
    integer generated always as identity primary key,
    promocode varchar(255)
    );

create table products_smirnov_pa.clients
(
    integer generated always as identity primary key,
    name varchar(255) not null,
    username varchar(255) not null,
    varchar(255) not null,
    email varchar(255),
    integer      not null
    constraint client_cart_id_fk
    references products_smirnov_pa.carts
    );

create table products_smirnov_pa.products_carts
(
    integer generated always as identity primary key,
    integer not null
    constraint product_client_products_id_fk
    references products_smirnov_pa.products,
    integer not null
    constraint product_client_cart_id_fk
    references products_smirnov_pa.carts,
    count integer not null
);