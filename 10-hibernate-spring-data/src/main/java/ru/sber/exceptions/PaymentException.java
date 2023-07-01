package ru.sber.exceptions;

public class PaymentException extends RuntimeException {
    /**
     * Исключение для вывода сообщения об ошибке при неудачной оплате корзины с товарами
     * @param message сообщение об ошибке
     */
    public PaymentException(String message) {
        super(message);
    }
}
