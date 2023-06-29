create schema if not exists products_smirnov_pa;

create table products_smirnov_pa.products
(
    id    integer generated always as identity primary key,
    name  varchar(255) not null,
    price numeric      not null,
    count integer      not null
    );

create table products_smirnov_pa.carts
(
    id        integer generated always as identity primary key,
    promocode varchar(255)
    );

create table products_smirnov_pa.clients
(
    id       integer generated always as identity primary key,
    name     varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    cart_id  integer      not null
    constraint client_cart_id_fk
    references products_smirnov_pa.carts
    );

create table products_smirnov_pa.products_carts
(
    id         integer generated always as identity primary key,
    id_product integer not null
    constraint product_client_products_id_fk
    references products_smirnov_pa.products,
    id_cart    integer not null
    constraint product_client_cart_id_fk
    references products_smirnov_pa.carts,
    count      integer not null
);

create table products_smirnov_pa.client_bank
(
    id        integer generated always as identity primary key,
    balance   numeric not null,
    client_id integer
    constraint client_bank_client_id_fk
    references products_smirnov_pa.client (id)
);