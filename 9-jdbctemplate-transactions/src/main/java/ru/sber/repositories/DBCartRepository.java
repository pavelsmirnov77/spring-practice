package ru.sber.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entities.Cart;
import ru.sber.entities.Payment;
import ru.sber.entities.Product;
import ru.sber.proxies.BankAppProxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DBCartRepository implements CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBCartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addProductById(long cartId, long productId) {
        String selectCountClientProductSql = """
                SELECT count FROM products_carts 
                WHERE id_product = ? 
                AND id_cart = ?
                """;
        String insertProductSql = """
                INSERT INTO products_carts (id_product, id_cart, count) 
                VALUES (?, ?, 1)
                """;
        String updateCountClientProductSql = """
                UPDATE products_carts SET count = count + 1 
                WHERE id_product = ? 
                AND id_cart = ?
                """;

        try {
            Integer count = jdbcTemplate.queryForObject(selectCountClientProductSql, Integer.class, productId, cartId);

            if (count != null && count > 0) {
                jdbcTemplate.update(updateCountClientProductSql, productId, cartId);
            } else {
                jdbcTemplate.update(insertProductSql, productId, cartId);
            }
        } catch (EmptyResultDataAccessException e) {
            jdbcTemplate.update(insertProductSql, productId, cartId);
        }
    }

    @Override
    public boolean changeQuantity(long cartId, long productId, long quantity) {
        String updateQuantitySql = """
                UPDATE products_carts 
                SET count = ? WHERE id_product = ? 
                AND id_cart = ?
                """;
        int rowsAffected = jdbcTemplate.update(updateQuantitySql, quantity, productId, cartId);
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteProductFromCart(long cartId, long productId) {
        String deleteProductSql = """
                DELETE FROM products_carts 
                WHERE id_product = ? AND id_cart = ?
                """;
        int rowsAffected = jdbcTemplate.update(deleteProductSql, productId, cartId);
        return rowsAffected > 0;
    }

    @Transactional
    @Override
    public Optional<Payment> payment(long cartId) {
        String selectProductsSql = """
            SELECT pc.id_product, pc.count, p.name, p.price 
            FROM products_carts pc 
            JOIN products p 
            ON pc.id_product = p.id 
            WHERE id_cart = ?
            """;
        String updateProductCountSql = "UPDATE products SET count = count - ? WHERE id = ?";
        String updateBalanceSql = "UPDATE clients_bank SET balance = ? WHERE client_id = ?";
        String clearCartSql = "DELETE FROM products_carts WHERE id_cart = ?";

        List<Product> products = jdbcTemplate.query(selectProductsSql, (resultSet, rowNum) -> {
            long productId = resultSet.getLong("id_product");
            int quantity = resultSet.getInt("count");
            String name = resultSet.getString("name");
            BigDecimal price = resultSet.getBigDecimal("price");

            return new Product(productId, name, price, quantity);
        }, cartId);

        if (products.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal totalCost = products.stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BankAppProxy bankAppProxy = new BankAppProxy(jdbcTemplate);
        long clientId = getClientIdByCartId(cartId);

        if (clientId == -1) {
            return Optional.empty();
        }

        BigDecimal clientBalance = bankAppProxy.getBalanceClient(clientId);

        if (clientBalance.compareTo(totalCost) >= 0) {
            BigDecimal updatedBalance = clientBalance.subtract(totalCost);

            for (Product product : products) {
                int rowsAffected = jdbcTemplate.update(updateProductCountSql, product.getQuantity(), product.getId());
                if (rowsAffected == 0) {
                    throw new RuntimeException("Ошибка обновления количества товара");
                }
                int totalQuantity = getProductQuantity(product.getId());

                if (product.getQuantity() > totalQuantity + 1) {
                    throw new RuntimeException("Количество товара в корзине превышает общее количество товара");
                }
            }

            int rowsAffected = jdbcTemplate.update(updateBalanceSql, updatedBalance, clientId);

            if (rowsAffected > 0) {
                jdbcTemplate.update(clearCartSql, cartId);
                return Optional.of(new Payment(totalCost, updatedBalance));
            } else {
                throw new RuntimeException("Ошибка обновления баланса клиента");
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Cart> getCartById(long cartId) {
        String selectCartSql = "SELECT id, promocode FROM carts WHERE id = ?";
        String selectClientProductsSql = """
                                        SELECT pc.id_product, pc.count, p.name, p.price  
                                        FROM products_carts pc 
                                        JOIN products p 
                                        ON pc.id_product = p.id 
                                        WHERE id_cart = ?
                                        """;

        Cart cart = jdbcTemplate.queryForObject(selectCartSql, (resultSet, rowNum) -> {
            long id = resultSet.getLong("id");
            String promocode = resultSet.getString("promocode");
            return new Cart(id, new ArrayList<>(), promocode);
        }, cartId);

        if (cart == null) {
            return Optional.empty();
        }

        List<Product> products = jdbcTemplate.query(selectClientProductsSql, (resultSet, rowNum) -> {
            long productId = resultSet.getLong("id_product");
            int quantity = resultSet.getInt("count");
            String name = resultSet.getString("name");
            BigDecimal price = resultSet.getBigDecimal("price");
            return new Product(productId, name, price, quantity);
        }, cartId);

        cart.setProducts(products);

        return Optional.of(cart);
    }

    /**
     * Получение id клиента по id корзины
     * @param cartId id корзины
     * @return id клиента
     */
    private long getClientIdByCartId(long cartId) {
        String selectCartIdClientSql = """
                SELECT cart_id FROM clients 
                WHERE cart_id = ?
                """;
        return jdbcTemplate.queryForObject(selectCartIdClientSql, Long.class, cartId);
    }

    /**
     * Получает количество товара по id
     * @param productId id товара
     * @return количество товара
     */
    private int getProductQuantity(long productId) {
        String getProductQuantitySql = "SELECT count FROM products WHERE id = ?";

        return jdbcTemplate.queryForObject(getProductQuantitySql, Integer.class, productId);
    }
}
