package ru.sber.repositories;

import org.springframework.stereotype.Repository;
import ru.sber.entities.Product;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий, выполняющий действия над товаром
 */
@Repository
public class DBProductRepository implements ProductRepository {
    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?currentSchema=products_smirnov_pa&user=postgres&password=postgres";

    @Override
    public long createProduct(Product product) {
        var insertProductSql = """
                INSERT INTO product (name, price, count) 
                VALUES (?,?,?);
                """;
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareInsertProductStatement = connection.prepareStatement(insertProductSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareInsertProductStatement.setString(1, product.getName());
            prepareInsertProductStatement.setDouble(2, product.getPrice().doubleValue());
            prepareInsertProductStatement.setLong(3, product.getQuantity());
            prepareInsertProductStatement.executeUpdate();

            ResultSet rs = prepareInsertProductStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean changeProduct(Product product) {
        var updateProductSql = """
                UPDATE product
                SET 
                name = ?,
                price = ?,
                count = ?
                where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareUpdateProductStatement = connection.prepareStatement(updateProductSql)) {
            prepareUpdateProductStatement.setString(1, product.getName());
            prepareUpdateProductStatement.setDouble(2, product.getPrice().doubleValue());
            prepareUpdateProductStatement.setLong(3, product.getQuantity());
            prepareUpdateProductStatement.setLong(4, product.getId());

            var rows = prepareUpdateProductStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteProductById(long productId) {
        var deleteProductSql = """
                DELETE FROM product 
                WHERE id = ?""";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareDeleteProductStatement = connection.prepareStatement(deleteProductSql)) {
            prepareDeleteProductStatement.setLong(1, productId);

            var rows = prepareDeleteProductStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findProductByName(String productName) {
        var selectProductsSql = """
                SELECT * FROM product 
                WHERE name like ?
                """;
        List<Product> products = new ArrayList<>();

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareSelectProductsSqlStatement = connection.prepareStatement(selectProductsSql)) {
            prepareSelectProductsSqlStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");

            var resultSet = prepareSelectProductsSqlStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                long quantity = resultSet.getLong("count");
                Product product = new Product(id, name, BigDecimal.valueOf(price), quantity);

                products.add(product);
            }

            return products;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> getProductById(long productId) {
        var selectProductsSql = """
                SELECT * FROM product 
                WHERE id = ?
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareSelectProductsStatement = connection.prepareStatement(selectProductsSql)) {
            prepareSelectProductsStatement.setLong(1, productId);

            var resultSet = prepareSelectProductsStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                long quantity = resultSet.getLong("count");
                Product product = new Product(id, name, BigDecimal.valueOf(price), quantity);

                return Optional.of(product);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
