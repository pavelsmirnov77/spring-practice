insert into products_smirnov_pa.users (username, password, email, login)
values ('User1', 'User1', 'User1@yandex.ru', 'User1');
insert into products_smirnov_pa.users (username, password, email, login)
values ('User2', 'User2', 'User2@yandex.ru', 'User2');
insert into products_smirnov_pa.users (username, password, email, login)
values ('User3', 'User3', 'User3@yandex.ru', 'User3');

insert into products_smirnov_pa.products (name, price, quantity)
values ('Товар1', 99, 15);
insert into products_smirnov_pa.products (name, price, quantity)
values ('Товар2', 55, 15);
insert into products_smirnov_pa.products (name, price, quantity)
values ('Товар3', 22, 15);
insert into products_smirnov_pa.products (name, price, quantity)
values ('Товар4', 100, 15);
insert into products_smirnov_pa.products (name, price, quantity)
values ('Товар5', 1000, 15);

insert into products_smirnov_pa.products_carts (client_id, product_id, quantity)
values (1, 1, 3);
insert into products_smirnov_pa.products_carts (client_id, product_id, quantity)
values (2, 4, 6);
insert into products_smirnov_pa.products_carts (client_id, product_id, quantity)
values (3, 3, 10);

insert into products_smirnov_pa.roles (name)
values ('ROLE_USER');
insert into products_smirnov_pa.roles (name)
values ('ROLE_ADMIN');

insert into products_smirnov_pa.user_roles
values (1, 1);
insert into products_smirnov_pa.user_roles
values (2, 1);
insert into products_smirnov_pa.user_roles
values (3, 2);