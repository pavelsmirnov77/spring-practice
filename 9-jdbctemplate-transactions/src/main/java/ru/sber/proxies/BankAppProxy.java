package ru.sber.proxies;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BankAppProxy implements BankAppInterfaceProxy {

    private final JdbcTemplate jdbcTemplate;

    public BankAppProxy(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalanceClient(long clientId) {
        String selectBalanceSql = "SELECT balance FROM clients_bank WHERE client_id = ?";
        return jdbcTemplate.queryForObject(selectBalanceSql, BigDecimal.class, clientId);
    }

    @Override
    public void setBalanceClient(long clientId, BigDecimal newBalance) {
        String updateBalanceSql = "UPDATE clients_bank SET balance = ? WHERE client_id = ?";
        jdbcTemplate.update(updateBalanceSql, newBalance, clientId);
    }
}
