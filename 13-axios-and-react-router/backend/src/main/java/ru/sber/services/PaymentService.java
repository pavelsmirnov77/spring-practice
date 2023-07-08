package ru.sber.services;

import ru.sber.entities.Payment;

/**
 * Сервис для оплаты товаров
 */
public interface PaymentService {

    /**
     * Совершает платеж
     *
     * @param payment Платеж
     * @return Возвращает статус выполнения платежа
     */
    boolean pay(Payment payment);

}
