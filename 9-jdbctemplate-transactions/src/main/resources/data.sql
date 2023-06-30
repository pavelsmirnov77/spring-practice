INSERT INTO products_smirnov_pa.products (name, price, count)
VALUES
    ('Метла', 100, 10),
    ('Зеркало', 200, 20),
    ('Ведро', 300, 30),
    ('Рулон обоев', 1000, 20),
    ('Скотч', 50, 100),
    ('Набор ручек', 300, 150),
    ('Клавиатура', 1500, 30);

INSERT INTO products_smirnov_pa.carts (promocode)
VALUES ('PROMOIVAN'), ('PROMOPETR'), ('PROMOSID');

INSERT INTO products_smirnov_pa.clients (name, username, password, email, cart_id)
VALUES ('Иван Иванов', 'ivanov123', 'password123', 'ivanov@yandex.com', 1),
       ('Петр Петров', 'petrov321', 'password321', 'petrov@yandex.com', 2),
       ('Сидр Сидоров', 'sidorov132', 'password132', 'sidorov@yandex.ru', 3);

INSERT INTO products_smirnov_pa.products_carts (id_product, id_cart, count)
VALUES
    (1, 1, 2),
    (2, 1, 1),
    (4, 1, 2),
    (5, 1, 1),
    (1, 2, 3),
    (2, 2, 1),
    (3, 2, 2),
    (4, 2, 3),
    (1, 3, 3),
    (2, 3, 3),
    (3, 3, 1),
    (4, 3, 3);


INSERT INTO products_smirnov_pa.clients_bank (balance, client_id)
VALUES
    (10000, 1),
    (20000, 2),
    (30000, 3);

COMMIT;
