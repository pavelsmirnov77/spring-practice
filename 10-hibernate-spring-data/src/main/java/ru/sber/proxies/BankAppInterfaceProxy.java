package ru.sber.proxies;

import ru.sber.entities.ClientBank;

import java.math.BigDecimal;

public interface BankAppInterfaceProxy {
    /**
     * Возвращает баланс клиента
     * @param clientId id клиента
     * @return баланс клиента
     */
    BigDecimal getBalance(long clientId);
    /**
     * Обновляет значение баланса клиента
     * @param clientId id клиента
     * @param newBalance новое значение баланса
     */
    void setBalance(long clientId, BigDecimal newBalance);

    /**
     * Добавляет клиента в список банковских клиентов
     * @param client клиент интернет магазина
     */
    void addClient(ClientBank client);
}
