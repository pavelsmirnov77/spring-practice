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
        var selectBalanceSql = """
            SELECT balance FROM products_smirnov_pa.client_bank 
            WHERE client_id = ?
            """;

        try (var connection = DriverManager.getConnection(JDBC);
             var statement = connection.prepareStatement(selectBalanceSql)) {

            statement.setLong(1, clientId);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal("balance");
                } else {
                    throw new RuntimeException("User is not a bank client");
                }
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
