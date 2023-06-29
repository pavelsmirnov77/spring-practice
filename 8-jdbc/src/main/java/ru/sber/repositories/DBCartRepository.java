package ru.sber.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Cart;
import ru.sber.entities.Payment;
import ru.sber.entities.Product;
import ru.sber.proxies.DBBankAppProxy;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static ru.sber.repositories.DBProductRepository.JDBC;

/**
 * Репозиторий, выполняющий действия над товарами интернет магазина
 */
@Repository
public class DBCartRepository implements CartRepository {

    @Autowired
    private DBBankAppProxy bankAppProxy;

    public DBCartRepository(DBBankAppProxy bankAppProxy) {
        this.bankAppProxy = bankAppProxy;
    }

    @Override
    public void addProductById(long cartId, long productId) {
        var selectProductSql = """ 
                SELECT count FROM products_smirnov_pa.product_client 
                WHERE id_product = ? 
                AND id_cart = ?
                """;
        var insertProductSql = """
                INSERT INTO products_smirnov_pa.product_client (id_product, id_cart, count) 
                VALUES (?, ?, 1
                )""";
        var updateProductSql = """
                UPDATE products_smirnov_pa.product_client 
                SET count = count + 1 
                WHERE id_product = ? 
                AND id_cart = ?
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var selectStatement = connection.prepareStatement(selectProductSql);
             var insertStatement = connection.prepareStatement(insertProductSql);
             var updateStatement = connection.prepareStatement(updateProductSql)) {

            selectStatement.setLong(1, productId);
            selectStatement.setLong(2, cartId);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count > 0) {
                    updateStatement.setLong(1, productId);
                    updateStatement.setLong(2, cartId);
                    updateStatement.executeUpdate();
                }
            } else {
                insertStatement.setLong(1, productId);
                insertStatement.setLong(2, cartId);
                insertStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean changeQuantity(long cartId, long productId, long quantity) {
        var updateQuantitySql = """
                UPDATE products_smirnov_pa.product_client 
                SET count = ? 
                WHERE id_product = ? 
                AND id_cart = ?
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var updateStatement = connection.prepareStatement(updateQuantitySql)) {

            updateStatement.setLong(1, quantity);
            updateStatement.setLong(2, productId);
            updateStatement.setLong(3, cartId);
            int rowsAffected = updateStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteProductFromCart(long cartId, long productId) {
        var deleteProductSql = """
                DELETE FROM products_smirnov_pa.product_client
                WHERE id_product = ? 
                AND id_cart = ?
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var deleteStatement = connection.prepareStatement(deleteProductSql)) {

            deleteStatement.setLong(1, productId);
            deleteStatement.setLong(2, cartId);
            int rowsAffected = deleteStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Payment> payment(long cartId) {
        var selectCartSql = """
        SELECT id, promocode FROM products_smirnov_pa.cart 
        WHERE id = ?
        """;
        var selectProductsSql = """
        SELECT pc.id_product, pc.count, p.name, p.price 
        FROM products_smirnov_pa.product_client pc 
        JOIN products_smirnov_pa.product p 
        ON pc.id_product = p.id 
        WHERE id_cart = ?
        """;
        var updateBalanceSql = """
        UPDATE products_smirnov_pa.client_bank 
        SET balance = ? 
        WHERE client_id = ?
        """;
        var clearCartSql = """
        DELETE FROM products_smirnov_pa.product_client 
        WHERE id_cart = ?
        """;

        try (var connection = DriverManager.getConnection(JDBC);
             var cartStatement = connection.prepareStatement(selectCartSql);
             var productsStatement = connection.prepareStatement(selectProductsSql)) {

            connection.setAutoCommit(false);

            cartStatement.setLong(1, cartId);
            ResultSet cartResultSet = cartStatement.executeQuery();
            if (!cartResultSet.next()) {
                return Optional.empty();
            }

            productsStatement.setLong(1, cartId);
            ResultSet productsResultSet = productsStatement.executeQuery();

            BigDecimal totalCost = BigDecimal.ZERO;
            while (productsResultSet.next()) {
                int quantity = productsResultSet.getInt("count");
                BigDecimal price = productsResultSet.getBigDecimal("price");
                totalCost = totalCost.add(price.multiply(BigDecimal.valueOf(quantity)));
            }

            BigDecimal clientBalance = bankAppProxy.getBalanceClient(getClientIdByCartId(cartId));

            if (clientBalance == null) {
                return Optional.empty();
            }

            if (clientBalance.compareTo(totalCost) >= 0) {
                BigDecimal updatedBalance = clientBalance.subtract(totalCost);

                try (var updateBalanceStatement = connection.prepareStatement(updateBalanceSql);
                     var clearCartStatement = connection.prepareStatement(clearCartSql)) {

                    updateBalanceStatement.setBigDecimal(1, updatedBalance);
                    updateBalanceStatement.setLong(2, getClientIdByCartId(cartId));
                    updateBalanceStatement.executeUpdate();

                    clearCartStatement.setLong(1, cartId);
                    clearCartStatement.executeUpdate();

                    bankAppProxy.setBalanceClient(getClientIdByCartId(cartId), updatedBalance);

                    return Optional.of(new Payment(totalCost, updatedBalance));
                } catch (SQLException e) {
                    throw new RuntimeException("Error performing payment: " + e.getMessage());
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Cart> getCartById(long cartId) {
        var selectCartSql = "SELECT id, promocode FROM products_smirnov_pa.cart WHERE id = ?";
        var selectProductsSql = """
            SELECT pc.id_product, pc.count, p.name, p.price 
            FROM products_smirnov_pa.product_client pc 
            JOIN products_smirnov_pa.product p 
            ON pc.id_product = p.id 
            WHERE id_cart = ?
            """;

        try (var connection = DriverManager.getConnection(JDBC);
             var cartStatement = connection.prepareStatement(selectCartSql);
             var productsStatement = connection.prepareStatement(selectProductsSql)) {

            cartStatement.setLong(1, cartId);
            ResultSet cartResultSet = cartStatement.executeQuery();

            if (cartResultSet.next()) {
                String promocode = cartResultSet.getString("promocode");
                Cart cart = new Cart(cartId, new ArrayList<>(), promocode);

                productsStatement.setLong(1, cartId);
                ResultSet productsResultSet = productsStatement.executeQuery();

                while (productsResultSet.next()) {
                    long productId = productsResultSet.getLong("id_product");
                    int quantity = productsResultSet.getInt("count");
                    String name = productsResultSet.getString("name");
                    BigDecimal price = productsResultSet.getBigDecimal("price");

                    Product product = new Product(productId, name, price, quantity);
                    cart.getProducts().add(product);
                }

                return Optional.of(cart);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long getClientIdByCartId(long cartId) throws SQLException {
        var selectClientSql = """
                SELECT cart_id FROM products_smirnov_pa.client 
                WHERE cart_id = ?
                """;
        try (var connection = DriverManager.getConnection(JDBC);
             var clientStatement = connection.prepareStatement(selectClientSql)) {
            clientStatement.setLong(1, cartId);
            ResultSet clientResultSet = clientStatement.executeQuery();
            if (clientResultSet.next()) {
                return clientResultSet.getLong("cart_id");
            } else {
                return -1;
            }
        }
    }
}
