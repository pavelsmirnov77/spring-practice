package ru.sber.repositories;

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

    @Override
    public void addProductById(long cartId, long productId) {
        var selectCountClientProductSql = """ 
            SELECT count FROM products_smirnov_pa.product_client 
            WHERE id_product = ? 
            AND id_cart = ?
            """;
        var insertProductSql = """
            INSERT INTO products_smirnov_pa.product_client (id_product, id_cart, count) 
            VALUES (?, ?, 1)
            """;
        var updateCountClientProductSql = """
            UPDATE products_smirnov_pa.product_client 
            SET count = count + 1 
            WHERE id_product = ? 
            AND id_cart = ?
            """;
        var updateCountProductSql = """
            UPDATE products_smirnov_pa.product 
            SET count = count - 1
            WHERE id = ?
            """;

        try (var connection = DriverManager.getConnection(JDBC);
             var selectCountClientStatement = connection.prepareStatement(selectCountClientProductSql);
             var insertProductStatement = connection.prepareStatement(insertProductSql);
             var updateCountClientProductStatement = connection.prepareStatement(updateCountClientProductSql);
             var updateCountProductStatement = connection.prepareStatement(updateCountProductSql)) {

            selectCountClientStatement.setLong(1, productId);
            selectCountClientStatement.setLong(2, cartId);
            ResultSet resultSet = selectCountClientStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count > 0) {
                    updateCountClientProductStatement.setLong(1, productId);
                    updateCountClientProductStatement.setLong(2, cartId);
                    updateCountClientProductStatement.executeUpdate();
                }
            } else {
                insertProductStatement.setLong(1, productId);
                insertProductStatement.setLong(2, cartId);
                insertProductStatement.executeUpdate();
            }

            updateCountProductStatement.setLong(1, productId);
            updateCountProductStatement.executeUpdate();

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
             var updateQuantityStatement = connection.prepareStatement(updateQuantitySql)) {

            updateQuantityStatement.setLong(1, quantity);
            updateQuantityStatement.setLong(2, productId);
            updateQuantityStatement.setLong(3, cartId);
            int rowsAffected = updateQuantityStatement.executeUpdate();

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
             var deleteProductStatement = connection.prepareStatement(deleteProductSql)) {

            deleteProductStatement.setLong(1, productId);
            deleteProductStatement.setLong(2, cartId);
            int rowsAffected = deleteProductStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Payment> payment(long cartId) {
        var selectCartCartSql = """
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
                SET balance = ? WHERE client_id = ?
                """;
        var clearCartSql = """
                DELETE FROM products_smirnov_pa.product_client 
                WHERE id_cart = ?
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var selectCartStatement = connection.prepareStatement(selectCartCartSql);
             var selectProductsStatement = connection.prepareStatement(selectProductsSql)) {

            connection.setAutoCommit(false);

            selectCartStatement.setLong(1, cartId);
            ResultSet cartResultSet = selectCartStatement.executeQuery();

            if (!cartResultSet.next()) {
                return Optional.empty();
            }

            selectProductsStatement.setLong(1, cartId);
            ResultSet productsResultSet = selectProductsStatement.executeQuery();

            BigDecimal totalCost = BigDecimal.ZERO;

            while (productsResultSet.next()) {
                int quantity = productsResultSet.getInt("count");
                BigDecimal price = productsResultSet.getBigDecimal("price");
                totalCost = totalCost.add(price.multiply(BigDecimal.valueOf(quantity)));
            }

            DBBankAppProxy bankAppProxy = new DBBankAppProxy();
            long clientId = getClientIdByCartId(cartId);

            if (clientId == -1) {
                return Optional.empty();
            }

            BigDecimal clientBalance = bankAppProxy.getBalanceClient(clientId);

            if (clientBalance.compareTo(totalCost) >= 0) {
                BigDecimal updatedBalance = clientBalance.subtract(totalCost);

                try (var updateBalanceStatement = connection.prepareStatement(updateBalanceSql);
                     var clearCartStatement = connection.prepareStatement(clearCartSql)) {

                    updateBalanceStatement.setBigDecimal(1, updatedBalance);
                    updateBalanceStatement.setLong(2, clientId);
                    updateBalanceStatement.executeUpdate();

                    clearCartStatement.setLong(1, cartId);
                    clearCartStatement.executeUpdate();

                    return Optional.of(new Payment(totalCost, updatedBalance));

                } catch (SQLException e) {
                    throw new RuntimeException("Ошибка при приёме платежа: " + e.getMessage());
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Cart> getCartById(long cartId) {
        var selectCartSql = """
                SELECT id, promocode 
                FROM products_smirnov_pa.cart 
                WHERE id = ?
                """;
        var selectClientProductsSql = """
                SELECT pc.id_product, pc.count, p.name, p.price 
                FROM products_smirnov_pa.product_client pc 
                JOIN products_smirnov_pa.product p 
                ON pc.id_product = p.id 
                WHERE id_cart = ?
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var cartStatement = connection.prepareStatement(selectCartSql);
             var productsClientStatement = connection.prepareStatement(selectClientProductsSql)) {

            cartStatement.setLong(1, cartId);
            ResultSet cartResultSet = cartStatement.executeQuery();

            if (cartResultSet.next()) {
                String promocode = cartResultSet.getString("promocode");
                Cart cart = new Cart(cartId, new ArrayList<>(), promocode);

                productsClientStatement.setLong(1, cartId);
                ResultSet productsResultSet = productsClientStatement.executeQuery();

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
        var selectCartIdClientSql = """
                SELECT cart_id FROM products_smirnov_pa.client 
                WHERE cart_id = ?
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var selectCartIdClientStatement = connection.prepareStatement(selectCartIdClientSql)) {
            selectCartIdClientStatement.setLong(1, cartId);
            ResultSet clientResultSet = selectCartIdClientStatement.executeQuery();

            if (clientResultSet.next()) {
                return clientResultSet.getLong("cart_id");
            } else {

                return -1;

            }
        }
    }
}
