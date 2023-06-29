package ru.sber.proxies;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;

import static ru.sber.repositories.DBProductRepository.JDBC;

/**
 * Прокси для получения банковской информации клиента
 */
@Component
public class DBBankAppProxy implements BankAppInterfaceProxy {
    @Override
    public BigDecimal getBalanceClient(long clientId) {
        String selectBalanceSql = """
                SELECT balance FROM products_smirnov_pa.client_bank 
                WHERE client_id = ?
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             PreparedStatement statement = connection.prepareStatement(selectBalanceSql)) {

            statement.setLong(1, clientId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBigDecimal("Баланс получен");
            } else {
                throw new RuntimeException("Пользователь не является клиентом банка");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBalanceClient(long clientId, BigDecimal newBalance) {
        var updateBalanceSql = """
            UPDATE products_smirnov_pa.client_bank 
            SET balance = ? 
            WHERE client_id = ?
            """;

        try (var connection = DriverManager.getConnection(JDBC);
             var updateBalanceStatement = connection.prepareStatement(updateBalanceSql)) {

            updateBalanceStatement.setBigDecimal(1, newBalance);
            updateBalanceStatement.setLong(2, clientId);
            updateBalanceStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
