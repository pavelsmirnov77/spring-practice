package ru.sber.proxies;

import java.math.BigDecimal;

public interface BankAppInterfaceProxy {
    /**
     * Возвращает баланс клиента
     * @param clientId id клиента
     * @return баланс клиента
     */
    BigDecimal getBalanceClient(long clientId);

    /**
     * Обновляет значение баланса клиента
     * @param clientId id клиента
     * @param newBalance новое значение баланса
     */
    void setBalanceClient(long clientId, BigDecimal newBalance);
}
