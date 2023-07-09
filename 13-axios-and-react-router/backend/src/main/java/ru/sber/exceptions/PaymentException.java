package ru.sber.exceptions;


public class PaymentException extends RuntimeException {
    /**
     * Выбрасывает сообщение при неудачной оплате товара
     * @param message сообщение об ошибке
     */
    public PaymentException(String message) {
        super(message);
    }
}
