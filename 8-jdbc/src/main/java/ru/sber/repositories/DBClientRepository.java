package ru.sber.repositories;

import org.springframework.stereotype.Repository;
import ru.sber.entities.Cart;
import ru.sber.entities.Client;
import ru.sber.entities.ClientResponse;
import ru.sber.entities.Product;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий, выполняющий действия над клиентом интернет магазина
 */
@Repository
public class DBClientRepository implements ClientRepository {
    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    @Override
    public long registrationClient(Client client) {
        var insertClientSql = """
                INSERT INTO client (name, username, password, cart_id) 
                VALUES (?,?,?,?);""";
        var insertCartSql = """
                INSERT INTO cart (promocode) 
                VALUES (?);
                """;
        var insertClientBankSql = """
                INSERT INTO client_bank (balance, client_id) 
                VALUES (?, ?);
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareCartStatement = connection.prepareStatement(insertCartSql, Statement.RETURN_GENERATED_KEYS);
             var prepareClientStatement = connection.prepareStatement(insertClientSql, Statement.RETURN_GENERATED_KEYS);
             var prepareClientBankStatement = connection.prepareStatement(insertClientBankSql)) {

            prepareCartStatement.setString(1, "PROMO20");
            prepareCartStatement.executeUpdate();

            ResultSet cartKeys = prepareCartStatement.getGeneratedKeys();
            long cartId;

            if (cartKeys.next()) {
                cartId = cartKeys.getLong(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора корзины");
            }

            prepareClientStatement.setString(1, client.getName());
            prepareClientStatement.setString(2, client.getLogin());
            prepareClientStatement.setString(3, client.getPassword());
            prepareClientStatement.setLong(4, cartId);
            prepareClientStatement.executeUpdate();

            ResultSet clientKeys = prepareClientStatement.getGeneratedKeys();
            long clientId;

            if (clientKeys.next()) {
                clientId = clientKeys.getLong(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора клиента");
            }

            prepareClientBankStatement.setBigDecimal(1, BigDecimal.valueOf(100000));
            prepareClientBankStatement.setLong(2, clientId);
            prepareClientBankStatement.executeUpdate();

            return clientId;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientResponse getClientResponseById(long clientResponseId) {
        var selectClientSql = """
                SELECT id, name, cart_id 
                FROM client 
                WHERE id = ?
                """;
        var selectCartSql = """
                SELECT id, promocode 
                FROM cart 
                WHERE id = ?
                """;
        var selectProductsSql = """
                SELECT p.id, p.name, p.price, pc.count
                FROM product p
                JOIN product_client pc ON p.id = pc.id_product
                WHERE pc.id_cart = ?
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareClientStatement = connection.prepareStatement(selectClientSql);
             var prepareCartStatement = connection.prepareStatement(selectCartSql);
             var prepareProductsStatement = connection.prepareStatement(selectProductsSql)) {
            prepareClientStatement.setLong(1, clientResponseId);

            ResultSet clientResultSet = prepareClientStatement.executeQuery();

            if (clientResultSet.next()) {
                long id = clientResultSet.getLong("id");
                String name = clientResultSet.getString("name");
                long cartId = clientResultSet.getLong("cart_id");

                prepareCartStatement.setLong(1, cartId);
                ResultSet cartResultSet = prepareCartStatement.executeQuery();

                if (cartResultSet.next()) {
                    long cartIdResult = cartResultSet.getLong("id");
                    String promocode = cartResultSet.getString("promocode");

                    prepareProductsStatement.setLong(1, cartIdResult);
                    ResultSet productsResultSet = prepareProductsStatement.executeQuery();
                    List<Product> products = new ArrayList<>();

                    while (productsResultSet.next()) {
                        long productId = productsResultSet.getLong("id");
                        String productName = productsResultSet.getString("name");
                        BigDecimal price = productsResultSet.getBigDecimal("price");
                        int count = productsResultSet.getInt("count");
                        Product product = new Product(productId, productName, price, count);
                        products.add(product);
                    }

                    Cart cart = new Cart(cartIdResult, products, promocode);

                    return new ClientResponse(id, name, cart);
                } else {
                    throw new RuntimeException("Ошибка при получении данных о корзине");
                }
            } else {
                throw new RuntimeException("Клиент с указанным идентификатором не найден");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteClientById(long clientId) {
        var deleteClientSql = "DELETE FROM client WHERE id = ?";
        var deleteCartSql = "DELETE FROM cart WHERE id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var deleteClientStatement = connection.prepareStatement(deleteClientSql);
             var deleteCartStatement = connection.prepareStatement(deleteCartSql)) {

            deleteCartStatement.setLong(1, clientId);
            deleteCartStatement.executeUpdate();

            deleteClientStatement.setLong(1, clientId);
            int rowsAffected = deleteClientStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
