package ru.sber.proxies;

import java.math.BigDecimal;

public interface BankAppInterfaceProxy {
    BigDecimal getBalanceClient(long clientId);
    void setBalanceClient(long clientId, BigDecimal newBalance);
}
