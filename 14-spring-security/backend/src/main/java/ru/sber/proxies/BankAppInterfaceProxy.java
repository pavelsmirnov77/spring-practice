package ru.sber.proxies;

import ru.sber.entities.BankCard;

import java.math.BigDecimal;

public interface BankAppInterfaceProxy {
    /**
     * Возвращает баланс банковской карты
     *
     * @param numberCard номер банковской карты
     * @return баланс бансковской карты
     */
    BigDecimal getBalance(long numberCard);
    /**
     * Обновляет значение баланса банковской карты
     *
     * @param numberCard номер банковской карты
     * @param newBalance новое значение баланса
     */
    void setBalance(long numberCard, BigDecimal newBalance);

    /**
     * Добавляет новой банковской карты
     *
     * @param bankCard банковская карта
     */
    void addClient(BankCard bankCard);

}
