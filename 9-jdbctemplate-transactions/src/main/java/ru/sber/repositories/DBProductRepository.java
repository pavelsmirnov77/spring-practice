package ru.sber.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий, выполняющий действия над товаром
 */
@Repository
public class DBProductRepository implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long createProduct(Product product) {
        var insertProductSql = """
                INSERT INTO products (name, price, count) 
                VALUES (?,?,?);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertProductSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice().doubleValue());
            preparedStatement.setLong(3, product.getQuantity());

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return (long) (int) keyHolder.getKeys().get("id");
    }

    @Override
    public boolean changeProduct(Product product) {
        var updateProductSql = """
                UPDATE products
                SET 
                name = ?,
                price = ?,
                count = ?
                where id = ?;
                """;
        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(updateProductSql);
            prepareStatement.setString(1, product.getName());
            prepareStatement.setDouble(2, product.getPrice().doubleValue());
            prepareStatement.setLong(3, product.getQuantity());
            prepareStatement.setLong(4, product.getId());

            return prepareStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;
    }

    @Override
    public boolean deleteProductById(long productId) {
        var deleteProductSql = """
        DELETE FROM products 
        WHERE id = ?
        """;

        var deleteProductFromCartsSql = """
        DELETE FROM products_carts
        WHERE id_product = ? AND id_cart = ?
        """;

        String selectCartsSql = """
        SELECT id_cart
        FROM products_carts
        WHERE id_product = ?
        """;
        List<Long> cartIds = jdbcTemplate.queryForList(selectCartsSql, Long.class, productId);

        for (Long cartId : cartIds) {
            jdbcTemplate.update(deleteProductFromCartsSql, productId, cartId);
        }

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(deleteProductSql);
            prepareStatement.setLong(1, productId);
            return prepareStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);
        return rows > 0;
    }



    @Override
    public List<Product> findProductByName(String productName) {
        var selectProductsSql = """
                SELECT * FROM products 
                WHERE name like ?
                """;
        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectProductsSql);
            prepareStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");

            return prepareStatement;
        };

        RowMapper<Product> productRowMapper = getProductRowMapper();

        return jdbcTemplate.query(preparedStatementCreator, productRowMapper);
    }

    private static RowMapper<Product> getProductRowMapper() {
        return (resultSet, rowNum) -> {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            long quantity = resultSet.getLong("count");
            return new Product(id, name, BigDecimal.valueOf(price), quantity);
        };
    }

    @Override
    public Optional<Product> getProductById(long productId) {
        var selectProductsSql = """
                SELECT * FROM products
                WHERE id = ?
                """;
        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectProductsSql);
            prepareStatement.setLong(1, productId);

            return prepareStatement;
        };

        RowMapper<Product> productRowMapper = getProductRowMapper();

        List<Product> products = jdbcTemplate.query(preparedStatementCreator, productRowMapper);

        return products.stream().findFirst();
    }
}
