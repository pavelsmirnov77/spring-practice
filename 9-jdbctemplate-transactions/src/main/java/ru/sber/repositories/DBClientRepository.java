package ru.sber.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Cart;
import ru.sber.entities.Client;
import ru.sber.entities.ClientResponse;
import ru.sber.entities.Product;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * Репозиторий, выполняющий действия над клиентом интернет магазина
 */
@Repository
public class DBClientRepository implements ClientRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long registrationClient(Client client) {
        var insertClientSql = """
                INSERT INTO clients (name, username, password, email, cart_id) 
                VALUES (?,?,?,?,?);""";
        var insertCartSql = """
                INSERT INTO carts (promocode) 
                VALUES (?);
                """;
        var insertClientBankSql = """
                INSERT INTO clients_bank (balance, client_id) 
                VALUES (?, ?);
                """;

        KeyHolder clientKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator cartPreparedStatementCreator = connection -> {
            PreparedStatement cartPreparedStatement = connection.prepareStatement(insertCartSql, Statement.RETURN_GENERATED_KEYS);
            cartPreparedStatement.setString(1, "PROMO20");

            return cartPreparedStatement;
        };

        jdbcTemplate.update(cartPreparedStatementCreator, clientKeyHolder);

        long cartId = (long) (int) clientKeyHolder.getKeys().get("id");

        PreparedStatementCreator clientPreparedStatementCreator = connection -> {
            PreparedStatement clientPreparedStatement = connection.prepareStatement(insertClientSql, Statement.RETURN_GENERATED_KEYS);
            clientPreparedStatement.setString(1, client.getName());
            clientPreparedStatement.setString(2, client.getLogin());
            clientPreparedStatement.setString(3, client.getPassword());
            clientPreparedStatement.setString(4, client.getEmail());
            clientPreparedStatement.setLong(5, cartId);

            return clientPreparedStatement;
        };

        jdbcTemplate.update(clientPreparedStatementCreator, clientKeyHolder);

        long clientId = (long) (int) clientKeyHolder.getKeys().get("id");

        PreparedStatementCreator clientBankPreparedStatementCreator = connection -> {
            PreparedStatement clientBankPreparedStatement = connection.prepareStatement(insertClientBankSql);
            clientBankPreparedStatement.setBigDecimal(1, BigDecimal.valueOf(100000));
            clientBankPreparedStatement.setLong(2, clientId);

            return clientBankPreparedStatement;
        };

        jdbcTemplate.update(clientBankPreparedStatementCreator);

        return clientId;
    }

    @Override
    public ClientResponse getClientResponseById(long clientResponseId) {
        var selectClientSql = """
            SELECT c.id, c.name, c.cart_id 
            FROM clients c 
            WHERE c.id = ?
            """;

        RowMapper<ClientResponse> clientResponseRowMapper = (resultSet, rowNum) -> {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long cartId = resultSet.getLong("cart_id");

            Cart cart = getCartById(cartId);
            return new ClientResponse(id, name, cart);
        };

        return jdbcTemplate.queryForObject(selectClientSql, clientResponseRowMapper, clientResponseId);
    }

    private Cart getCartById(long cartId) {
        var selectCartSql = """
            SELECT ct.id, ct.promocode 
            FROM carts ct 
            WHERE ct.id = ?
            """;

        RowMapper<Cart> cartRowMapper = (resultSet, rowNum) -> {
            long id = resultSet.getLong("id");
            String promo = resultSet.getString("promocode");

            List<Product> products = getProductsForCart(cartId);
            return new Cart(id, products, promo);
        };

        return jdbcTemplate.queryForObject(selectCartSql, cartRowMapper, cartId);
    }

    private List<Product> getProductsForCart(long cartId) {
        var selectProductsSql = """
            SELECT p.id, p.name, p.price, pc.count
            FROM products p
            JOIN products_carts pc ON p.id = pc.id_product
            WHERE pc.id_cart = ?
            """;

        RowMapper<Product> productRowMapper = (resultSet, rowNum) -> {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            BigDecimal price = resultSet.getBigDecimal("price");
            int count = resultSet.getInt("count");
            return new Product(id, name, price, count);
        };

        return jdbcTemplate.query(selectProductsSql, productRowMapper, cartId);
    }

    @Override
    public boolean deleteClientById(long clientId) {
        var deleteClientBankSql = "DELETE FROM clients_bank WHERE id = ?";
        var deleteClientSql = "DELETE FROM clients WHERE id = ?";
        var deleteProductsCarts = "DELETE FROM products_carts WHERE id_cart IN (SELECT id FROM carts WHERE id = ?)";
        var deleteCartSql = "DELETE FROM carts WHERE id = ?";

        int rowsAffectedClientBank = jdbcTemplate.update(deleteClientBankSql, clientId);
        int rowsAffectedClient = jdbcTemplate.update(deleteClientSql, clientId);
        int rowsAffectedProducts = jdbcTemplate.update(deleteProductsCarts, clientId);
        int rowsAffectedCart = jdbcTemplate.update(deleteCartSql, clientId);

        return rowsAffectedClientBank > 0 && rowsAffectedClient > 0
                && rowsAffectedProducts > 0 && rowsAffectedCart > 0;
    }
}

